import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Reservation {
    private int id;
    private int idMedia;
    private int idUtilisateur;
    private Date dateDebut;
    private Date dateFin;

    public Reservation(int id, int idMedia, int idUtilisateur, Date dateDebut, Date dateFin) {
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
    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public static ArrayList<Reservation> getMesReservations(int idUtilisateur) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        ArrayList<Reservation> mesReservations = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT m.id, m.titre, m.createur, r.id, r.idUtilisateur, r.dateDeb, r.dateFin FROM reservation r INNER JOIN media m on r.idMedia = m.id WHERE idUtilisateur = ?");
            st.setInt(1, idUtilisateur);
            ResultSet reservations = st.executeQuery();

            while(reservations.next()) {
                Reservation reservation = new Reservation(reservations.getInt(4), reservations.getInt(1), reservations.getInt(5), reservations.getDate(6), reservations.getDate(7));
                mesReservations.add(reservation);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();

        return mesReservations;
    }

    public static Reservation findReservation(int idMedia, int idUtilisateur, Date dateDeb, Date dateFin) throws SQLException {
        for (int i = 0; i < Reservation.getMesReservations(idUtilisateur).size(); i++) {
            Reservation reservation = Reservation.getMesReservations(idUtilisateur).get(i);
            if (idMedia == reservation.getIdMedia() && idUtilisateur == reservation.getIdUtilisateur() && dateDeb.equals(reservation.getDateDebut()) && dateFin.equals(reservation.getDateFin())) {
                return Reservation.getMesReservations(idUtilisateur).get(i);
            }
        }
        return null;
    }

    public static void ajouterReservation(int idMedia, int idUtilisateur, String dateDeb, String dateFin) throws SQLException {
        Connection conn = MySQLConnection.getConnexion();
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO reservation (idMedia, idUtilisateur, dateDeb, dateFin) VALUES (?, ?, ?, ?);");
            st.setInt(1, idMedia);
            st.setInt(2, idUtilisateur);
            st.setString(3, dateDeb);
            st.setString(4, dateFin);
            st.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        conn.close();
    }

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
}
