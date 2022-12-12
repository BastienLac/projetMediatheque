import java.sql.Connection;
import java.sql.PreparedStatement;

public class Livre extends Media {
    protected int nombrePage;

    public Livre(int nombrePage, String titre, String createur, int anneeDeParution, int categorie) {
        super(titre, createur, anneeDeParution, categorie);
        this.nombrePage=nombrePage;
    }

    public static void ajouterLivre()  {
        String nbPagesText = PageAdmin.nbPages.getText();
        try {
            Connection conn = MySQLConnection.getConnexion();
            PreparedStatement st = conn.prepareStatement("INSERT INTO livre (`id`,`nombrePage`) VALUES (?,?)");
            st.setString(1, recupererID());
            st.setString(2, nbPagesText);
            st.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

}
