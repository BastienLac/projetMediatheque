import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CD extends Media {
    protected int nombreChanson;

    protected CD(String titre, String createur, int anneeDeParution, int nombreChanson) {
        super(titre, createur, anneeDeParution);
        this.nombreChanson = nombreChanson;
    }

    protected static ArrayList<Media> getMediaParCategorie(CategorieMedia categorie) {
        return null;
    }
}
