import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;

public class PageAdmin {
    private static Object[][] data;
    private static String[] columnNames;

    public static void main() throws SQLException {
        JFrame adminpage = new JFrame("Administarateur");
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        adminpage.setSize(tailleMoniteur.width, tailleMoniteur.height);
        adminpage.setLocation(100, 50);

        // label bienvenue
        JLabel bienvenue = new JLabel("Bienvenue a la page administateur ");
        adminpage.setLayout(null);
        bienvenue.setBounds(440, 70, 600, 130);
        adminpage.add(bienvenue);
        bienvenue.setFont(new Font("Serif", Font.BOLD, 30));
        // Changer la couleur du texte
        bienvenue.setForeground(Color.DARK_GRAY);

        //
        JLabel tableau = new JLabel("Ci-joint le tableau des medias, vous pouvez en ajouter ou supprimer");
        tableau.setBounds(50, 145, 550, 150);
        adminpage.add(tableau);
        //suppression
        JLabel suppression = new JLabel("Pour supprimer une ligne, entrer le numero de la ligne que vous desirez supprimer puis cliquez sur supprimer");
        suppression.setBounds(50, 265, 750, 150);
        adminpage.add(suppression);

        //ajout des données de la bdd
        ArrayList<Media> allmedias = Media.getAll();

        // comoBox
        ArrayList<CategorieMedia> allCategorie = CategorieMedia.getAllCategorie();
        String[] categories = new String[allCategorie.toArray().length];
        for (int i = 0; i < categories.length; i++) {
            categories[i] = allCategorie.get(i).getNom();
        }

        JLabel categoriesAvaible = new JLabel("Categories disponibles :");
        categoriesAvaible.setBounds(580, 200, 300, 40);
        adminpage.getContentPane().add(categoriesAvaible);
        // Ajout Combo Box a la fenetre
        JComboBox<String> jComboBox = new JComboBox<>(categories);
        jComboBox.setBounds(580, 240, 100, 40);
        adminpage.getContentPane().add(jComboBox);

        //button
        JButton delete = new JButton("Supprimer");
        JTextField iDTODELETE = new JTextField();
        iDTODELETE.setBounds(70, 400, 100, 30);
        adminpage.add(iDTODELETE);

        // table
        columnNames = new String[]{ "Titre", "Createur", "Annee de parution", "IdCategorieMedia"};
        data = new Object[allmedias.toArray().length][columnNames.length];
        for (int i = 0; i < allmedias.toArray().length; i++) {
            for(int j = 0; j<columnNames.length ;j++){
               switch (j) {
                   case 0 : {data[i][j]= allmedias.get(i).getTitre();
                             break;
                   }
                   case 1 : { data[i][j]= allmedias.get(i).getCreateur();
                       break;
                   }
                   case 2 :{data[i][j]= allmedias.get(i).getAnneeDeParution();
                       break;
                   }
                   case 3 :{data[i][j]= allmedias.get(i).getCategorie();
                       break;
                   }
               }
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JScrollPane MyScrollPane = new JScrollPane(table);
        MyScrollPane.setBounds(50, 230, 520, 100);
        adminpage.add(MyScrollPane);
        MyScrollPane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.blue));

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // check for selected row first
                try {
                    Connection conn = MySQLConnection.getConnexion();
                    PreparedStatement st = conn.prepareStatement("DELETE FROM media WHERE titre= ?");
                    st.setString(1,iDTODELETE.getText() );
                    st.executeUpdate();
                } catch (Exception e) {
                    System.out.println(e);
                }
                model.removeRow(table.getSelectedRow());
                JOptionPane.showMessageDialog(null, "Vous avez bien supprime le media");
            }
        });

        delete.setBounds(70, 360, 100, 30);
        adminpage.add(delete);
        JButton addData = new JButton("Ajouter");
        addData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                //JTextField id = new JTextField(16);
                JTextField titre = new JTextField(16);
                JTextField createur = new JTextField(16);
                JTextField anneeDeParution = new JTextField(16);
                JTextField categorie = new JTextField(16);
                //id.setBounds(315, 360, 100, 30);
                titre.setBounds(415, 360, 100, 30);
                createur.setBounds(515, 360, 100, 30);
                anneeDeParution.setBounds(615, 360, 100, 30);
                categorie.setBounds(715, 360, 100, 30);
                //adminpage.getContentPane().add(id);
                adminpage.getContentPane().add(titre);
                adminpage.getContentPane().add(createur);
                adminpage.getContentPane().add(anneeDeParution);
                adminpage.getContentPane().add(categorie);
                JButton validation = new JButton("Valider");
                validation.setBounds(815, 360, 130, 30);
                adminpage.getContentPane().add(validation);
                adminpage.setVisible(true);

                //action lorsqu'on clique
                validation.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s = e.getActionCommand();
                        if (s.equals("Valider")) {

                           // String identifiant = id.getText();
                            String mediaTitre = titre.getText();
                            String mediaAnnee = createur.getText();
                            String mediaDate = anneeDeParution.getText();
                            String mediaCategorie = categorie.getText();
                            model.insertRow(table.getRowCount(), new Object[]{ mediaTitre, mediaAnnee, mediaDate, mediaCategorie});

                            try {
                                Connection conn = MySQLConnection.getConnexion();
                                PreparedStatement st = conn.prepareStatement("INSERT INTO media (`titre`, `createur`, `anneeDeParution`, `idCategorieMedia`) VALUES (?,?,?,?)");
                                st.setString(1, mediaTitre);
                                st.setString(2, mediaAnnee);
                                st.setString(3, mediaDate);
                                st.setString(4, mediaCategorie);
                                st.executeUpdate();
                            } catch (Exception exception) {
                                System.out.println(exception);
                            }
                        }
                    }
                });
            }
        });
        addData.setBounds(210, 360, 100, 30);
        adminpage.add(addData);
        adminpage.setVisible(true);
        adminpage.setLayout(null);
    }
}
