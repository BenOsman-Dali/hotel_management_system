import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reservation {

    private Client client;
    private Chambre chambre;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int nombrePersonnes;
    private boolean estAnnulee;
    private List<Service> services;

    public Reservation(Client client, Chambre chambre, LocalDate dateDebut, LocalDate dateFin, int nombrePersonnes) {
        this.client = client;
        this.chambre = chambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nombrePersonnes = nombrePersonnes;
        this.estAnnulee = false;
        this.services = new ArrayList<>();
    }

    // Getters
    public Chambre getChambre() { return chambre; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public boolean isEstAnnulee() { return estAnnulee; }
    public Client getClient() { return client; }
    public List<Service> getServices() { return services; }

    // --- INSTANCE METHODS (needed by GestionReservations) ---
    public void modifierDates(LocalDate nouveauDebut, LocalDate nouvelleFin) {
        this.dateDebut = nouveauDebut;
        this.dateFin = nouvelleFin;
    }

    public void annuler() {
        this.estAnnulee = true;
    }

    // Service methods
    public void ajouterService(Service service) {
        this.services.add(service);
    }

    public double getTotalServices() {
        double total = 0;
        for (Service s : services) {
            total += s.getPrixTotal();
        }
        return total;
    }
}