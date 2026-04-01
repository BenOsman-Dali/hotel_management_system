public class Suite extends Chambre {
    public Suite(int numéro, double prixParNuit, int capacité){
        super(numéro, prixParNuit, capacité);
    }
    @Override
    public String getType(){return "Suite";}
}
