import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.EventObject;

public class PageAdmin {
    static Object[][] data;
    static String[] columnNames;
    static DefaultTableModel model = new DefaultTableModel(data, columnNames);
    static JTable table = new JTable(model);
    static JTextField titre = new JTextField();
    static JTextField createur = new JTextField();
    static JTextField anneeDeParution = new JTextField();
    static JTextField categorie = new JTextField();
    static JTextField nbChanson = new JTextField();
    static JTextField duree = new JTextField();
    static JTextField console = new JTextField();
    static JTextField nbPages = new JTextField();
    static JLabel nbChansonCD = new JLabel();
    static JLabel consoleJV = new JLabel();
    static JLabel dureeDVD = new JLabel();
    static JLabel nbPagesLivre = new JLabel();
    static JComboBox type = new JComboBox();

    public static void main() throws SQLException {
        //creation du Jframe
        JFrame adminpage = new JFrame("Administrateur");
        //fixation des dimensions de la fenetre
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        adminpage.setSize(tailleMoniteur.width, tailleMoniteur.height);

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
        int length = allCategorie.toArray().length;
        Integer[] categories = new Integer[length];
        for (int i = 0; i < categories.length; i++) {
            categories[i] = allCategorie.get(i).getId();
        }

        // table
        columnNames = new String[]{"Titre", "Createur", "Annee de parution", "IdCategorieMedia", "Type"};
        data = new Object[allmedias.toArray().length][columnNames.length];
        for (int i = 0; i < allmedias.toArray().length; i++) {
            for (int j = 0; j < columnNames.length; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = allmedias.get(i).getTitre();
                        break;
                    case 1:
                        data[i][j] = allmedias.get(i).getCreateur();
                        break;
                    case 2:
                        data[i][j] = allmedias.get(i).getAnneeDeParution();
                        break;
                    case 3:
                        data[i][j] = allmedias.get(i).getCategorie();
                        break;
                    case 4:
                        data[i][j] = Media.recupererType(allmedias.get(i).getId());
                }
            }
        }
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model) {
            //desactiver l'editeur de ligne
            public boolean editCellAt(int row, int column, EventObject e) {
                return false;
            }
        };
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane MyScrollPane = new JScrollPane(table);
        MyScrollPane.setBounds(50, 230, 520, 100);
        adminpage.add(MyScrollPane);
        MyScrollPane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.blue));

        //button supprimer
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBounds(70, 360, 100, 30);
        adminpage.add(deleteButton);
        deleteButton.addActionListener(ae -> {
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
            JComboBox<Integer> jComboBox = new JComboBox<Integer>(categories);
            adminpage.getContentPane().add(jComboBox);
            jComboBox.setBounds(660, 480, 40, 40);

            //bouton Valider
            JButton validation = new JButton("Valider");
            adminpage.getContentPane().add(validation);
            validation.setBounds(390, 520, 100, 30);

            //ComoBox
            String[] types = new String[]{"Choisissez le type","CD", "DVD", "JeuVideo", "Livre"};
            type = new JComboBox(types);
            adminpage.add(type);
            type.setBounds(600, 360, 100, 30);
            type.setVisible(true);
            adminpage.setBackground(Color.blue);
            nbChanson = new JTextField("");
            duree = new JTextField("");
            console = new JTextField("");
            nbPages = new JTextField("");
            nbChansonCD = new JLabel("Nombre de chansons : ");
            consoleJV = new JLabel("Console : ");
            dureeDVD = new JLabel("Duree : ");
            nbPagesLivre = new JLabel("Nombre de pages : ");

            type.addActionListener(e -> {
                String typeSelected = type.getSelectedItem().toString();
                String s = e.getActionCommand();
                if (typeSelected.equals("CD")) {
                    nbChansonCD.setVisible(true);
                    nbChanson.setVisible(true);
                    adminpage.add(nbChansonCD);
                    nbChansonCD.setBounds(710, 360, 150, 30);
                    adminpage.add(nbChanson);
                    nbChanson.setBounds(850, 360, 100, 30);
                    duree.setVisible(false);
                    console.setVisible(false);
                    nbPages.setVisible(false);
                    dureeDVD.setVisible(false);
                    consoleJV.setVisible(false);
                    nbPagesLivre.setVisible(false);
                } else if (typeSelected.equals("DVD")) {
                    dureeDVD.setVisible(true);
                    duree.setVisible(true);
                    adminpage.add(duree);
                    duree.setBounds(750, 360, 100, 30);
                    adminpage.add(dureeDVD);
                    dureeDVD.setBounds(702, 360, 50, 30);
                    nbChanson.setVisible(false);
                    console.setVisible(false);
                    nbPages.setVisible(false);
                    nbChansonCD.setVisible(false);
                    consoleJV.setVisible(false);
                    nbPagesLivre.setVisible(false);
                } else if (typeSelected.equals("JeuVideo")) {
                    console.setVisible(true);
                    consoleJV.setVisible(true);
                    adminpage.add(consoleJV);
                    consoleJV.setBounds(702, 360, 70, 30);
                    adminpage.add(console);
                    console.setBounds(760, 360, 100, 30);
                    nbChansonCD.setVisible(false);
                    dureeDVD.setVisible(false);
                    nbPagesLivre.setVisible(false);
                    nbChanson.setVisible(false);
                    nbPages.setVisible(false);
                    duree.setVisible(false);
                } else if (typeSelected.equals("Livre")) {
                    nbPages.setVisible(true);
                    nbPagesLivre.setVisible(true);
                    adminpage.add(nbPagesLivre);
                    nbPagesLivre.setBounds(710, 360, 120, 30);
                    adminpage.add(nbPages);
                    nbPages.setBounds(830, 360, 100, 30);
                    nbChansonCD.setVisible(false);
                    consoleJV.setVisible(false);
                    dureeDVD.setVisible(false);
                    nbChanson.setVisible(false);
                    console.setVisible(false);
                    duree.setVisible(false);
                }
            });
            //action lorsqu'on clique sur le bouton valider
            validation.addActionListener(e -> {
                String s = e.getActionCommand();
                String typeSelectede = type.getSelectedItem().toString();
                if (s.equals("Valider")) {
                    if (typeSelectede.equals("Choisissez le type"))
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner un type");
                    else if (typeSelectede.equals("CD")) {
                        if (titre.getText().equals("") || createur.getText().equals("") || anneeDeParution.getText().equals("") || categorie.getText().equals("") || nbChanson.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs et entrer l'id d'une categorie existante");
                        } else {
                            Media.ajouterMedia();
                            CD.ajouterCD();
                            JOptionPane.showMessageDialog(null, "Votre " + typeSelectede + " a ete bien ajoute");
                        }
                    } else if (typeSelectede.equals("DVD")) {
                        if (titre.getText().equals("") || createur.getText().equals("") || anneeDeParution.getText().equals("") || categorie.getText().equals("") || duree.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs et entrer l'id d'une categorie existante");
                        } else {
                            Media.ajouterMedia();
                            DVD.ajouterDVD();
                            JOptionPane.showMessageDialog(null, "Votre " + typeSelectede + " a ete bien ajoute");
                        }
                    } else if (typeSelectede.equals("JeuVideo")) {
                        if (titre.getText().equals("") || createur.getText().equals("") || anneeDeParution.getText().equals("") || categorie.getText().equals("") || console.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs et entrer l'id d'une categorie existante");
                        } else {
                            Media.ajouterMedia();
                            JeuVideo.ajouterJV();
                            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs et entrer l'id d'une categorie existante");
                        }
                    } else if (typeSelectede.equals("Livre")) {
                        if (titre.getText().equals("") || createur.getText().equals("") || anneeDeParution.getText().equals("") || categorie.getText().equals("") || nbPages.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs et entrer l'id d'une categorie existante");
                        } else {
                            Media.ajouterMedia();
                            Livre.ajouterLivre();
                            JOptionPane.showMessageDialog(null, "Votre " + typeSelectede + " a ete bien ajoute");
                        }
                    }

                }
            });

        });
        adminpage.setVisible(true);
        adminpage.setLayout(null);
    }
}