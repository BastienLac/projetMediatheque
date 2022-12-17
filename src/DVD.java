import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DVD extends Media {
    protected int duree;

    protected DVD(int id, String titre, String createur, int anneeDeParution, int categorie, int duree) {
        super(id, titre, createur, anneeDeParution, categorie);
        this.duree = duree;
    }
    protected static ArrayList<Media> getAll() throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> allmedia = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, m.anneeDeParution, m.idCategorieMedia, d.id, d.duree FROM media m inner join dvd d on m.id = d.id WHERE m.id IN (SELECT id from dvd);");
            ResultSet dvds = st.executeQuery();
            while(dvds.next()) {
                Media dvd = new DVD(dvds.getInt(1), dvds.getString(2), dvds.getString(3), dvds.getInt(4), dvds.getInt(5), dvds.getInt(7));
                allmedia.add(dvd);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();
        return allmedia;
    }
<<<<<<< HEAD

=======
>>>>>>> mohamad
    protected static ArrayList<Media> getMediaParCategorie(int idCateg) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> mediasParCateg = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, m.anneeDeParution, m.idCategorieMedia, d.id, d.duree FROM media m inner join dvd d on m.id = d.id WHERE m.id IN (SELECT id from dvd) and m.idCategorieMedia = ?");
            st.setInt(1, idCateg);
            ResultSet dvds = st.executeQuery();

            while (dvds.next()) {
                Media dvd = new DVD(dvds.getInt(1), dvds.getString(2), dvds.getString(3), dvds.getInt(4), dvds.getInt(5), dvds.getInt(7));
                mediasParCateg.add(dvd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        conn.close();

        return mediasParCateg;
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
