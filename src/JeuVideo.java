import java.util.ArrayList;

public class JeuVideo extends Media {
    protected String console;

    protected JeuVideo(String titre, String createur, int anneeDeParution, String console) {
        super(titre, createur, anneeDeParution);
        this.console = console;
    }

    protected static ArrayList<Media> getMediaParCategorie(CategorieMedia categorie) {
        return null;
    }
}
