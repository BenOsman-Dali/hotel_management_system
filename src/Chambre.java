import java.io.Serializable;
import java.time.LocalDate;

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

    public double getPrixParNuitAvecSaison(java.time.LocalDate date) {
        Saison saison = Saison.determinerSaison(date);
        return prixParNuit * saison.getMultiplicateur();
    }

    public double getPrixTotalSejour(LocalDate debut, LocalDate fin) {
        double total = 0;
        LocalDate current = debut;
        while (current.isBefore(fin)) {
            total += getPrixParNuitAvecSaison(current);
            current = current.plusDays(1);
        }
        return total;
    }

    public Saison getSaisonDominante(LocalDate debut, LocalDate fin) {
        java.util.Map<Saison, Integer> comptage = new java.util.HashMap<>();
        LocalDate current = debut;
        while (current.isBefore(fin)) {
            Saison s = Saison.determinerSaison(current);
            comptage.put(s, comptage.getOrDefault(s, 0) + 1);
            current = current.plusDays(1);
        }

        Saison dominante = Saison.BASSE;
        int maxJours = 0;
        for (java.util.Map.Entry<Saison, Integer> entry : comptage.entrySet()) {
            if (entry.getValue() > maxJours) {
                maxJours = entry.getValue();
                dominante = entry.getKey();
            }
        }
        return dominante;
    }
}