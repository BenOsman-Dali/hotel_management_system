public class PaiementCarte implements Paiement {
    private final String numeroCarte;

    public PaiementCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    @Override
    public boolean effectuerPaiement(double montant) {
        System.out.println("Paiement CB de " + montant + "€ effectué avec carte ****" + numeroCarte.substring(12));
        return true;
    }

    @Override
    public String getTypePaiement() {
        return "Carte Bancaire";
    }
}