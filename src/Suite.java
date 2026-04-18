public class Suite extends Chambre {
    public Suite(int numero) {
        super(numero, 150.0, 4);
    }
    @Override
    public String getType() { return "Suite"; }
}