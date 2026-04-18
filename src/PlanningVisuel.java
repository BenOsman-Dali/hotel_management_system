import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class PlanningVisuel {

    public static void afficherPlanning(Hotel hotel, List<Reservation> reservations, int annee, int mois) {
        YearMonth yearMonth = YearMonth.of(annee, mois);
        int joursDansMois = yearMonth.lengthOfMonth();
        LocalDate premierJour = yearMonth.atDay(1);
        LocalDate dernierJour = yearMonth.atEndOfMonth();

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║          PLANNING DES RÉSERVATIONS - " + hotel.getNom());
        System.out.println("║          " + premierJour.getMonth().toString().toUpperCase() + " " + annee);
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");

        // ✅ En-tête des dates (1-31)
        System.out.print("Chambre  | ");
        for (int i = 1; i <= joursDansMois; i++) {
            System.out.print(String.format("%2d", i) + " |");
        }
        System.out.println();
        System.out.println("─────────┼" + "─────".repeat(joursDansMois));

        // ✅ Lignes par chambre
        for (Chambre ch : hotel.getChambres()) {
            System.out.print("Ch. " + String.format("%3d", ch.getNumero()) + " | ");
            for (int i = 1; i <= joursDansMois; i++) {
                LocalDate date = yearMonth.atDay(i);
                String symbole = getSymbolePourDate(ch, date, reservations);
                System.out.print("  " + symbole + " |");
            }
            System.out.println();
        }

        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("Légende: 🟢 Libre | 🔴 Occupée | 🟠 Maintenance |  Arrivée/Départ");
        System.out.println();
    }

    private static String getSymbolePourDate(Chambre chambre, LocalDate date, List<Reservation> reservations) {
        // ✅ Vérifier maintenance
        if (chambre.isEnMaintenance()) return "🟠";

        // ✅ Vérifier les réservations
        for (Reservation r : reservations) {
            if (r.isEstAnnulee()) continue;

            // ✅ Vérifier si c'est la bonne chambre
            if (r.getChambre().getNumero() != chambre.getNumero()) continue;

            // ✅ Vérifier si c'est le bon hôtel (si l'hôtel est stocké dans la réservation)
            if (r.getHotel() != null && !r.getHotel().getNom().equals(chambre.getClass())) continue;

            LocalDate debut = r.getDateDebut();
            LocalDate fin = r.getDateFin();

            // ✅ Arrivée
            if (date.isEqual(debut)) return "📅";

            // ✅ Départ (dernier jour de séjour)
            if (date.isEqual(fin.minusDays(1))) return "📅";

            // ✅ Séjour en cours
            if ((date.isAfter(debut) || date.isEqual(debut)) && date.isBefore(fin)) {
                return "🔴";
            }
        }

        // ✅ Libre
        return "🟢";
    }

    // ✅ Version simplifiée pour affichage sur 7 jours (à partir d'aujourd'hui)
    public static void afficherPlanningSemaine(Hotel hotel, List<Reservation> reservations) {
        afficherPlanning(hotel, reservations, LocalDate.now().getYear(), LocalDate.now().getMonthValue());
    }
}