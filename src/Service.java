import java.io.Serializable;

public class Service implements Serializable {
    private TypeService type;
    private int quantite;

    public Service(TypeService type, int quantite) {
        this.type = type;
        this.quantite = quantite;
    }

    public TypeService getType() { return type; }
    public int getQuantite() { return quantite; }

    public double getPrixTotal() {
        return type.getPrixBase() * quantite;
    }
}