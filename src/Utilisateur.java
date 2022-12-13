import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Utilisateur {
    private String nom;
    private String prenom;
    private String nomUtilisateur;
    private String motDePasse;
    private boolean estAdmin;

    public Utilisateur(String nom, String prenom, String nomUtilisateur, String motDePasse, boolean estAdmin) {
        this.nom = nom;
        this.prenom = prenom;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.estAdmin = estAdmin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public boolean isEstAdmin() {
        return estAdmin;
    }

    /**
     *
     * @param {string} unLogin - Login de l'utilisateur
     * @param {string} unMDP - Mot de passe de l'utilisateur
     * @return {Utilisateur|null} L'utilisateur correspondant aux informations fournies. Null sinon.
     */
    public static Utilisateur getUtilisateur(String unLogin, String unMDP){
        Connection conn = MySQLConnection.getConnexion();
        try{
            PreparedStatement st = conn.prepareStatement("SELECT * FROM utilisateur WHERE login=? and mdp=?");
            st.setString(1, unLogin);
            st.setString(2, unMDP);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                boolean isAdmin = rs.getInt(6) == 1
                        ? true
                        : false;
                Utilisateur monUtilisateur = new Utilisateur(rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5), isAdmin);
                return monUtilisateur;
            }
            return null;
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    /**
     * Permet d'inscrire un utilisateur selon les données saisies par l'utilisateur
     */
    public static void inscrireUtilisateur(){
        String nom = InscriptionPage.txtNom.getText();
        String prenom = InscriptionPage.txtPrenom.getText();
        String login = InscriptionPage.txtLogin.getText();
        String mdp = InscriptionPage.txtMdp.getText();
        Connection conn = MySQLConnection.getConnexion();
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO utilisateur (prenom, nom, login, mdp, estAdmin) VALUES (?,?,?,?,0)");
            st.setString(1, prenom);
            st.setString(2, nom);
            st.setString(3, login);
            st.setString(4, mdp);
            st.execute();
            conn.close();
            InscriptionPage.jf.setVisible(false);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
