import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CD extends Media {
    protected int nombreChanson;

    protected CD(String titre, String createur, int anneeDeParution, int nombreChanson) {
        super(titre, createur, anneeDeParution);
        this.nombreChanson = nombreChanson;
    }

    protected static ArrayList<Media> getMediaParCategorie(int idCateg) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> mediasParCateg = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, m.anneeDeParution, m.idCategorieMedia, c.id, c.nombreChanson FROM media m inner join cd c on m.id = c.id WHERE m.id IN (SELECT id from cd) and m.idCategorieMedia = ?");
            st.setInt(1, idCateg);
            ResultSet cds = st.executeQuery();

            while(cds.next()) {
                Media cd = new CD(cds.getString(2), cds.getString(3), cds.getInt(4), cds.getInt(7));
                mediasParCateg.add(cd);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();

        return mediasParCateg;
    }
}
