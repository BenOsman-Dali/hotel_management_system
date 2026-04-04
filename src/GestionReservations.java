import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionReservations {
    private List<Reservation> reservations;

    public GestionReservations() {
        this.reservations = new ArrayList<>();
    }

    // 1. Créer une réservation
    public Reservation creerReservation(Client client, Chambre chambre, LocalDate debut, LocalDate fin, int personnes) throws Exception {
        if (chambre.isEnMaintenance()) {
            throw new Exception("Impossible : La chambre est en maintenance.");
        }
        if (debut.isAfter(fin) || debut.isEqual(fin)) {
            throw new Exception("Impossible : La date de début doit être avant la date de fin.");
        }
        // null car c'est une nouvelle réservation, pas besoin d'exclure
        if (verifierConflit(chambre, debut, fin, null)) {
            throw new Exception("Impossible : Conflit de dates détecté.");
        }

        Reservation nouvelle = new Reservation(client, chambre, debut, fin, personnes);
        reservations.add(nouvelle);
        return nouvelle;
    }

    // 2. Vérifier les conflits (exclut la réservation passée en paramètre si modification)
    private boolean verifierConflit(Chambre chambre, LocalDate debut, LocalDate fin, Reservation aExclure) {
        for (Reservation r : reservations) {
            if (r.isEstAnnulee()) continue;
            if (r == aExclure) continue; // On ignore la réservation qu'on est en train de modifier

            if (r.getChambre().getNumero() == chambre.getNumero()) {
                // Chevauchement : DébutNouveau < FinExistant ET FinNouveau > DébutExistant
                if (debut.isBefore(r.getDateFin()) && fin.isAfter(r.getDateDebut())) {
                    return true;
                }
            }
        }
        return false;
    }

    // 3. Modifier une réservation (prend l'objet en paramètre)
    public void modifierReservation(Reservation reservation, LocalDate nouveauDebut, LocalDate nouvelleFin) throws Exception {
        if (reservation == null || reservation.isEstAnnulee()) {
            throw new Exception("Réservation invalide ou annulée.");
        }
        if (reservation.getChambre().isEnMaintenance()) {
            throw new Exception("Impossible : La chambre est en maintenance.");
        }
        if (verifierConflit(reservation.getChambre(), nouveauDebut, nouvelleFin, reservation)) {
            throw new Exception("Impossible : Conflit de dates avec une autre réservation.");
        }

        reservation.modifierDates(nouveauDebut, nouvelleFin);
    }

    // 4. Annuler une réservation (prend l'objet en paramètre)
    public void annulerReservation(Reservation reservation) throws Exception {
        if (reservation == null) {
            throw new Exception("Réservation introuvable.");
        }
        reservation.annuler();
    }
}