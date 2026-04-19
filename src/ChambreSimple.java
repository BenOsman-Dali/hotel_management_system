import java.io.Serializable;
public class ChambreSimple extends Chambre implements Serializable {
    private static final long serialVersionUID = 1L;
    public ChambreSimple(int numero) {
        super(numero, 50.0, 1);
    }
    @Override
    public String getType() { return "Simple"; }
}