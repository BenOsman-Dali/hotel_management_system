import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class GestionAvis implements Serializable {
    private static List<Avis> listeAvis = new ArrayList<>();

    public static void ajouterAvis(Avis avis) {
        if (avis.getNote() >= 1 && avis.getNote() <= 5) {
            listeAvis.add(avis);
            System.out.println("✅ Avis ajouté avec succès !");
        } else {
            System.out.println("❌ La note doit être entre 1 et 5");
        }
    }

    public static double getNoteMoyenne(Hotel hotel) {
        int total = 0;
        int count = 0;
        for (Avis a : listeAvis) {
            if (a.getHotel().getNom().equals(hotel.getNom())) {
                total += a.getNote();
                count++;
            }
        }
        return count > 0 ? (double) total / count : 0;
    }

    public static void afficherAvis(Hotel hotel) {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│         AVIS POUR " + hotel.getNom().toUpperCase() + "          │");
        System.out.println("└────────────────────────────────────────────┘");
        System.out.println("⭐ Note moyenne : " + String.format("%.1f", getNoteMoyenne(hotel)) + "/5");
        for (Avis a : listeAvis) {
            if (a.getHotel().getNom().equals(hotel.getNom())) {
                System.out.println("• " + a.getClient().getPrenom() + " " + a.getClient().getNom() +
                        " | " + a.getNote() + "★ | " + a.getCommentaire());
            }
        }
    }

    public static List<Avis> getAvis() {
        return listeAvis;
    }
}