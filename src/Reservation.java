import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int compteurGlobal = 1;

    private int id;
    private final Client client;
    private final Chambre chambre;
    private Hotel hotel;
    private final LocalDate dateDebut;
    private final LocalDate dateFin;
    private final int nombrePersonnes;
    private boolean estAnnulee;
    private final List<Service> services;

    public Reservation(Client client, Chambre chambre, Hotel hotel, LocalDate dateDebut, LocalDate dateFin, int nombrePersonnes) {
        this.id = compteurGlobal++;
        this.client = client;
        this.chambre = chambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nombrePersonnes = nombrePersonnes;
        this.estAnnulee = false;
        this.services = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Client getClient() { return client; }
    public Chambre getChambre() { return chambre; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public int getNombrePersonnes() { return nombrePersonnes; }
    public boolean isEstAnnulee() { return estAnnulee; }
    public List<Service> getServices() { return services; }

    public long getNombreNuits() {
        return java.time.temporal.ChronoUnit.DAYS.between(dateDebut, dateFin);
    }

    public void ajouterService(Service service) {
        this.services.add(service);
    }


    public Hotel getHotel() { return hotel; }

    public void annuler() {
        this.estAnnulee = true;
        if (chambre != null) {
            chambre.setEtat(EtatChambre.LIBRE);
        }
    }

    @Override
    public String toString() {
        return "Réservation N°" + id;
    }
}