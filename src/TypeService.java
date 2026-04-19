import java.io.Serializable;

public enum TypeService implements Serializable {
    PETIT_DEJEUNER(15.0),
    BLANCHISSERIE(20.0),
    ROOM_SERVICE(25.0),
    TRANSPORT(50.0);

    private final double prixBase;

    TypeService(double prixBase) {
        this.prixBase = prixBase;
    }

    public double getPrixBase() {
        return prixBase;
    }
}