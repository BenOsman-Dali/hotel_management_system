public abstract class Chambre {
    private int numéro;
    private String type;
    private double prixParNuit;
    private String état;
    private int capacité;
    public Chambre(int numéro, double prixParNuit, int capacité) {
        this.numéro = numéro;
        this.prixParNuit = prixParNuit;
        this.capacité = capacité;
        this.état = "libre";
    }

    public int getNumero() { return numéro; }
    public double getPrixParNuit() { return prixParNuit; }
    public String getEtat() { return état; }
    public void setEtat(String etat) { this.état = état; }
    public int getCapacite() { return capacité; }

    public abstract String getType();
}