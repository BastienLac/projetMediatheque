import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Media {
    static ArrayList<String> typesSelected = new ArrayList<String>();
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
      }
      public static  void ajouterMedia() {

          String mediaTitre = PageAdmin.titre.getText();
          String mediaCreateur = PageAdmin.createur.getText();
          String mediaDate = PageAdmin.anneeDeParution.getText();
          String mediaCategorie = PageAdmin.categorie.getText();
          String typeSelected = PageAdmin.type.getSelectedItem().toString();
          ArrayList<String> typesSelected = new ArrayList<String>();
          typesSelected.add(typeSelected);
          PageAdmin.model.insertRow(PageAdmin.table.getRowCount(), new Object[]{mediaTitre, mediaCreateur, mediaDate, mediaCategorie,typeSelected});

          try {
              Connection conn = MySQLConnection.getConnexion();
              assert conn != null;
              PreparedStatement st = conn.prepareStatement("INSERT INTO media (`titre`, `createur`, `anneeDeParution`, `idCategorieMedia`) VALUES (?,?,?,?)");
              st.setString(1, mediaTitre);
              st.setString(2, mediaCreateur);
              st.setString(3, mediaDate);
              st.setString(4, mediaCategorie);
              st.executeUpdate();
              conn.close();
          } catch (Exception exception) {
              System.out.println(exception);
          }

      }
    public static  String recupererID() {

        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            Connection conn = MySQLConnection.getConnexion();
            PreparedStatement st = conn.prepareStatement("SELECT * from media");
            ResultSet media = st.executeQuery();
            arrayList = new ArrayList<String>();
            while (media.next()) {
                arrayList.add(media.getString(1));
            }
            conn.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
        return arrayList.get(PageAdmin.model.getRowCount()-1);
    }
}
