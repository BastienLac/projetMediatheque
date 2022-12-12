import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CD extends Media {
    protected int nombreChanson;

    public CD(int nombreChanson, String titre, String createur, int anneeDeParution, int categorie) {
        super(titre, createur, anneeDeParution, categorie);
        this.nombreChanson = nombreChanson;
    }
    public static void ajouterCD()  {
        String nbChansonText= PageAdmin.nbChanson.getText();
        try {
            Connection conn = MySQLConnection.getConnexion();
            PreparedStatement st = conn.prepareStatement("INSERT INTO cd (`id`,`nombreChanson`) VALUES (?,?)");
            st.setString(1,recupererID());
            st.setString(2, nbChansonText);
            st.executeUpdate();
            conn.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
