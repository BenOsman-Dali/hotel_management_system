import java.io.Serializable;
public class Suite extends Chambre implements Serializable {
    private static final long serialVersionUID = 1L;
    public Suite(int numero) {
        super(numero, 150.0, 4);
    }
    @Override
    public String getType() { return "Suite"; }
}