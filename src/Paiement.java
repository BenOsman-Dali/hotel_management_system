public interface Paiement {
    boolean effectuerPaiement(double montant);
    String getTypePaiement();
}