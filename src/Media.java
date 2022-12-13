import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Media {
    protected String titre;
    protected String createur;
    protected int anneeDeParution;
    protected CategorieMedia categorie;

    protected Media(String titre, String createur, int anneeDeParution) {
        this.titre = titre;
        this.createur = createur;
        this.anneeDeParution = anneeDeParution;
    }
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

        System.out.println("size " + allMedias.size());
        return allMedias;
    };
}
