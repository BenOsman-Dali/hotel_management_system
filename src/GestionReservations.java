import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class GestionReservations implements Serializable {
    private static List<Reservation> reservations = new ArrayList<>();
    private static int compteurId = 1;

    public static Reservation creerReservation(Client client, Chambre chambre, Hotel hotel, LocalDate debut, LocalDate fin, int personnes) throws Exception {
        // ✅ VÉRIFICATION : L'hôtel correspond à la chambre
        if (!hotel.getChambres().contains(chambre)) {
            throw new Exception("Cette chambre n'appartient pas à cet hôtel");
        }

        // ✅ VÉRIFICATION : Dates doivent être futures
        LocalDate aujourdhui = LocalDate.now();
        if (debut.isBefore(aujourdhui) || debut.isEqual(aujourdhui)) {
            throw new Exception("La date de début doit être dans le futur (après aujourd'hui)");
        }
        if (fin.isBefore(aujourdhui) || fin.isEqual(aujourdhui)) {
            throw new Exception("La date de fin doit être dans le futur (après aujourd'hui)");
        }

        // ✅ VÉRIFICATION : Début < Fin
        if (debut.isAfter(fin) || debut.isEqual(fin)) {
            throw new Exception("La date de début doit être avant la date de fin");
        }

        // ✅ VÉRIFICATION : Capacité de la chambre
        if (personnes > chambre.getCapacite()) {
            throw new Exception("Nombre de personnes (" + personnes + ") dépasse la capacité de la chambre " +
                    chambre.getType() + " (max: " + chambre.getCapacite() + " personnes)");
        }
        if (personnes <= 0) {
            throw new Exception("Le nombre de personnes doit être au moins 1");
        }

        // ✅ VÉRIFICATION : Maintenance
        if (chambre.isEnMaintenance()) {
            throw new Exception("Chambre en maintenance");
        }

        // ✅ VÉRIFICATION : Conflits (uniquement dans cet hôtel)
        if (verifierConflit(chambre, debut, fin, null)) {
            throw new Exception("Conflit de dates avec une autre réservation");
        }

        Reservation reservation = new Reservation(client, chambre, hotel, debut, fin, personnes);
        reservation.setId(compteurId++);
        chambre.setEtat(EtatChambre.OCCUPEE);
        reservations.add(reservation);
        client.ajouterReservation(reservation);
        return reservation;
    }

    private static boolean verifierConflit(Chambre chambre, LocalDate debut, LocalDate fin, Reservation exclue) {
        for (Reservation r : reservations) {
            if (r.isEstAnnulee() || r == exclue) continue;
            if (r.getChambre().getNumero() == chambre.getNumero()) {
                if (debut.isBefore(r.getDateFin()) && fin.isAfter(r.getDateDebut())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Reservation> getReservations() {
        return reservations;
    }

    public static Reservation trouverReservation(int id) {
        for (Reservation r : reservations) {
            if (r.getId() == id) return r;
        }
        return null;
    }
}