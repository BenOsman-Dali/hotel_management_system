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
    private Saison saisonPrincipale;

    public Facture(int numero, Reservation reservation) {
        this.numero = numero;
        this.reservation = reservation;
        this.dateEmission = LocalDate.now();
        this.saisonPrincipale = reservation.getChambre().getSaisonDominante(
                reservation.getDateDebut(),
                reservation.getDateFin()
        );
        calculerMontants();
    }

    private void calculerMontants() {
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


    public double getTotalGeneral() {
        return totalGeneral;
    }
    // --------------------------------

    public int getNumero() { return numero; }
    public Reservation getReservation() { return reservation; }
    public Paiement getPaiement() { return paiement; }


    public void afficher() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║              FACTURE N°" + String.format("%05d", numero) + "                  ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║ Date d'émission : " + dateEmission);
        System.out.println("║ Client : " + reservation.getClient().getPrenom() + " " + reservation.getClient().getNom());
        System.out.println("║ Hôtel : " + (reservation.getHotel() != null ? reservation.getHotel().getNom() : "N/A"));
        System.out.println("║ Chambre : " + reservation.getChambre().getType() + " N°" + reservation.getChambre().getNumero());
        System.out.println("║ Séjour : Du " + reservation.getDateDebut() + " au " + reservation.getDateFin());
        System.out.println("║ Nombre de nuits : " + reservation.getNombreNuits());
        System.out.println("║ Saison appliquée : " + saisonPrincipale.getPeriode() +
                " (×" + saisonPrincipale.getMultiplicateur() + ")");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║ DÉTAIL DES COÛTS :                                 ║");
        System.out.println("╠════════════════════════════════════════════════════╣");

        // ✅ Affichage détaillé des nuits avec saison
        System.out.println("║ 🏨 HÉBERGEMENT :                                   ║");
        System.out.println("║    Prix de base : " + String.format("%8.2f", reservation.getChambre().getPrixParNuit()) + "€/nuit");
        System.out.println("║    Multiplicateur saison : ×" + saisonPrincipale.getMultiplicateur());
        System.out.println("║    Total nuits (" + reservation.getNombreNuits() + " nuits) : " + String.format("%8.2f", totalNuits) + "€");

        // ✅ Services
        System.out.println("║                                                    ║");
        System.out.println("║ 🧺 SERVICES SUPPLÉMENTAIRES :                      ║");
        if (reservation.getServices().isEmpty()) {
            System.out.println("║    Aucun service ajouté");
        } else {
            for (Service s : reservation.getServices()) {
                System.out.println("║    • " + s.getType().getLibelle() + " ×" + s.getQuantite() +
                        String.format("%8.2f", s.getPrixTotal()) + "€");
            }
        }
        System.out.println("║    Total services : " + String.format("%8.2f", totalServices) + "€");

        // ✅ Sous-total
        System.out.println("║                                                    ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        double sousTotal = totalNuits + totalServices;
        System.out.println("║ SOUS-TOTAL : " + String.format("%26.2f", sousTotal) + "€");

        // ✅ Réduction
        System.out.println("║ Réduction fidélité (" + (reservation.getClient().getTauxReduction()*100) + "%) : " +
                String.format("%11.2f", reduction) + "€");

        // ✅ Taxes
        System.out.println("║ Taxes (10%) : " + String.format("%19.2f", taxes) + "€");

        // ✅ TOTAL
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║ 💵 TOTAL À PAYER : " + String.format("%21.2f", totalGeneral) + "€");
        System.out.println("╚════════════════════════════════════════════════════╝");

        if (paiement != null) {
            System.out.println("✅ Payé via : " + paiement.getTypePaiement());
        } else {
            System.out.println("⏳ Statut : En attente de paiement");
        }
        System.out.println();
    }
}