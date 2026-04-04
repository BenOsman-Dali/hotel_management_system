import java.util.ArrayList;
import java.util.List;

public class GestionClients {
    private static List<Client> listeClients = new ArrayList<>();

    public static void ajouterClient(Client client) {
        listeClients.add(client);
        System.out.println("Client ajouté : " + client.getPrenom() + " " + client.getNom());
    }

    public static Client trouverClient(String nom) {
        for (Client c : listeClients) {
            if (c.getNom().equalsIgnoreCase(nom)) {
                return c;
            }
        }
        return null;
    }

    public static void afficherTousLesClients() {
        for (Client c : listeClients) {
            System.out.println(c.getPrenom() + " " + c.getNom() + " (Fidélité : " + (c.getTauxReduction() * 100) + "%)");
        }
    }

    public static List<Client> getlisteClients() {
        return listeClients;
    }
}