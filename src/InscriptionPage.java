import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

public class InscriptionPage implements ActionListener {
    static JButton inscriptionButton = new JButton("Valider");
    static JTextField txtNom = new JTextField(16);
    static JTextField txtPrenom = new JTextField(16);
    static JTextField txtLogin = new JTextField(16);
    static JTextField txtMdp = new JPasswordField(16);
    static JFrame jf = new JFrame("Inscription");
    public static void main(){
        JPanel inscriptionPanel = new JPanel();
        inscriptionPanel.setLayout(null);

        jf.setSize(500,500);

        JLabel inscriptionLabel = new JLabel("Votre inscription :", JLabel.CENTER);
        inscriptionLabel.setFont(new Font("Times New Roman", JLabel.CENTER, 16));
        inscriptionLabel.setBounds(80,30, 300, 40);

        JLabel inscriptionNomLabel = new JLabel("Votre Nom :", JLabel.CENTER);
        inscriptionNomLabel.setFont(new Font("Times New Roman", JLabel.CENTER, 14));
        inscriptionNomLabel.setBounds(80,70, 300, 40);
        txtNom.setBounds(130,100, 200, 40);

        JLabel inscriptionPrenomLabel = new JLabel("Votre Prenom :", JLabel.CENTER);
        inscriptionPrenomLabel.setFont(new Font("Times New Roman", JLabel.CENTER, 14));
        inscriptionPrenomLabel.setBounds(80,140, 300, 40);
        txtPrenom.setBounds(130,170, 200, 40);

        JLabel inscriptionLoginLabel = new JLabel("Votre Login :", JLabel.CENTER);
        inscriptionLoginLabel.setFont(new Font("Times New Roman", JLabel.CENTER, 14));
        inscriptionLoginLabel.setBounds(80,210, 300, 40);
        txtLogin.setBounds(130,240, 200, 40);

        JLabel inscriptionMDPLabel = new JLabel("Votre mot de passe :", JLabel.CENTER);
        inscriptionMDPLabel.setFont(new Font("Times New Roman", JLabel.CENTER, 14));
        inscriptionMDPLabel.setBounds(80,280, 300, 40);
        txtMdp.setBounds(130,310, 200, 40);

        inscriptionButton.setBounds(130, 400, 200, 40);
        ConnexionBoutonListener cbl = new ConnexionBoutonListener();
        inscriptionButton.addActionListener(cbl);

        inscriptionPanel.add(inscriptionLabel);
        inscriptionPanel.add(inscriptionNomLabel);
        inscriptionPanel.add(inscriptionPrenomLabel);
        inscriptionPanel.add(inscriptionLoginLabel);
        inscriptionPanel.add(inscriptionMDPLabel);
        inscriptionPanel.add(inscriptionButton);
        inscriptionPanel.add(txtNom);
        inscriptionPanel.add(txtPrenom);
        inscriptionPanel.add(txtLogin);
        inscriptionPanel.add(txtMdp);
        jf.add(inscriptionPanel);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
    }
}
