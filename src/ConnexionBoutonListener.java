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
        JLabel connexionLoginLabel = new JLabel("Votre Login :", JLabel.CENTER);
        connexionLoginLabel.setFont(new Font("Times New Roman", JLabel.CENTER, 14));
        connexionLoginLabel.setBounds(80,70, 300, 40);
        txtLogin.setBounds(130,100, 200, 40);

        JLabel connexionMDPLabel = new JLabel("Votre mot de passe :", JLabel.CENTER);
        connexionMDPLabel.setFont(new Font("Times New Roman", JLabel.CENTER, 14));
        connexionMDPLabel.setBounds(80,140, 300, 40);
        txtMdp.setBounds(130,170, 200, 40);
        connexionButton.setBounds(130, 230, 200, 40);
        inscriptionButton.setBounds(20, 400, 150, 30);
        inscriptionButton.addActionListener(cbl);

        connexionPanel.add(connexionLoginLabel);
        connexionPanel.add(connexionMDPLabel);
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
                    try {
                        PageAdmin.main();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
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
                JOptionPane.showMessageDialog(null, "Erreur, mdp ou login incorrect. Reessayez");
        } else if (s.equals("S'inscrire")) {
             InscriptionPage.main();
        }else if (s.equals("Valider")) {
            Utilisateur.inscrireUtilisateur();
        }
    }
}
