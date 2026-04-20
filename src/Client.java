import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String nom;
    private final String prenom;
    private final String email;
    private int nombreSejours;
    private double tauxReduction;
    private final List<Reservation> historique;

    public Client(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.nombreSejours = 0;
        this.tauxReduction = 0.0;
        this.historique = new ArrayList<>();
    }

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public int getNombreSejours() { return nombreSejours; }
    public double getTauxReduction() { return tauxReduction; }
    public List<Reservation> getHistorique() { return historique; }

    public void ajouterReservation(Reservation r) {
        this.historique.add(r);
        this.nombreSejours++;
        mettreAJourFidelite();
    }

    private void mettreAJourFidelite() {
        if (nombreSejours >= 10) tauxReduction = 0.20;
        else if (nombreSejours >= 5) tauxReduction = 0.10;
        else tauxReduction = 0.0;
    }
    // S'assurer que cette méthode existe (appelée dans MenuAdmin) :
    public void afficherHistorique() {
        System.out.println("Historique de " + prenom + " " + nom + " :");
        for (Reservation r : historique) {
            System.out.println("- Chambre " + r.getChambre().getNumero() +
                    " du " + r.getDateDebut() + " au " + r.getDateFin());
        }
        System.out.println("Total séjours : " + nombreSejours + " | Réduction : " + (tauxReduction * 100) + "%");
    }
    // Ajouter cette méthode dans Client.java :
    public void laisserAvis(Hotel hotel, int note, String commentaire) {
        Avis avis = new Avis(this, hotel, note, commentaire);
        GestionAvis.ajouterAvis(avis);
    }
}