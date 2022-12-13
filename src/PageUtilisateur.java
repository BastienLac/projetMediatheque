import javax.swing.*;
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
    static JTable table = new JTable(model);
    public static void main(int[] idUtilisateur) throws SQLException {
        //creation du Jframe
        JFrame jf = new JFrame("Utilisateur");
        //fixation des dimensions de la fenetre
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize(tailleMoniteur.width, tailleMoniteur.height);

        // label bienvenue
        JLabel bienvenue = new JLabel("Bienvenue a la page utiliateur ");
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

        jComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.getDataVector().removeAllElements();
                System.out.println("remove " + model.getRowCount());

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

                model.fireTableDataChanged();
                model = new DefaultTableModel(mediasByCateg, enteteTabMedia);
                System.out.println(model.getRowCount());

                table = new JTable(model) {
                    //desactiver l'editeur de ligne
                    public boolean editCellAt(int row, int column, java.util.EventObject e) {
                        return false;
                    }
                };
                table.getTableHeader().setReorderingAllowed(false);
                table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

                JScrollPane MyScrollPane = new JScrollPane(table);
                MyScrollPane.setBounds(50, 230, 520, 100);
                MyScrollPane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.blue));
                jf.add(MyScrollPane);
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

        JButton btnAjouter = new JButton("Réserver");
        btnAjouter.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnAjouter.setBounds(400, 450, 100, 50);
        jf.getContentPane().add(btnAjouter);

        JTextField txtIdMedia = new JTextField();
        txtIdMedia.setBounds(500, 500, 100, 30);
        jf.getContentPane().add(txtIdMedia);

        // Format d'affichage de la date
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");

        Date dateDeb = new Date(System.currentTimeMillis());
        String dateDebReservation = formatter.format(dateDeb);

        // currentDate + 1 week
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.WEEK_OF_YEAR, 1);
        Date dateFin = currentDate.getTime();
        String dateFinReservation = formatter.format(dateFin);

        btnAjouter.addActionListener(e -> {
            JTextField txtDateDeb = new JTextField();
            txtDateDeb.setBounds(600, 500, 100, 30);
            txtDateDeb.setText(dateDebReservation);
            txtDateDeb.setEnabled(false);
            jf.getContentPane().add(txtDateDeb);

            JTextField txtDateFin = new JTextField();
            txtDateFin.setBounds(700, 500, 100, 30);
            txtDateFin.setText(dateFinReservation);
            txtDateFin.setEnabled(false);
            jf.getContentPane().add(txtDateFin);
        });

        jf.setVisible(true);
        jf.setLayout(null);
    }
}
