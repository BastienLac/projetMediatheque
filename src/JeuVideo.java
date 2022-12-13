import java.sql.Connection;
import java.sql.PreparedStatement;

public class JeuVideo extends Media {
    protected String console;
    public JeuVideo(String titre, String console, String createur, int anneeDeParution, int categorie) {
        super(titre, createur, anneeDeParution, categorie);
        this.console=console;
    }
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
