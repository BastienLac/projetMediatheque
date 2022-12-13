import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Reservation {
    private Date dateDebut;
    private Date dateFin;

    public Reservation(Date dateDebut, Date dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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
            PreparedStatement st = conn.prepareStatement("SELECT m.titre, m.createur, r.dateDeb, r.dateFin FROM reservation r INNER JOIN media m on r.idMedia = m.id WHERE idUtilisateur = ?");
            st.setInt(1, idUtilisateur);
            ResultSet reservations = st.executeQuery();

            while(reservations.next()) {
                Reservation reservation = new Reservation(reservations.getDate(3), reservations.getDate(4));
                mesReservations.add(reservation);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        conn.close();

        return mesReservations;
    }

    public static void ajouterReservation(int idMedia, int idUtilisateur, Date dateDeb, Date dateFin)  {
        try {
            Connection conn = MySQLConnection.getConnexion();
            PreparedStatement st = conn.prepareStatement("INSERT INTO reservation ('idMedia','idUtilisateur', 'dateDeb', 'dateFin') VALUES (?,?, ?, ?)");
            st.setInt(1, idMedia);
            st.setInt(2, idUtilisateur);
            st.setDate(3, dateDeb);
            st.setDate(4, dateFin);
            st.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
