public class Utilisateur {
    private String nom;
    private String prenom;
    private String nomUtilisateur;
    private String motDePasse;
    private boolean estAdmin;

    public Utilisateur(String nom, String prenom, String nomUtilisateur, String motDePasse, boolean estAdmin) {
        this.nom = nom;
        this.prenom = prenom;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.estAdmin = estAdmin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public boolean isEstAdmin() {
        return estAdmin;
    }
}
