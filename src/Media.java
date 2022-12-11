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

    public Media() {
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
      public static void supprimerMedia() {
        try {
              Connection conn = MySQLConnection.getConnexion();
              assert conn != null;
              PreparedStatement st = conn.prepareStatement("DELETE FROM media WHERE titre= ? AND createur=?");
              st.setString(1, PageAdmin.table.getValueAt(PageAdmin.table.getSelectedRow(), 0).toString());
              st.setString(2, PageAdmin.table.getValueAt(PageAdmin.table.getSelectedRow(), 1).toString());
              st.executeUpdate();
          } catch (Exception e) {
              System.out.println(e);
          }
        System.out.println(PageAdmin.table.getValueAt(PageAdmin.table.getSelectedRow(), 0).toString());
      }
      public static void ajouterMedia() {

          String mediaTitre = PageAdmin.titre.getText();
          String mediaAnnee = PageAdmin.categorie.getText();
          String mediaDate = PageAdmin.anneeDeParution.getText();
          String mediaCategorie = PageAdmin.categorie.getText();
          PageAdmin.model.insertRow(PageAdmin.table.getRowCount(), new Object[]{mediaTitre, mediaAnnee, mediaDate, mediaCategorie});

          try {
              Connection conn = MySQLConnection.getConnexion();
              assert conn != null;
              PreparedStatement st = conn.prepareStatement("INSERT INTO media (`titre`, `createur`, `anneeDeParution`, `idCategorieMedia`) VALUES (?,?,?,?)");
              st.setString(1, mediaTitre);
              st.setString(2, mediaAnnee);
              st.setString(3, mediaDate);
              st.setString(4, mediaCategorie);
              st.executeUpdate();
          } catch (Exception exception) {
              System.out.println(exception);
          }

      }
}
