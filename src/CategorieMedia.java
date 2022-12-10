import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.awt.Font.BOLD;

public class CategorieMedia {
    private String nom;

    public CategorieMedia(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public static ArrayList<CategorieMedia> getAllCategorie() throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<CategorieMedia> allCategorie = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM categoriemedia");
            ResultSet categorieMedia = st.executeQuery();

            while(categorieMedia.next()) {
                CategorieMedia categ = new CategorieMedia(categorieMedia.getString(1));
                allCategorie.add(categ);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();

        return allCategorie;
    }
}
