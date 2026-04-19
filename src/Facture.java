import java.io.Serializable;
import java.time.LocalDate;
public class Facture implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numero;
    private Reservation reservation;
    private LocalDate dateEmission;
    private double totalNuits;
    private double totalServices;
    private double reduction;
    private double taxes;
    private double totalGeneral;
    private Paiement paiement;

    public Facture(int numero, Reservation reservation) {
        this.numero = numero;
        this.reservation = reservation;
        this.dateEmission = LocalDate.now();
        calculerMontants();
    }

    private void calculerMontants() {
        long nuits = reservation.getNombreNuits();
        double prixNuit = reservation.getChambre().getPrixParNuit();

        System.out.println("🔍 DEBUG CALCUL:");
        System.out.println("  - Nuits: " + nuits);
        System.out.println("  - Prix/nuit: " + prixNuit + "€");
        System.out.println("  - Total nuits: " + (nuits * prixNuit) + "€");

        totalNuits = nuits * prixNuit;

        totalServices = 0;
        for (Service s : reservation.getServices()) {
            totalServices += s.getPrixTotal();
        }
        System.out.println("  - Total services: " + totalServices + "€");

        double sousTotal = totalNuits + totalServices;
        System.out.println("  - Sous-total: " + sousTotal + "€");

        reduction = sousTotal * reservation.getClient().getTauxReduction();
        System.out.println("  - Réduction: " + reduction + "€");

        taxes = (sousTotal - reduction) * 0.10;
        System.out.println("  - Taxes (10%): " + taxes + "€");

        totalGeneral = sousTotal - reduction + taxes;
        System.out.println("  - TOTAL: " + totalGeneral + "€");
        System.out.println("🔍 FIN DEBUG\n");
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    // --- AJOUTEZ CE GETTER PUBLIC ---
    public double getTotalGeneral() {
        return totalGeneral;
    }
    // --------------------------------

    public int getNumero() { return numero; }
    public Reservation getReservation() { return reservation; }
    public LocalDate getDateEmission() { return dateEmission; }
    public double getTotalNuits() { return totalNuits; }
    public double getTotalServices() { return totalServices; }
    public double getReduction() { return reduction; }
    public double getTaxes() { return taxes; }
    public Paiement getPaiement() { return paiement; }

    public void afficher() {
        System.out.println("\n=== FACTURE N°" + numero + " ===");
        System.out.println("Date : " + dateEmission);
        System.out.println("Client : " + reservation.getClient().getPrenom() + " " + reservation.getClient().getNom());
        System.out.println("Chambre : " + reservation.getChambre().getType() + " N°" + reservation.getChambre().getNumero());
        System.out.println("Du " + reservation.getDateDebut() + " au " + reservation.getDateFin());
        System.out.println("Nuits : " + totalNuits + "€");
        System.out.println("Services : " + totalServices + "€");
        System.out.println("Réduction Fidélité : -" + reduction + "€");
        System.out.println("Taxes (10%) : " + taxes + "€");
        System.out.println("TOTAL A PAYER : " + totalGeneral + "€");
        if (paiement != null) {
            System.out.println("Payé via : " + paiement.getTypePaiement());
        }
        System.out.println("================================\n");
    }
}