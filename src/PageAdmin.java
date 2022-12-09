import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.IdentityHashMap;

public class PageAdmin  {
    private static Object[][] data;
    private static String[] columnNames;

    public static void main() throws SQLException {
        JFrame adminpage = new JFrame("Administarateur");
        adminpage.setSize(1000, 1000);
        adminpage.setLocation(100, 50);

        // label bienvenue
        JLabel bienvenue = new JLabel("Bienvenue à la page administateur ");
        adminpage.setLayout(null);
        bienvenue.setBounds(240, 70, 600, 130);
        adminpage.add(bienvenue);
        bienvenue.setFont(new Font("Serif", Font.BOLD, 30));
        // Changer la couleur du texte
        bienvenue.setForeground(Color.DARK_GRAY);


        //
        JLabel tableau = new JLabel("Ci-joint le tableau des médias, vous pouvez en ajouter ou supprimer");
        tableau.setBounds(50, 145, 550, 150);
        adminpage.add(tableau);
        //suppression
        JLabel suppression = new JLabel("Pour supprimer une ligne, entre l'id de la ligne que vous désirez supprimer puis cliquez sur supprimer");
        suppression.setBounds(50, 265, 750, 150);
        adminpage.add(suppression);

        //ajout des données de la bdd
        ArrayList<Media> allmedias = Media.getAll();
        String[] mediasTitre = new String[5];
        String[] mediasCreateur = new String[5];
        int[] mediasAnnee = new int[5];
        int[] mediasCategorie = new int[5];
        for (int i = 0; i < 1; i++) {
            mediasTitre[i] = allmedias.get(i).getTitre();
        }
        for (int i = 0; i < 1; i++) {
            mediasCreateur[i] = allmedias.get(i).getCreateur();
        }
        for (int i = 0; i < 1; i++) {
            mediasAnnee[i] = allmedias.get(i).getAnneeDeParution();
        }
        for (int i = 0; i < 1; i++) {
            mediasCategorie[i] = allmedias.get(i).getCategorie();
        }

        // table
        data = new Object[][] {{1,mediasTitre[0],mediasCreateur[0], mediasAnnee[0],mediasCategorie[0]}, {2,mediasTitre[1],mediasCreateur[1], mediasAnnee[0],mediasCategorie[0]},};
        columnNames = new String[] {"Id", "Titre","Créateur","Année de parution","IdCategorieMedia"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JScrollPane MyScrollPane= new JScrollPane(table);
        MyScrollPane.setBounds(50, 230, 520, 100);
        adminpage.add(MyScrollPane);

        //button
        JButton button= new JButton("Supprimer");
        JTextField iDTODELETE = new JTextField();
        iDTODELETE.setBounds(70, 400, 100, 30);
        adminpage.add(iDTODELETE);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // check for selected row first

                try {
                    Connection conn = MySQLConnection.getConnexion();
                    PreparedStatement st = conn.prepareStatement("DELETE FROM media WHERE id= ?");
                    st.setString(1,iDTODELETE.getText());
                    st.executeUpdate();
                }
                catch(Exception e){
                    System.out.println(e);
                }
                model.removeRow(Integer.parseInt(iDTODELETE.getText())-1);
                JOptionPane.showMessageDialog(null, "Vous avez bien supprimé le media");
            }
        });
        button.setBounds(70, 360, 100, 30);
        adminpage.add(button);
        JButton addData= new JButton("Ajouter");
        addData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {

                JTextField id = new JTextField(16);
                JTextField titre = new JTextField(16);
                JTextField createur = new JTextField(16);
                JTextField anneeDeParution = new JTextField(16);
                JTextField category = new JTextField(16);
                id.setBounds(315, 360, 100, 30);
                titre.setBounds(415, 360, 100, 30);
                createur.setBounds(515, 360, 100, 30);
                anneeDeParution.setBounds(615, 360, 100, 30);
                category.setBounds(715, 360, 100, 30);
                adminpage.add(id);
                adminpage.add(titre);
                adminpage.add(createur);
                adminpage.add(anneeDeParution);
                adminpage.add(category);
                JButton validation = new JButton("Valider");
                validation.setBounds(815, 360, 130, 30);
                adminpage.add(validation);

                validation.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s = e.getActionCommand();
                        if (s.equals("Valider")) {

                            String identifiant = id.getText();
                            String mediaTitre = titre.getText();
                            String mediaAnnee = createur.getText();
                            String mediaDate = anneeDeParution.getText();
                            String mediaCategorie = category.getText();
                            model.insertRow(table.getRowCount(),new Object[]{identifiant,mediaTitre,mediaAnnee,mediaDate,mediaCategorie});

                            try {
                                int a = 7;
                                Connection conn = MySQLConnection.getConnexion();
                                PreparedStatement st = conn.prepareStatement("INSERT INTO media (`id`, `titre`, `createur`, `anneeDeParution`, `idCategorieMedia`) VALUES (?,?,?,?,?)");
                                st.setString(1,identifiant);
                                st.setString(2,mediaTitre);
                                st.setString(3,mediaAnnee);
                                st.setString(4,mediaDate);
                                st.setString(5,mediaCategorie);

                                st.executeUpdate();

                            }
                            catch(Exception exception){
                                System.out.println(exception);
                            }
                            System.out.println(Integer.parseInt(identifiant));
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
