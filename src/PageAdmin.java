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
        JLabel suppression = new JLabel("Selectionner le media que vous voulez supprimer  ");
        suppression.setBounds(50, 105, 350, 150);
        adminpage.add(suppression);

        // table
        data = new Object[][] {{"101", "Ramesh"}, {"102", "Adithya"}, {"103", "Jai"}, {"104", "Sai"}};
        columnNames = new String[] {"ID", "Name"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        adminpage.add(table);
        table.setBounds(50, 230, 520, 400);


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
        button.setBounds(720, 360, 100, 30);
        adminpage.add(button);

        JButton addData= new JButton("Ajouter");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                model.insertRow(table.getRowCount(),new Object[]{"Sushil","600"});
            }
        });
        addData.setBounds(760, 360, 100, 30);
        adminpage.add(addData);

        adminpage.setVisible(true);
        adminpage.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
