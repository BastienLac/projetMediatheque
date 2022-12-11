import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class PageAdmin {
    static Object[][] data;
    static String[] columnNames;
    static DefaultTableModel model = new DefaultTableModel(data, columnNames);
    static JTable table = new JTable(model);
    static JTextField titre = new JTextField();
    static JTextField createur = new JTextField();
    static JTextField anneeDeParution = new JTextField();
    static JTextField categorie = new JTextField();
    public static void main() throws SQLException {

        //creation du Jframe
        JFrame adminpage = new JFrame("Administrateur");
        //fixation des dimensions de la fenetre
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        adminpage.setSize(tailleMoniteur.width, tailleMoniteur.height);
        adminpage.setLocation(100, 50);

        // label bienvenue
        JLabel bienvenue = new JLabel("Bienvenue a la page administrateur ");
        adminpage.setLayout(null);
        bienvenue.setBounds(440, 70, 600, 130);
        adminpage.add(bienvenue);
        bienvenue.setFont(new Font("Serif", Font.BOLD, 30));
        // Changer la couleur du texte
        bienvenue.setForeground(Color.DARK_GRAY);

        //
        JLabel tableau = new JLabel("Ci-dessous le tableau des medias, vous pouvez en ajouter ou supprimer");
        tableau.setBounds(50, 145, 550, 150);
        adminpage.add(tableau);
        //suppression
        JLabel suppression = new JLabel("Pour supprimer une ligne, selectionnez la ligne que vous desirez supprimer puis cliquez sur supprimer");
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

        // table
        columnNames = new String[]{"Titre", "Createur", "Annee de parution", "IdCategorieMedia"};
        data = new Object[allmedias.toArray().length][columnNames.length];
        for (int i = 0; i < allmedias.toArray().length; i++) {
            for (int j = 0; j < columnNames.length; j++) {
                switch (j) {
                    case 0 -> data[i][j] = allmedias.get(i).getTitre();
                    case 1 -> data[i][j] = allmedias.get(i).getCreateur();
                    case 2 -> data[i][j] = allmedias.get(i).getAnneeDeParution();
                    case 3 -> data[i][j] = allmedias.get(i).getCategorie();
                }
            }
        }
        model = new DefaultTableModel(data, columnNames);
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
        adminpage.add(MyScrollPane);
        MyScrollPane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.blue));

        //button supprimer
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBounds(70, 360, 100, 30);
        adminpage.add(deleteButton);
        deleteButton.addActionListener(ae -> {
            // check for selected row first
            Media.supprimerMedia();
            model.removeRow(table.getSelectedRow());
            JOptionPane.showMessageDialog(null, "Vous avez bien supprime le media");
        });

        //bouton ajouter
        JButton addData = new JButton("Ajouter");
        addData.setBounds(210, 360, 100, 30);
        adminpage.add(addData);
        addData.addActionListener(a -> {
            //creation des InputText
            JLabel titree = new JLabel("Titre :");
            JLabel createurr = new JLabel("Createur :");
            JLabel anneeDeParutionn = new JLabel("Annee de parution :");
            JLabel categoriee = new JLabel("Id de la categorie :");
            titre = new JTextField("");
            createur = new JTextField("");
            anneeDeParution = new JTextField("");
            categorie = new JTextField("");
            adminpage.getContentPane().add(titree);
            adminpage.getContentPane().add(createurr);
            adminpage.getContentPane().add(anneeDeParutionn);
            adminpage.getContentPane().add(categoriee);
            adminpage.getContentPane().add(titre);
            adminpage.getContentPane().add(createur);
            adminpage.getContentPane().add(anneeDeParution);
            adminpage.getContentPane().add(categorie);
            titree.setBounds(345, 360, 50, 30);
            createurr.setBounds(325, 400, 80, 30);
            anneeDeParutionn.setBounds(270, 440, 120, 30);
            categoriee.setBounds(270, 480, 120, 30);
            titre.setBounds(390, 360, 100, 30);
            createur.setBounds(390, 400, 100, 30);
            anneeDeParution.setBounds(390, 440, 100, 30);
            categorie.setBounds(390, 480, 100, 30);
            //categories disponibles
            JLabel categoriesAvaible = new JLabel("Categories disponibles :");
            adminpage.getContentPane().add(categoriesAvaible);
            categoriesAvaible.setBounds(500, 480, 200, 30);
            // Ajout Combo Box a la fenetre
            JComboBox<String> jComboBox = new JComboBox<>(categories);
            adminpage.getContentPane().add(jComboBox);
            jComboBox.setBounds(660, 480, 40, 40);

            //bouton Valider
            JButton validation = new JButton("Valider");
            adminpage.getContentPane().add(validation);
            validation.setBounds(390, 520, 100, 30);
            //action lorsqu'on clique
            validation.addActionListener(e -> {
                String s = e.getActionCommand();
                if (s.equals("Valider")) {
                    Media.ajouterMedia();
                }
            });
        });
        adminpage.setVisible(true);
        adminpage.setLayout(null);
    }
}
