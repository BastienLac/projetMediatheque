import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
public class ConnexionBoutonListener implements ActionListener {
    static JButton connexionButton = new JButton("Se connecter");
    static JButton inscriptionButton = new JButton("S'inscrire");
    static JTextField txtLogin = new JTextField(16);
    static JTextField txtMdp = new JPasswordField(16);
    static JFrame jf = new JFrame("Connexion");
    public static void main(){
            JPanel connexionPanel = new JPanel();
            connexionPanel.setLayout(null);
            jf.setSize(500,500);

            JLabel connexionLabel = new JLabel("Bonjour, veuillez vous connecter", JLabel.CENTER);
            connexionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            connexionLabel.setBounds(80,30, 300, 40);

            ConnexionBoutonListener cbl = new ConnexionBoutonListener();
            connexionButton.addActionListener(cbl);
            txtLogin.setBounds(130,100, 200, 40);
            txtMdp.setBounds(130,150, 200, 40);
            connexionButton.setBounds(130, 200, 200, 40);
            inscriptionButton.setBounds(20, 400, 150, 30);
            inscriptionButton.addActionListener(cbl);

            connexionPanel.add(connexionLabel);
            connexionPanel.add(txtLogin);
            connexionPanel.add(txtMdp);
            connexionPanel.add(connexionButton);
            connexionPanel.add(inscriptionButton);
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
            Utilisateur utilisateur1 = Utilisateur.getUtilisateur(login, mdp);
            if(utilisateur1 != null){
                if(utilisateur1.isEstAdmin()){
                    // page admin
                }
                else{
                    try {
                        PageUtilisateur.main();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            else
                txtLogin.setText("TES PAS CO");
        } else if (s.equals("S'inscrire")) {
             InscriptionPage.main();
        } else if (s.equals("Valider")) {
            Utilisateur.inscrireUtilisateur();
        }
    }
}
