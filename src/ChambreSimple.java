public class ChambreSimple extends Chambre {
    public ChambreSimple(int numéro, double prixParNuit, int capacité){
        super(numéro, prixParNuit, 1);
    }

    @Override
    public String getType() {
        return "Simple";
    }
}
