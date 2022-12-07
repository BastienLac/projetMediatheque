import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;

public class PageAdmin implements ActionListener {
    private static Object[][] data;
    private static String[] columnNames;

    public static void main(){
        JFrame adminpage = new JFrame("Administarateur");
        adminpage.setSize(1000, 1000);
        adminpage.setLocation(100, 50);

        // label bienvenue
        JLabel bienvenue = new JLabel("Bienvenue à la page administateur : ");
        adminpage.setLayout(null);
        bienvenue.setBounds(50, 70, 300, 130);
        adminpage.add(bienvenue);

        //checkbox
        //String s1[] = { "DVD", "JeuVideo", "Livre" };
        //JComboBox combobox = new JComboBox(s1);
        //combobox.setBounds(50, 180, 200, 30);
        //adminpage.add(combobox);

        //suppression
        JLabel suppression = new JLabel("Pour supprimer une ligne, selectionner la puis cliquer sur supprimer ");
        suppression.setBounds(50, 105, 450, 150);
        adminpage.add(suppression);

        // table
        data = new Object[][] {{"1", "CD1"}, {"2", "CD2"}, {"3", "CD3"}, {"4", "CD4"}};
        columnNames = new String[] {"ID", "Name"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JScrollPane MyScrollPane= new JScrollPane(table);
        MyScrollPane.setBounds(50, 230, 520, 100);
        adminpage.add(MyScrollPane);


        //button
        JButton button= new JButton("Supprimer");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // check for selected row first
                if(table.getSelectedRow() != -1) {
                    // remove selected row from the model
                    model.removeRow(table.getSelectedRow());
                    JOptionPane.showMessageDialog(null, "Vous avez bien supprimé le media");
                }
            }
        });
        button.setBounds(220, 360, 100, 30);
        adminpage.add(button);

        JButton addData= new JButton("Ajouter");
        addData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {

                JTextField id = new JTextField(16);
                JTextField Name = new JTextField(16);
                id.setBounds(465, 360, 100, 30);
                Name.setBounds(565, 360, 100, 30);
                adminpage.add(id);
                adminpage.add(Name);
                JButton validation = new JButton("Valider");
                validation.setBounds(665, 360, 180, 30);
                adminpage.add(validation);

                //if(Name.getText().equals("")) {Name.setText("entrer le nom");}
                //if(id.getText().equals("")) {id.setText("entrer l'id");}
                validation.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s = e.getActionCommand();
                        if (s.equals("Valider")) {
                            String identifiant = id.getText();
                            String nom = Name.getText();
                            model.insertRow(table.getRowCount(),new Object[]{identifiant,nom});

                }
            }
                });
            }
        });
        addData.setBounds(360, 360, 100, 30);
        adminpage.add(addData);
        adminpage.setVisible(true);
        adminpage.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
