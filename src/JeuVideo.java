import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JeuVideo extends Media {
    protected String console;
    protected JeuVideo(int id, String titre, String createur, int anneeDeParution, int categorie, String console) {
        super(id, titre, createur, anneeDeParution, categorie);
        this.console = console;
    }

    /**
     *
     * @return tous les JEUVIDEO présents dans la base de donneés
     * @throws SQLException
     */
    protected static ArrayList<Media> getAll() throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> allmedia = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, m.anneeDeParution, m.idCategorieMedia, j.id, j.console FROM media m inner join jeuvideo j on m.id = j.id WHERE m.id IN (SELECT id from jeuvideo);");
            ResultSet jvs = st.executeQuery();
            while(jvs.next()) {
                Media jv = new JeuVideo(jvs.getInt(1), jvs.getString(2), jvs.getString(3), jvs.getInt(4), jvs.getInt(5), jvs.getString(7));
                allmedia.add(jv);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();
        return allmedia;
    }

    /**
     *
     * @param idCateg
     * @return tableau contenant tous les JEUVIDEO de la catégorie en question
     * @throws SQLException
     */
    protected static ArrayList<Media> getMediaParCategorie(int idCateg) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Media> mediasParCateg = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, m.anneeDeParution, m.idCategorieMedia, j.id, j.console FROM media m inner join jeuvideo j on m.id = j.id WHERE m.id IN (SELECT id from jeuvideo) and m.idCategorieMedia = ?;");
            st.setInt(1, idCateg);
            ResultSet jvs = st.executeQuery();

            while (jvs.next()) {
                Media jv = new JeuVideo(jvs.getInt(1), jvs.getString(2), jvs.getString(3), jvs.getInt(4), jvs.getInt(5), jvs.getString(7));
                mediasParCateg.add(jv);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        conn.close();

        return mediasParCateg;
    }

    /**
     * cette fonction insert un nouveau jeuvideo en fonction de l'id du média récupéré
     */
    public static void ajouterJV()  {
        String consoleText = PageAdmin.console.getText();
        try {
            Connection conn = MySQLConnection.getConnexion();
            PreparedStatement st = conn.prepareStatement("INSERT INTO jeuvideo (`id`,`console`) VALUES (?,?)");
            st.setString(1, Media.recupererID());
            st.setString(2, consoleText);
            st.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
