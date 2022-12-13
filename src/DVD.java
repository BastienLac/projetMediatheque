import java.util.ArrayList;

public class DVD extends Media {
    protected int duree;

    protected DVD(String titre, String createur, int anneeDeParution, int duree) {
        super(titre, createur, anneeDeParution);
        this.duree = duree;
    }


    protected static ArrayList<Media> getMediaParCategorie(CategorieMedia categorie) {
        return null;
    }
}
