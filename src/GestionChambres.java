import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class GestionChambres implements Serializable {

    // ✅ Initialiser les chambres pour chaque hôtel
    public static void initialiserChambres() {
        // 🏨 Hôtel 1 : Grand Hôtel Paris
        Hotel hotelParis = GestionHotels.trouverHotel("Grand Hôtel Paris");
        if (hotelParis != null) {
            for (int i = 101; i <= 110; i++) {
                hotelParis.ajouterChambre(new ChambreSimple(i));
            }
            for (int i = 201; i <= 208; i++) {
                hotelParis.ajouterChambre(new ChambreDouble(i));
            }
            for (int i = 301; i <= 305; i++) {
                hotelParis.ajouterChambre(new Suite(i));
            }
            System.out.println("✅ Chambres initialisées pour : " + hotelParis.getNom());
        }

        // 🏨 Hôtel 2 : Hôtel Lyon Centre
        Hotel hotelLyon = GestionHotels.trouverHotel("Hôtel Lyon Centre");
        if (hotelLyon != null) {
            for (int i = 501; i <= 510; i++) {
                hotelLyon.ajouterChambre(new ChambreSimple(i));
            }
            for (int i = 601; i <= 607; i++) {
                hotelLyon.ajouterChambre(new ChambreDouble(i));
            }
            for (int i = 701; i <= 703; i++) {
                hotelLyon.ajouterChambre(new Suite(i));
            }
            System.out.println("✅ Chambres initialisées pour : " + hotelLyon.getNom());
        }

        // 🏨 Hôtel 3 : Hôtel Marseille Vue Mer
        Hotel hotelMarseille = GestionHotels.trouverHotel("Hôtel Marseille Vue Mer");
        if (hotelMarseille != null) {
            for (int i = 801; i <= 806; i++) {
                hotelMarseille.ajouterChambre(new ChambreSimple(i));
            }
            for (int i = 901; i <= 905; i++) {
                hotelMarseille.ajouterChambre(new ChambreDouble(i));
            }
            for (int i = 1000; i <= 1003; i++) {
                hotelMarseille.ajouterChambre(new Suite(i));
            }
            System.out.println("✅ Chambres initialisées pour : " + hotelMarseille.getNom());
        }
    }

    // ✅ NOUVELLE MÉTHODE : Retourne toutes les chambres (tous hôtels confondus)
    public static List<Chambre> getChambres() {
        List<Chambre> toutesChambres = new ArrayList<>();
        for (Hotel h : GestionHotels.getHotels()) {
            toutesChambres.addAll(h.getChambres());
        }
        return toutesChambres;
    }

    // ✅ Trouver une chambre dans un hôtel spécifique
    public static Chambre trouverChambre(Hotel hotel, int numero) {
        if (hotel == null) return null;
        return hotel.trouverChambre(numero);
    }

    // ✅ Trouver une chambre par numéro (tous hôtels)
    public static Chambre trouverChambre(int numero) {
        for (Hotel h : GestionHotels.getHotels()) {
            Chambre ch = h.trouverChambre(numero);
            if (ch != null) return ch;
        }
        return null;
    }

    // ✅ Afficher toutes les chambres d'un hôtel
    public static void afficherChambres(Hotel hotel) {
        if (hotel == null) {
            System.out.println("❌ Hôtel non trouvé");
            return;
        }
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│         CHAMBRES - " + hotel.getNom().toUpperCase());
        System.out.println("└────────────────────────────────────────────┘");
        for (Chambre ch : hotel.getChambres()) {
            String etatIcon = ch.getEtat() == EtatChambre.LIBRE ? "🟢" :
                    ch.getEtat() == EtatChambre.OCCUPEE ? "🔴" : "🟠";
            System.out.println(etatIcon + " Chambre " + ch.getNumero() +
                    " (" + ch.getType() + ") | " + ch.getEtat() +
                    " | " + ch.getPrixParNuit() + "€/nuit | Cap: " + ch.getCapacite());
        }
    }
}