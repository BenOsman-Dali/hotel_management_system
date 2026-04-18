public enum Saison {
    BASSE(1.0, "Nov-Mar"),
    MOYENNE(1.2, "Avr-Mai, Sep-Oct"),
    HAUTE(1.5, "Juin-Août"),
    FESTIVE(1.8, "Noël, Nouvel An");

    private final double multiplicateur;
    private final String periode;

    Saison(double multiplicateur, String periode) {
        this.multiplicateur = multiplicateur;
        this.periode = periode;
    }

    public double getMultiplicateur() { return multiplicateur; }
    public String getPeriode() { return periode; }

    public static Saison determinerSaison(java.time.LocalDate date) {
        int mois = date.getMonthValue();
        int jour = date.getDayOfMonth();

        if ((mois == 12 && jour >= 20) || (mois == 1 && jour <= 5)) {
            return FESTIVE;
        }
        if (mois >= 6 && mois <= 8) {
            return HAUTE;
        }
        if ((mois >= 4 && mois <= 5) || (mois >= 9 && mois <= 10)) {
            return MOYENNE;
        }
        return BASSE;
    }
}