import java.util.List;

public class Statistiques {
    public static double getTauxOccupation(List<Reservation> reservations, int totalChambres) {
        long actives = reservations.stream().filter(r -> !r.isEstAnnulee()).count();
        return totalChambres > 0 ? (actives * 100.0) / totalChambres : 0;
    }

    public static double getRevenuTotal(List<Facture> factures) {
        return factures.stream().mapToDouble(Facture::getTotalGeneral).sum();
    }

    public static Client getClientPlusFidele(List<Client> clients) {
        Client plusFidele = null;
        int maxSejours = 0;
        for (Client c : clients) {
            if (c.getNombreSejours() > maxSejours) {
                maxSejours = c.getNombreSejours();
                plusFidele = c;
            }
        }
        return plusFidele;
    }
}