public class PaiementEspece implements Paiement {
    @Override
    public boolean effectuerPaiement(double montant) {
        System.out.println("Paiement Espèces de " + montant + "€ reçu.");
        return true;
    }

    @Override
    public String getTypePaiement() {
        return "Espèces";
    }
}