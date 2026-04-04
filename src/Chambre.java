public class Chambre {
    private int numéro;
    private String type;
    private double prixParNuit;
    private String état;
    private int capacité;
    private boolean enMaintenance;
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
    public boolean isEnMaintenance() {
        return enMaintenance;
    }

    public void setEnMaintenance(boolean enMaintenance) {
        this.enMaintenance = enMaintenance;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
