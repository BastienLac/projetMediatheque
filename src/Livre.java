import java.util.ArrayList;

public class Livre extends Media {
    protected int nombrePage;

    protected Livre(String titre, String createur, int anneeDeParution, int nombrePage) {
        super(titre, createur, anneeDeParution);
        this.nombrePage = nombrePage;
    }


    protected static ArrayList<Media> getMediaParCategorie(CategorieMedia categorie) {
        return null;
    }
}
