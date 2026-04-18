public class ChambreDouble extends Chambre {
    public ChambreDouble(int numero) {
        super(numero, 80.0, 2);
    }
    @Override
    public String getType() { return "Double"; }
}