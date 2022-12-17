import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategorieMedia {
    private int id;
    private String nom;

    public CategorieMedia(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() { return id; }

    public String getNom() {
        return nom;
    }

    /**
     * Permet de récuperer toutes les catégories de média de la base de données
     * @return {CategorieMedia} Liste de catégories de média
     */
    public static ArrayList<CategorieMedia> getAllCategorie() throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<CategorieMedia> allCategorie = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM categoriemedia");
            ResultSet categorieMedia = st.executeQuery();

            while (categorieMedia.next()) {
                CategorieMedia categ = new CategorieMedia(categorieMedia.getInt(1), categorieMedia.getString(2));
                allCategorie.add(categ);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        conn.close();

        return allCategorie;
    }

    /**
     * Permet de retourner une catégorie selon un nom passé en paramètre
     * @param {string} nomCateg - nom de la catégorie voulue
     * @return {Categorie} La catégorie correspondante au nom
     */
    public static CategorieMedia getCategorieParNom(String nomCateg) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        CategorieMedia maCategorie = null;
        try {
            PreparedStatement st = conn.prepareStatement("SELECT id, nom FROM categoriemedia where nom = ?");
            st.setString(1, nomCateg);
            ResultSet categorieMedia = st.executeQuery();

            while (categorieMedia.next()) {
                maCategorie = new CategorieMedia(categorieMedia.getInt(1), categorieMedia.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        conn.close();

        return maCategorie;
    }

}
