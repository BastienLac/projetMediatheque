import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PageAdmin implements ActionListener {

    public static void main(){
        JFrame adminpage = new JFrame("Administarateur");
        adminpage.setSize(1000, 1000);
        adminpage.setLocation(200, 150);
        JLabel labelM = new JLabel("Bienvenue à la page administateur : ");
        adminpage.setLayout(null);

        labelM.setBounds(50, 50, 200, 30);
        JTextField text_field = new JTextField();
        text_field.setBounds(50, 100, 200, 30);
        JFrame.setDefaultLookAndFeelDecorated(true);
        adminpage.add(labelM);
        adminpage.add(text_field);
        adminpage.setVisible(true);
        adminpage.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
