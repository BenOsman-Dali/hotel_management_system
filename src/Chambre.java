import java.io.Serializable;
public abstract class Chambre implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int numero;
    protected double prixParNuit;
    protected int capacite;
    protected EtatChambre etat;

    public Chambre(int numero, double prixParNuit, int capacite) {
        this.numero = numero;
        this.prixParNuit = prixParNuit;
        this.capacite = capacite;
        this.etat = EtatChambre.LIBRE;
    }

    public int getNumero() { return numero; }
    public double getPrixParNuit() { return prixParNuit; }
    public int getCapacite() { return capacite; }
    public EtatChambre getEtat() { return etat; }
    public void setEtat(EtatChambre etat) { this.etat = etat; }
    public boolean isEnMaintenance() { return etat == EtatChambre.MAINTENANCE; }

    public abstract String getType();

    // Ajouter cette méthode dans Chambre.java :
    public double getPrixParNuitAvecSaison(java.time.LocalDate date) {
        Saison saison = Saison.determinerSaison(date);
        return prixParNuit * saison.getMultiplicateur();
    }

    public double getPrixTotalSejour(java.time.LocalDate debut, java.time.LocalDate fin) {
        double total = 0;
        java.time.LocalDate current = debut;
        while (current.isBefore(fin)) {
            total += getPrixParNuitAvecSaison(current);
            current = current.plusDays(1);
        }
        return total;
    }
}