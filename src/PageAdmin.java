import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

public class PageAdmin implements ActionListener {

    public static void main(){
            JPanel connexionPanel = new JPanel();
            JFrame jf = new JFrame("Administrateur");

            JLabel PageAdminLabel = new JLabel("Bonjour, bienvenue a votre page administrateur", JLabel.CENTER);
            PageAdminLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
            jf.setSize(tailleMoniteur.width, tailleMoniteur.height);


            PageAdmin cbl = new PageAdmin();;
            jf.add(PageAdminLabel);
            jf.setVisible(true);
            jf.setLocationRelativeTo(null);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
