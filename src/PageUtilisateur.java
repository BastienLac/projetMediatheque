import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class PageUtilisateur {
    static Object[][] data;
    static String[] columnNames;
    static DefaultTableModel model = new DefaultTableModel(data, columnNames);
    static Media mediaSelected = null;
    static JTextField txtDateDeb = new JTextField();
    static JTextField txtDateFin = new JTextField();

    public static void main(int[] idUtilisateur) throws SQLException {
        //creation du Jframe
        JFrame jf = new JFrame("Utilisateur");
        //fixation des dimensions de la fenetre
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize(tailleMoniteur.width, tailleMoniteur.height);

        // label bienvenue
        JLabel bienvenue = new JLabel("Bienvenue sur la page utiliateur ");
        jf.setLayout(null);
        bienvenue.setBounds(440, 70, 600, 130);
        jf.add(bienvenue);
        bienvenue.setFont(new Font("Serif", Font.BOLD, 30));
        // Changer la couleur du texte
        bienvenue.setForeground(Color.DARK_GRAY);

        JLabel jLabelCategorie = new JLabel("Catégorie de médias");
        jLabelCategorie.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jLabelCategorie.setBounds(50, 50, 200, 50);
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
        jComboBox.setBounds(50, 100, 300, 50);
        jf.getContentPane().add(jComboBox);

        JLabel jLabelChooseCategorie = new JLabel();
        jLabelChooseCategorie.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jLabelChooseCategorie.setBounds(50, 150, 300, 50);
        jf.getContentPane().add(jLabelChooseCategorie);

        String[] enteteTabMedia = {"titre", "createur", "année de parution"};
        JTable tableMedias = new JTable(model) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        tableMedias.getTableHeader().setReorderingAllowed(false);
        tableMedias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        JScrollPane MyScrollPane = new JScrollPane(tableMedias);
        MyScrollPane.setBounds(50, 230, 520, 100);
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
        jLabelReservation.setBounds(50, 300, 200, 50);
        jf.getContentPane().add(jLabelReservation);

        ArrayList<Reservation> reservations = null;
        try {
            reservations = Reservation.getMesReservations(idUtilisateur[0]);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        String[] entete = {"Date début", "Date de fin"};
        Object[][] reservationsUtilisateur = new Object[reservations.size()][entete.length];
        for (int i = 0; i < reservations.size(); i++) {
            for (int j = 0; j < entete.length; j++) {
                switch (j) {
                    case 0 : {
                        reservationsUtilisateur[i][j] = reservations.get(i).getDateDebut();
                        break;
                    }
                    case 1 : {
                        reservationsUtilisateur[i][j] = reservations.get(i).getDateFin();
                        break;
                    }
                }
            }
        }

        JTable tableReservation = new JTable(reservationsUtilisateur, entete);
        tableReservation.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        tableReservation.setBounds(50, 400, 300, 150);
        jf.getContentPane().add(tableReservation);

        JButton btnReserver = new JButton("Réserver");
        btnReserver.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnReserver.setBounds(400, 450, 100, 50);
        jf.getContentPane().add(btnReserver);

        JTextField txtIdMedia = new JTextField();
        txtIdMedia.setBounds(500, 500, 100, 30);
        jf.getContentPane().add(txtIdMedia);

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

                if (tableMedias.getSelectedRow() > 0) {
                    txtDateDeb.setBounds(600, 500, 100, 30);
                    txtDateDeb.setText(dateDebReservation);
                    txtDateDeb.setEnabled(false);
                    jf.getContentPane().add(txtDateDeb);

                    txtDateFin.setBounds(700, 500, 100, 30);
                    txtDateFin.setText(dateFinReservation);
                    txtDateFin.setEnabled(false);
                    jf.getContentPane().add(txtDateFin);
                }
            }
        });

        btnReserver.addActionListener(e -> {
            Reservation.ajouterReservation(mediaSelected.getId(), idUtilisateur[0], dateDebReservation, dateFinReservation);
        });

        jf.setVisible(true);
        jf.setLayout(null);
    }
}
