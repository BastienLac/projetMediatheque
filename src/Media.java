import java.sql.*;
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
            assert conn != null;
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
          if(mediaTitre.equals("") || mediaCreateur.equals("") ||mediaDate.equals("") || typeSelected.equals("")) {}
          else{ PageAdmin.model.insertRow(PageAdmin.table.getRowCount(), new Object[]{mediaTitre, mediaCreateur, mediaDate, mediaCategorie,typeSelected});}

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
    public static String recupererID() {

        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            Connection conn = MySQLConnection.getConnexion();
            assert conn != null;
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
    public static int getIdMedia(Media media) {

        int id = 0;
        try {
            Connection conn = MySQLConnection.getConnexion();
            assert conn != null;
            PreparedStatement st = conn.prepareStatement("SELECT id from media where titre = ? and createur = ?;");
            st.setString(1, media.getTitre());
            st.setString(2, media.getCreateur());
            ResultSet resultMedia = st.executeQuery();
            while (resultMedia.next()) {
                id = resultMedia.getInt(1);
            }
            conn.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
        return id;
    }
    public static  String recupererType(int id) {
        int idType = 0;
        try {
            Connection conn = MySQLConnection.getConnexion();
            assert conn != null;
            PreparedStatement st = conn.prepareStatement("SELECT * from media where id = ?");
            st.setInt(1, id);
            ResultSet media = st.executeQuery();
            while (media.next()) {
                idType = media.getInt(1);
            }
            conn.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
        try {
            Connection conn = MySQLConnection.getConnexion();
            assert conn != null;
            PreparedStatement stt = conn.prepareStatement("SELECT * from livre");
            ResultSet livre = stt.executeQuery();
            ArrayList<Integer> livres = new ArrayList<>();
            while (livre.next()) {
                livres.add(livre.getInt(1));
            }
            if (livres.contains(idType)) {
                return "Livre";
            }
            conn.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        try {
            Connection conn = MySQLConnection.getConnexion();
            assert conn != null;
            PreparedStatement a = conn.prepareStatement("SELECT * from dvd");
            ResultSet dvd = a.executeQuery();
            ArrayList<Integer> dvds = new ArrayList<>();
            while (dvd.next()) {
                dvds.add(dvd.getInt(1));
            }
            if (dvds.contains(idType)) {
                return "DVD";
            }
            conn.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        try {
            Connection conn = MySQLConnection.getConnexion();
            assert conn != null;
            PreparedStatement b = conn.prepareStatement("SELECT * from cd");
            ResultSet cd = b.executeQuery();
            ArrayList<Integer> cds = new ArrayList<>();
            while (cd.next()) {
                cds.add(cd.getInt(1));
            }
            if (cds.contains(idType)) {
                return "CD";
            }
            conn.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        try {
            Connection conn = MySQLConnection.getConnexion();
            assert conn != null;
            PreparedStatement JV = conn.prepareStatement("SELECT * from jeuVideo");
            ResultSet jv = JV.executeQuery();
            ArrayList<Integer> jvs = new ArrayList<>();
            while (jv.next()) {
                jvs.add(jv.getInt(1));
            }
            if (jvs.contains(idType)) {
                return "Jeu Video";
            }
            conn.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
        return "Pas de type";
    }
}
