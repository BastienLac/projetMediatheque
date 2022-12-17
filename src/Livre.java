import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Livre extends Media {
    protected int nombrePage;

    protected Livre(int id, String titre, String createur, int anneeDeParution, int categorie, int nombrePage) {
        super(id, titre, createur, anneeDeParution, categorie);
        this.nombrePage = nombrePage;
    }

    protected static ArrayList<Media> getAll() throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> allmedia = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, m.anneeDeParution, m.idCategorieMedia, l.id, l.nombrePage FROM media m inner join livre l on m.id = l.id WHERE m.id IN (SELECT id from livre);");
            ResultSet livres = st.executeQuery();
            while(livres.next()) {
                Media livre = new livre(livres.getInt(1), livres.getString(2), livres.getString(3), livres.getInt(4), livres.getInt(5), livres.getInt(7));
                allmedia.add(livre);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();
        return allmedia;
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

    protected static ArrayList<Media> getMediaParCategorie(int idCateg) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> mediasParCateg = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, m.anneeDeParution, m.idCategorieMedia, l.id, l.nombrePage FROM media m inner join livre l on m.id = l.id WHERE m.id IN (SELECT id from livre) and m.idCategorieMedia = ?");
            st.setInt(1, idCateg);
            ResultSet livres = st.executeQuery();

            while(livres.next()) {
                Media livre = new Livre(livres.getInt(1), livres.getString(2), livres.getString(3), livres.getInt(4), livres.getInt(5), livres.getInt(7));
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
