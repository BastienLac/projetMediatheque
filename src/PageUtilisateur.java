import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PageUtilisateur {
    static Object[][] data;
    static String[] columnNames;
    static DefaultTableModel model = new DefaultTableModel(data, columnNames);
    static Object[][] data1;
    static String[] columnNames1;
    static DefaultTableModel modelreservation = new DefaultTableModel(data1, columnNames1);
    static Media mediaSelected = null;
    static Reservation reservationSelected = null;

    public static void main(int[] idUtilisateur) throws SQLException {
        //creation du Jframe
        JFrame jf = new JFrame("Utilisateur");
        //fixation des dimensions de la fenetre
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize(tailleMoniteur.width, tailleMoniteur.height);

        Utilisateur utilisateur = Utilisateur.getUtilisateurParId(idUtilisateur[0]);

        // label bienvenue
        JLabel bienvenue = new JLabel("Bienvenue " + utilisateur.getPrenom() + " !");
        jf.setLayout(null);
        bienvenue.setBounds(50, 30, 500, 30);
        jf.add(bienvenue);
        bienvenue.setFont(new Font("Serif", Font.BOLD, 30));
        // Changer la couleur du texte
        bienvenue.setForeground(Color.DARK_GRAY);

        JLabel jLabelCategorie = new JLabel("Recherche par catégorie de médias");
        jLabelCategorie.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jLabelCategorie.setBounds(50, 100, 200, 30);
        jf.getContentPane().add(jLabelCategorie);

        // Liste des categories media
        ArrayList<CategorieMedia> allCategorie = CategorieMedia.getAllCategorie();
        String[] categories = new String[allCategorie.size()];
        Vector modelCateg = new Vector<>();
        for (int i = 0; i < categories.length; i++) {
            modelCateg.add(i, allCategorie.get(i).getNom());
            categories[i] = allCategorie.get(i).getNom();
        }

        // Ajout Combo Box a la fenetre
        JComboBox<String> jComboBox = new JComboBox(modelCateg);
        jComboBox.setBounds(50, 130, 300, 50);
        jf.getContentPane().add(jComboBox);

        String[] enteteTabMedia = {"titre", "createur", "année de parution"};
        JTable tableMedias = new JTable(model) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        tableMedias.getTableHeader().setReorderingAllowed(false);
        tableMedias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        JScrollPane MyScrollPane = new JScrollPane(tableMedias);
        MyScrollPane.setBounds(50, 200, 500, 100);
        MyScrollPane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.blue));
        jf.add(MyScrollPane);
        MyScrollPane.setVisible(false);

        jComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableMedias.getSelectionModel().clearSelection();
                model.getDataVector().removeAllElements();

                CategorieMedia selectedCategorie = null;
                try {
                    selectedCategorie = CategorieMedia.getCategorieParNom((String) jComboBox.getSelectedItem());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                ArrayList<Media> medias = null;
                try {
                    medias = Media.getMediaParCategorie(selectedCategorie.getId());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                Object[][] mediasByCateg = new Object[medias.size()][enteteTabMedia.length];
                for (int i = 0; i < medias.size(); i++) {
                    for (int j = 0; j < enteteTabMedia.length; j++) {
                        switch (j) {
                            case 0 : {
                                mediasByCateg[i][j] = medias.get(i).titre;
                                break;
                            }
                            case 1 : {
                                mediasByCateg[i][j] = medias.get(i).createur;
                                break;
                            }
                            case 2 : {
                                mediasByCateg[i][j] = medias.get(i).anneeDeParution;
                                break;
                            }
                        }
                    }
                    model.addRow(mediasByCateg[i]);
                }

                model = new DefaultTableModel(mediasByCateg, enteteTabMedia);
                model.fireTableDataChanged();
                tableMedias.setModel(model);
                MyScrollPane.setVisible(true);
            }
        });

        JLabel jLabelReservation = new JLabel("Mes réservations");
        jLabelReservation.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jLabelReservation.setBounds(800, 100, 200, 30);
        jf.getContentPane().add(jLabelReservation);

        ArrayList<Reservation> reservations = null;
        try {
            reservations = Reservation.getMesReservations(utilisateur.getId());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        String[] enteteReservation = {"Titre", "Id", "Date début", "Date de fin"};
        JTable tableReservation = new JTable(modelreservation) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        Object[][] reservationsUtilisateur = new Object[reservations.size()][enteteReservation.length];
        for (int i = 0; i < reservations.size(); i++) {
            for (int j = 0; j < enteteReservation.length; j++) {
                switch (j) {
                    case 0 : {
                        reservationsUtilisateur[i][j] = Objects.requireNonNull(Media.findMediaById(reservations.get(i).getIdMedia())).getTitre();
                        break;
                    }
                    case 1 : {
                        reservationsUtilisateur[i][j] = Objects.requireNonNull(Media.findMediaById(reservations.get(i).getIdMedia())).getId();
                        break;
                    }
                    case 2 : {
                        reservationsUtilisateur[i][j] = reservations.get(i).getDateDebut();
                        break;
                    }
                    case 3 : {
                        reservationsUtilisateur[i][j] = reservations.get(i).getDateFin();
                        break;
                    }
                }
            }
        }

        modelreservation = new DefaultTableModel(reservationsUtilisateur, enteteReservation);
        modelreservation.fireTableDataChanged();
        tableReservation.setModel(modelreservation);
        //JTable tableReservation = new JTable(reservationsUtilisateur, enteteReservation);
        tableReservation.getColumnModel().getColumn(1).setMinWidth(0);
        tableReservation.getColumnModel().getColumn(1).setMaxWidth(0);
        tableReservation.getColumnModel().getColumn(1).setPreferredWidth(0);
        tableReservation.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        tableReservation.setBounds(800, 130, 400, 100);

        JScrollPane scrollPaneReservation = new JScrollPane(tableReservation);
        scrollPaneReservation.setBounds(800, 130, 500, 100);
        scrollPaneReservation.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.blue));
        jf.add(scrollPaneReservation);
        scrollPaneReservation.setVisible(true);
        //jf.getContentPane().add(tableReservation);

        JButton btnReserver = new JButton("Réserver");
        btnReserver.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnReserver.setBounds(800, 250, 100, 50);
        jf.getContentPane().add(btnReserver);

        // Format d'affichage de la date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date dateDeb = new Date(System.currentTimeMillis());
        String dateDebReservation = formatter.format(dateDeb);

        // currentDate + 1 week
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.WEEK_OF_YEAR, 1);
        Date dateFin = currentDate.getTime();
        String dateFinReservation = formatter.format(dateFin);

        tableMedias.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                try {
                    mediaSelected = Media.findMedia((String) tableMedias.getValueAt(tableMedias.getSelectedRow(), 0), (String) tableMedias.getValueAt(tableMedias.getSelectedRow(), 1), (int) tableMedias.getValueAt(tableMedias.getSelectedRow(), 2));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnReserver.addActionListener(e -> {
            // aucun média n'est sélectionné
            if (tableMedias.getSelectedRow() < 0) {
                /*JDialog emptySelection = new JDialog(jf, "Aucun média selectionné");
                JLabel label = new JLabel("Veuillez sélectionné un média pour le réserver.");
                emptySelection.add(label);
                emptySelection.setSize(500, 200);
                emptySelection.setLocation(500, 200);
                emptySelection.setVisible(true);
                 */
                JOptionPane.showMessageDialog(null, "Veuillez sélectionné un média pour le réserver.");

            }
            else {
                try {
                    Reservation.ajouterReservation(mediaSelected.getId(), utilisateur.getId(), dateDebReservation, dateFinReservation);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                // Format d'affichage de la date pour l'utilisateur
                SimpleDateFormat formatUser = new SimpleDateFormat("dd/MM/yyyy");

                /*JDialog emptySelection = new JDialog(jf, "Média réservé !");
                JLabel label = new JLabel("Vous venez de réserver le média : " + mediaSelected.getTitre() + " du " + formatUser.format(dateDeb) + " jusqu'au " + formatUser.format(dateFin));
                emptySelection.add(label);
                emptySelection.setSize(500, 200);
                emptySelection.setLocation(500, 200);
                emptySelection.setVisible(true);
                 */
                JOptionPane.showMessageDialog(null, "Vous venez de réserver le média : " + mediaSelected.getTitre() + " du " + formatUser.format(dateDeb) + " jusqu\'au " + formatUser.format(dateFin));

            }
        });

        tableReservation.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                try {
                    reservationSelected = Reservation.findReservation((int) tableReservation.getValueAt(tableReservation.getSelectedRow(), 1), utilisateur.getId(), (java.sql.Date) tableReservation.getValueAt(tableReservation.getSelectedRow(), 2), (java.sql.Date) tableReservation.getValueAt(tableReservation.getSelectedRow(), 3));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //button supprimer
        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setBounds(800, 350, 100, 30);
        jf.add(btnSupprimer);

        System.out.println("count " + tableReservation.getRowCount());

        btnSupprimer.addActionListener(e -> {
            // aucune réservation n'est sélectionnée
            if (tableReservation.getSelectedRow() < 0) {
                /*JDialog emptySelection = new JDialog(jf, "Aucune réservation selectionnée");
                JLabel label = new JLabel("Veuillez sélectionner une réservation pour la supprimer.");
                emptySelection.add(label);
                emptySelection.setSize(500, 200);
                emptySelection.setLocation(500, 200);
                emptySelection.setVisible(true);
                 */
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner une réservation pour la supprimer.");
            }
            else {
                try {
                    Reservation.supprimerReservation(reservationSelected.getId());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("count " + tableReservation.getRowCount() + " selected row " + tableReservation.getSelectedRow());
                modelreservation.removeRow(tableReservation.getSelectedRow());
                JOptionPane.showMessageDialog(null, "Vous avez bien supprimé la réservation");
            }
        });

        jf.setVisible(true);
        jf.setLayout(null);
    }
}
