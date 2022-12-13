import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
public class DVD extends Media {
    protected int duree;

    public DVD(int duree,String titre, String createur, int anneeDeParution, int categorie) {
        super(titre, createur, anneeDeParution, categorie);
        this.duree = duree;
    }
    public static void ajouterDVD()  {
        String dureeText = PageAdmin.duree.getText();
        try {
            Connection conn = MySQLConnection.getConnexion();
            PreparedStatement st = conn.prepareStatement("INSERT INTO dvd (`id`,`duree`) VALUES (?,?)");
            st.setString(1, Media.recupererID());
            st.setString(2, dureeText);
            st.executeUpdate();

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
