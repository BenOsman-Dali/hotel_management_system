import java.util.ArrayList;
import java.util.List;

public class Client {
    private String nom;
    private String prenom;
    private int nombreSejours;
    private double tauxReduction;
    private List<Reservation> historique;

    public Client(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.nombreSejours = 0;
        this.tauxReduction = 0.0;
        this.historique = new ArrayList<>();
    }

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public int getNombreSejours() { return nombreSejours; }
    public double getTauxReduction() { return tauxReduction; }
    public List<Reservation> getHistorique() { return historique; }

    public void ajouterReservation(Reservation r) {
        this.historique.add(r);
        this.nombreSejours++;
        mettreAJourFidelite();
    }

    private void mettreAJourFidelite() {
        if (nombreSejours >= 10) {
            this.tauxReduction = 0.20;
        } else if (nombreSejours >= 5) {
            this.tauxReduction = 0.10;
        } else {
            this.tauxReduction = 0.0;
        }
    }

    public void afficherHistorique() {
        System.out.println("Historique de " + prenom + " " + nom + " :");
        for (Reservation r : historique) {
            System.out.println("- Chambre " + r.getChambre().getNumero() +
                    " du " + r.getDateDebut() + " au " + r.getDateFin());
        }
        System.out.println("Total séjours : " + nombreSejours + " | Réduction : " + (tauxReduction * 100) + "%");
    }
}