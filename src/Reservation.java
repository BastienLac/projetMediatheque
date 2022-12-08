import java.util.Date;

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
}
