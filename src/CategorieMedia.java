import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public static CategorieMedia getCategorieParNom(String nomCateg) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        //Map<String, Integer> categ = new HashMap<String, Integer>();
        CategorieMedia maCategorie = null;
        try {
            PreparedStatement st = conn.prepareStatement("SELECT id, nom FROM categoriemedia where nom = ?");
            st.setString(1, nomCateg);
            ResultSet categorieMedia = st.executeQuery();

            while (categorieMedia.next()) {
                //categ.put(categorieMedia.getString(2), categorieMedia.getInt(1));
                maCategorie = new CategorieMedia(categorieMedia.getInt(1), categorieMedia.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        conn.close();

        return maCategorie;
    }

}
