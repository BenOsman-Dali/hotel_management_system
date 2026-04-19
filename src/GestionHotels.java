import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class GestionHotels implements Serializable {
    private static List<Hotel> hotels = new ArrayList<>();

    static {
        // ✅ Les noms doivent correspondre exactement avec GestionChambres
        hotels.add(new Hotel("Grand Hôtel Paris", "123 Champs-Élysées", "Paris", 5));
        hotels.add(new Hotel("Hôtel Lyon Centre", "45 Rue de la République", "Lyon", 4));
        hotels.add(new Hotel("Hôtel Marseille Vue Mer", "78 Corniche Kennedy", "Marseille", 4));
    }

    public static void ajouterHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    public static Hotel trouverHotel(String nom) {
        for (Hotel h : hotels) {
            if (h.getNom().equalsIgnoreCase(nom)) return h;
        }
        return null;
    }

    public static List<Hotel> getHotels() {
        return hotels;
    }

    public static void setHotels(List<Hotel> newHotels) {
        hotels = newHotels != null ? newHotels : new ArrayList<>();
    }

    public static void afficherHotels() {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│              LISTE DES HÔTELS              │");
        System.out.println("└────────────────────────────────────────────┘");
        for (Hotel h : hotels) {
            System.out.println("🏨 " + h.getNom() + " (" + h.getVille() + ") - " +
                    h.getEtoiles() + "★ | Chambres: " + h.getNombreChambres() +
                    " | Libres: " + h.getNombreChambresLibres());
        }
    }
}