public class PaiementVirement implements Paiement {
    private final String iban;

    public PaiementVirement(String iban) {
        this.iban = iban;
    }

    @Override
    public boolean effectuerPaiement(double montant) {
        System.out.println("Virement de " + montant + "€ initié vers " + iban);
        return true;
    }

    @Override
    public String getTypePaiement() {
        return "Virement";
    }
}