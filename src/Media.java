import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Media {
    protected int id;
    protected String titre;
    protected String createur;
    protected int anneeDeParution;
    protected int categorieId;
    protected Media(int id, String titre, String createur, int anneeDeParution, int categorie) {
        this.id = id;
        this.titre = titre;
        this.createur = createur;
        this.anneeDeParution = anneeDeParution;
        this.categorieId = categorie;
    }

    /**
     *
     * @param idCateg {int} id de la catégorie
     * @return les médias de la catégorie passée en paramètre
     * @throws SQLException
     */
    protected static ArrayList<Media> getMediaParCategorie(int idCateg) throws SQLException {
        ArrayList<Media> allMedias = new ArrayList<>();
        if (CD.getMediaParCategorie(idCateg).size() > 0){
            allMedias.addAll(CD.getMediaParCategorie(idCateg));
        }

        if (DVD.getMediaParCategorie(idCateg).size() > 0){
            allMedias.addAll(DVD.getMediaParCategorie(idCateg));
        }

        if (Livre.getMediaParCategorie(idCateg).size() > 0){
            allMedias.addAll(Livre.getMediaParCategorie(idCateg));
        }

        if (JeuVideo.getMediaParCategorie(idCateg).size() > 0){
            allMedias.addAll(JeuVideo.getMediaParCategorie(idCateg));
        }
        return allMedias;
    };

    /**
     *
     * @return tous les médias
     * @throws SQLException
     */
    protected static ArrayList<Media> getAll() throws SQLException {
        ArrayList<Media> allMedias = new ArrayList<>();
        if (CD.getAll().size() > 0){
            allMedias.addAll(CD.getAll());
        }

        if (DVD.getAll().size() > 0){
            allMedias.addAll(DVD.getAll());
        }

        if (Livre.getAll().size() > 0){
            allMedias.addAll(Livre.getAll());
        }

        if (JeuVideo.getAll().size() > 0){
            allMedias.addAll(JeuVideo.getAll());
        }
        return allMedias;
    }

    /**
     * Supprime un média
     */
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

    /**
     * Ajoute un média
     */
    public static void ajouterMedia() {
        String mediaTitre = PageAdmin.titre.getText();
        String mediaCreateur = PageAdmin.createur.getText();
        String mediaDate = PageAdmin.anneeDeParution.getText();
        String mediaCategorie = PageAdmin.categorie.getText();
        String typeSelected = PageAdmin.type.getSelectedItem().toString();
        ArrayList<String> typesSelected = new ArrayList<String>();
        typesSelected.add(typeSelected);
        PageAdmin.model.insertRow(PageAdmin.table.getRowCount(), new Object[]{mediaTitre, mediaCreateur, mediaDate, mediaCategorie, typeSelected});

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

    /**
     *
     * @return Récupère l'id du média
     */
    public static String recupererID() {
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

    /**
     *
     * @param id {int} id du média
     * @return le type de média
     */
    public static String recupererType(int id) {
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

    /**
     *
     * @param titre {String} titre média
     * @param createur {String} createur média
     * @param anneDeParution {int} année de parution du média
     * @return le média corrspondant aux paramètres
     * @throws SQLException
     */
    public static Media findMedia(String titre, String createur, int anneDeParution) throws SQLException {
        for (int i = 0; i < Media.getAll().size(); i++) {
            if (titre.equals(Media.getAll().get(i).getTitre()) && createur.equals(Media.getAll().get(i).getCreateur()) && anneDeParution == Media.getAll().get(i).getAnneeDeParution()) {
                return Media.getAll().get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param id {int} id média
     * @return {Media|null} Le média corrspondant à l'id. sinon null
     * @throws SQLException
     */
    public static Media findMediaById(int id) throws SQLException {
        for (int i = 0; i < Media.getAll().size(); i++) {
            if (id == Media.getAll().get(i).getId()) {
                return Media.getAll().get(i);
            }
        }
        return null;
    }

    public int getId() {
        return id;
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
        return categorieId;
    }
}
