import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PageUtilisateur {
    public static void main() throws SQLException {
        JFrame jf = new JFrame("Page utilisateur");

        // dimension JFrame
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setSize(tailleMoniteur.width, tailleMoniteur.height);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jLabelCategorie = new JLabel("Catégorie de médias");
        jLabelCategorie.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jLabelCategorie.setBounds(50, 50, 200, 50);
        jf.getContentPane().add(jLabelCategorie);

        // Liste des categories media
        ArrayList<CategorieMedia> allCategorie = CategorieMedia.getAllCategorie();
        String[] categories = new String[allCategorie.toArray().length];
        for (int i = 0; i < categories.length; i++) {
            categories[i] = allCategorie.get(i).getNom();
        }

        // Ajout Combo Box a la fenetre
        JComboBox<String> jComboBox = new JComboBox<>(categories);
        jComboBox.setBounds(50, 100, 300, 50);
        jf.getContentPane().add(jComboBox);

        JLabel jLabelChooseCategorie = new JLabel();
        jLabelChooseCategorie.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jLabelChooseCategorie.setBounds(50, 150, 300, 50);
        jf.getContentPane().add(jLabelChooseCategorie);

        jf.setVisible(true);

        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategorie = "Tu as sélectionné " + jComboBox.getItemAt(jComboBox.getSelectedIndex());
                jLabelChooseCategorie.setText(selectedCategorie);

            }
        });
    }
}
