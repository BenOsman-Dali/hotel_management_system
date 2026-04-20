import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class PlanningVisuel {
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";      // Vert (Libre)
    private static final String RED = "\u001B[31m";        // Rouge (Occupée)
    private static final String YELLOW = "\u001B[33m";     // Jaune (Maintenance)
    private static final String BRIGHT_GREEN = "\u001B[92m"; // Vert clair (Arrivée)
    private static final String BRIGHT_RED = "\u001B[91m";   // Rouge clair (Départ)
    private static final String BOLD = "\u001B[1m";        // Gras

    public static void afficherPlanning(Hotel hotel, List<Reservation> reservations, int annee, int mois) {
        YearMonth yearMonth = YearMonth.of(annee, mois);
        int joursDansMois = yearMonth.lengthOfMonth();
        LocalDate premierJour = yearMonth.atDay(1);

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║          PLANNING DES RÉSERVATIONS - " + BOLD + hotel.getNom() + RESET);
        System.out.println("║          " + BOLD + premierJour.getMonth().toString().toUpperCase() + " " + annee + RESET);
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");

        System.out.print("Chambre  | ");
        for (int i = 1; i <= joursDansMois; i++) {
            System.out.print(String.format("%3d", i) + "|");
        }
        System.out.println();
        System.out.println("─────────┼" + "───".repeat(joursDansMois));

        for (Chambre ch : hotel.getChambres()) {
            System.out.print("Ch. " + String.format("%3d", ch.getNumero()) + " | ");
            for (int i = 1; i <= joursDansMois; i++) {
                LocalDate date = yearMonth.atDay(i);
                String symbole = getSymbolePourDate(ch, date, reservations);
                System.out.print(" " + symbole + " |");
            }
            System.out.println();
        }

        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("Légende: " + GREEN + "■ Libre" + RESET + " | " + RED + "■ Occupée" + RESET + " | " +
                YELLOW + "■ Maintenance" + RESET + " | " + BRIGHT_GREEN + "▲ Arrivée" + RESET + " | " +
                BRIGHT_RED + "▼ Départ" + RESET);
        System.out.println();
    }

    private static String getSymbolePourDate(Chambre chambre, LocalDate date, List<Reservation> reservations) {
        if (chambre.isEnMaintenance()) return YELLOW + "■" + RESET;

        for (Reservation r : reservations) {
            if (r.isEstAnnulee()) continue;
            if (r.getChambre().getNumero() != chambre.getNumero()) continue;

            LocalDate debut = r.getDateDebut();
            LocalDate fin = r.getDateFin();

            if (date.isEqual(debut)) return BRIGHT_GREEN + "▲" + RESET;
            if (date.isEqual(fin.minusDays(1))) return BRIGHT_RED + "▼" + RESET;
            if ((date.isAfter(debut) || date.isEqual(debut)) && date.isBefore(fin)) {
                return RED + "■" + RESET;
            }
        }
        return GREEN + "■" + RESET;
    }
}