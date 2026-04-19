import java.io.Serializable;
public class ChambreDouble extends Chambre implements Serializable {
    private static final long serialVersionUID = 1L;
    public ChambreDouble(int numero) {
        super(numero, 80.0, 2);
    }
    @Override
    public String getType() { return "Double"; }
}