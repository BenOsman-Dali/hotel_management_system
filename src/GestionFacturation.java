import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GestionFacturation implements Serializable {
    private static final long serialVersionUID = 1L;

    private static List<Facture> factures = new ArrayList<>();
    private static int compteurFacture = 1;

    public static Facture genererFacture(Reservation reservation) {
        Facture f = new Facture(compteurFacture++, reservation);
        factures.add(f);
        return f;
    }

    public static void payerFacture(Facture facture, Paiement paiement) {
        if (paiement.effectuerPaiement(facture.getTotalGeneral())) {
            facture.setPaiement(paiement);
            System.out.println("Paiement enregistré avec succès!");
        }
    }

    public static List<Facture> getFactures() {
        return factures;
    }

    // ✅ NOUVELLE MÉTHODE : Vérifier si une réservation est déjà facturée
    public static boolean estDejaFacturee(Reservation reservation) {
        for (Facture f : factures) {
            if (f.getReservation() == reservation) {
                return true;
            }
        }
        return false;
    }

    public static void setFactures(List<Facture> facturesChargees) {
    }
}