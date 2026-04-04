public class Service {
    private TypeService type;
    private double prix;
    private int quantite;

    public Service(TypeService type, double prix, int quantite) {
        this.type = type;
        this.prix = prix;
        this.quantite = quantite;
    }

    public TypeService getType() { return type; }
    public double getPrix() { return prix; }
    public int getQuantite() { return quantite; }

    public double getPrixTotal() {
        return prix * quantite;
    }

    @Override
    public String toString() {
        return quantite + "x " + type.getLibelle() + " (" + getPrixTotal() + "€)";
    }
}