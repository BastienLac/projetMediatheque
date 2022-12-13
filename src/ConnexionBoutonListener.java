import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class ConnexionBoutonListener implements ActionListener {
    static JButton connexionButton = new JButton("Se connecter");
    static JTextField txtLogin = new JTextField(16);
    static JTextField txtMdp = new JPasswordField(16);
    public static void main(){
            JPanel connexionPanel = new JPanel();
            connexionPanel.setLayout(null);
            JFrame jf = new JFrame("Connexion");
            jf.setSize(500,500);

            JLabel connexionLabel = new JLabel("Bonjour, veuillez vous connecter", JLabel.CENTER);
            connexionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            connexionLabel.setBounds(80,30, 300, 40);

            ConnexionBoutonListener cbl = new ConnexionBoutonListener();
            connexionButton.addActionListener(cbl);
            txtLogin.setBounds(130,100, 200, 40);
            txtMdp.setBounds(130,150, 200, 40);
            connexionButton.setBounds(130, 200, 200, 40);

            connexionPanel.add(connexionLabel);
            connexionPanel.add(txtLogin);
            connexionPanel.add(txtMdp);
            connexionPanel.add(connexionButton);
            jf.add(connexionPanel);
            jf.setVisible(true);
            jf.setLocationRelativeTo(null);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();
        if (s.equals("Se connecter")) {
            String login = txtLogin.getText();
            String mdp = txtMdp.getText();
            Connection conn = MySQLConnection.getConnexion();
            try {
                PreparedStatement st = conn.prepareStatement("SELECT * FROM utilisateur WHERE login=? and mdp=?");
                st.setString(1, login);
                st.setString(2, mdp);
                ResultSet rs = st.executeQuery();
                if(rs.next()){
                    PageUtilisateur.main(new int[] {rs.getInt(1)});
                }
            } catch (Exception ex) {
                System.out.println(e);
            }
        }
    }
}
