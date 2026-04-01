public class ChambreDouble extends Chambre {
    public ChambreDouble(int numéro, double prixParNuit, int capacité){
        super(numéro, prixParNuit, 2);
    }
    @Override
    public String getType() {return "Double";}
}
