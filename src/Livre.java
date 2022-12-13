import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Livre extends Media {
    protected int nombrePage;

    protected Livre(String titre, String createur, int anneeDeParution, int nombrePage) {
        super(titre, createur, anneeDeParution);
        this.nombrePage = nombrePage;
    }


    protected static ArrayList<Media> getMediaParCategorie(int idCateg) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> mediasParCateg = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, m.anneeDeParution, m.idCategorieMedia, l.id, l.nombrePage FROM media m inner join livre l on m.id = l.id WHERE m.id IN (SELECT id from livre) and m.idCategorieMedia = ?");
            st.setInt(1, idCateg);
            ResultSet livres = st.executeQuery();

            while(livres.next()) {
                Media livre = new CD(livres.getString(2), livres.getString(3), livres.getInt(4), livres.getInt(7));
                mediasParCateg.add(livre);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();

        return mediasParCateg;
    }
}
