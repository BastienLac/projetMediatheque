import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Media {
    protected String titre;
    protected String createur;
    protected int anneeDeParution;
    protected CategorieMedia categorie;

    protected Media(String titre, String createur, int anneeDeParution) {
        this.titre = titre;
        this.createur = createur;
        this.anneeDeParution = anneeDeParution;
    }
    protected static ArrayList<Media> getMediaParCategorie(int idCateg) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> mediasParCateg = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("SELECT titre, createur, anneeDeParution FROM media m WHERE idCategorieMedia = ?");
            st.setInt(1, idCateg);
            ResultSet medias = st.executeQuery();

            while(medias.next()) {
                Media cd = new Media(medias.getString(1), medias.getString(2), medias.getInt(3));
                mediasParCateg.add(cd);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();

        return mediasParCateg;
    };
}
