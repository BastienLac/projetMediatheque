import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Media {
    protected String titre;
    protected String createur;
    protected int anneeDeParution;
    protected int categorie;

    public Media(String titre, String createur, int anneeDeParution, int categorie) {
        this.titre = titre;
        this.createur = createur;
        this.anneeDeParution = anneeDeParution;
        this.categorie = categorie;
    }

    public String getTitre() {
        return titre;
    }

    public String getCreateur() {
        return createur;
    }

    public int getAnneeDeParution() {
        return anneeDeParution;
    }

    public int getCategorie() {
        return categorie;
    }

    public static ArrayList<Media> getAll() throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> allmedia = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM media");
            ResultSet media = st.executeQuery();
            while(media.next()) {
                Media _media = new Media(media.getString(2),media.getString(3),media.getInt(4),media.getInt(5));
                allmedia.add(_media);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();
        return allmedia;
    }
}
