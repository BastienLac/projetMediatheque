import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Reservation {
    private int id;
    private int idMedia;
    private int idUtilisateur;
    private String dateDebut;
    private String dateFin;

    public Reservation(int idMedia, int idUtilisateur, String dateDebut, String dateFin) {
        this.idMedia = idMedia;
        this.idUtilisateur = idUtilisateur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Reservation(int id, int idMedia, int idUtilisateur, String dateDebut, String dateFin) {
        this.id = id;
        this.idMedia = idMedia;
        this.idUtilisateur = idUtilisateur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId(){ return id; }
    public int getIdMedia() {
        return idMedia;
    }
    public int getIdUtilisateur() {
        return idUtilisateur;
    }
    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    /**
     *
     * @param idUtilisateur {int} id de l'utilisateur
     * @return {ArrayList<Reservation>} Renvoie la liste des réservations de l'utilisateur
     * @throws SQLException
     */
    public static ArrayList<Reservation> getMesReservations(int idUtilisateur) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Reservation> mesReservations = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, r.id, r.idUtilisateur, r.dateDeb, r.dateFin FROM reservation r INNER JOIN media m on r.idMedia = m.id WHERE idUtilisateur = ?");
            st.setInt(1, idUtilisateur);
            ResultSet reservations = st.executeQuery();

            while(reservations.next()) {
                Reservation reservation = new Reservation(reservations.getInt(4), reservations.getInt(1), reservations.getInt(5), reservations.getString(6), reservations.getString(7));
                mesReservations.add(reservation);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();

        return mesReservations;
    }

    /**
     *
     * @param r {Reservation} La réservation a trouver
     * @return {Reservation|null} La réservation existe dans mesReservation. null sinon
     * @throws SQLException
     */
    public static Reservation findReservation(Reservation r) throws SQLException {
        for (int i = 0; i < Reservation.getMesReservations(r.getIdUtilisateur()).size(); i++) {
            Reservation reservation = Reservation.getMesReservations(r.getIdUtilisateur()).get(i);
            if (r.getIdMedia() == reservation.getIdMedia() && r.getIdUtilisateur() == reservation.getIdUtilisateur() && r.getDateDebut().equals(reservation.getDateDebut()) && r.getDateFin().equals(reservation.getDateFin())) {
                return Reservation.getMesReservations(r.getIdUtilisateur()).get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param r {Reservation} la réservation a ajouter
     * @apiNote permet d'ajouter une réservation
     * @throws SQLException
     */
    public static void ajouterReservation(Reservation r) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO reservation (idMedia, idUtilisateur, dateDeb, dateFin) VALUES (?, ?, ?, ?);");
            st.setInt(1, r.getIdMedia());
            st.setInt(2, r.getIdUtilisateur());
            st.setString(3, r.getDateDebut());
            st.setString(4, r.getDateFin());
            st.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        conn.close();
    }

    /**
     *
     * @param idReservation {int} id de la réservation
     * @apiNote Permet de supprimer une réservation
     * @throws SQLException
     */
    public static void supprimerReservation(int idReservation) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM reservation WHERE id = ?");
            st.setInt(1, idReservation);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        conn.close();
    }

    /**
     *
     * @param r {Reservation} - La réservation de l'utilisateur
     * @param idUtilisateur {int} - id de l'utilisateur
     * @return {boolean} true si l'utilisateur a déjà réservé ce média. false sinon
     * @throws SQLException
     */
    public static boolean existingReservation(Reservation r, int idUtilisateur) throws SQLException {
        for (int i = 0; i < Reservation.getMesReservations(idUtilisateur).size(); i++) {
            if (Reservation.getMesReservations(idUtilisateur).get(i).getIdMedia() == r.getIdMedia()
                && Reservation.getMesReservations(idUtilisateur).get(i).getIdUtilisateur() == r.getIdUtilisateur()){
                    return true;
            }
        }
        return false;
    }
}