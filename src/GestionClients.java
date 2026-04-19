import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class GestionClients implements Serializable {
    private static List<Client> listeClients = new ArrayList<>();

    public static void ajouterClient(Client client) {
        listeClients.add(client);
        System.out.println("✅ Client ajouté : " + client.getPrenom() + " " + client.getNom());
    }

    public static Client trouverClient(String email) {
        for (Client c : listeClients) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return c;
            }
        }
        return null;
    }

    public static List<Client> getlisteClients() {
        return listeClients;
    }
    public static void setlisteClients(List<Client> clients) {
        listeClients = clients != null ? clients : new ArrayList<>();
    }

    public static void afficherTousLesClients() {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│              LISTE DES CLIENTS             │");
        System.out.println("└────────────────────────────────────────────┘");
        for (Client c : listeClients) {
            System.out.println("• " + c.getPrenom() + " " + c.getNom() +
                    " | Email: " + c.getEmail() +
                    " | Séjours: " + c.getNombreSejours() +
                    " | Réduction: " + (c.getTauxReduction() * 100) + "%");
        }
    }
}