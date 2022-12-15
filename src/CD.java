import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CD extends Media {
    protected int nombreChanson;
    protected CD(int id, String titre, String createur, int anneeDeParution, int categorie, int nombreChanson) {
        super(id, titre, createur, anneeDeParution, categorie);
        this.nombreChanson = nombreChanson;
    }
    protected static ArrayList<Media> getAll() throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> allmedia = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, m.anneeDeParution, m.idCategorieMedia, c.id, c.nombreChanson FROM media m inner join cd c on m.id = c.id WHERE m.id IN (SELECT id from cd);");
            ResultSet cds = st.executeQuery();
            while(cds.next()) {
                CD cd = new CD(cds.getInt(1), cds.getString(2), cds.getString(3), cds.getInt(4), cds.getInt(5), cds.getInt(7));
                allmedia.add(cd);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();
        return allmedia;
    }
    protected static ArrayList<Media> getMediaParCategorie(int idCateg) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> mediasParCateg = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, m.anneeDeParution, m.idCategorieMedia, c.id, c.nombreChanson FROM media m inner join cd c on m.id = c.id WHERE m.id IN (SELECT id from cd) and m.idCategorieMedia = ?");
            st.setInt(1, idCateg);
            ResultSet cds = st.executeQuery();

            while(cds.next()) {
                Media cd = new CD(cds.getInt(1), cds.getString(2), cds.getString(3), cds.getInt(4), cds.getInt(5), cds.getInt(7));
                mediasParCateg.add(cd);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();

        return mediasParCateg;
    }
    public static void ajouterCD() {
        String nbChansonText = PageAdmin.nbChanson.getText();
        try {
            Connection conn = MySQLConnection.getConnexion();
            PreparedStatement st = conn.prepareStatement("INSERT INTO cd (`id`,`nombreChanson`) VALUES (?,?)");
            st.setString(1, recupererID());
            st.setString(2, nbChansonText);
            st.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}