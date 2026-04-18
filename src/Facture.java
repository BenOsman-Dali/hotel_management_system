import java.time.LocalDate;
import java.util.List;

public class Facture {
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
        // Calcul avec tarification saisonnière
        totalNuits = reservation.getChambre().getPrixTotalSejour(
                reservation.getDateDebut(),
                reservation.getDateFin()
        );

        totalServices = 0;
        for (Service s : reservation.getServices()) {
            totalServices += s.getPrixTotal();
        }

        double sousTotal = totalNuits + totalServices;
        reduction = sousTotal * reservation.getClient().getTauxReduction();
        taxes = (sousTotal - reduction) * 0.10;
        totalGeneral = sousTotal - reduction + taxes;
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