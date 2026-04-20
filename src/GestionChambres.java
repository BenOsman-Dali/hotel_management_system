import java.io.Serializable;

public class GestionChambres implements Serializable {


    public static void initialiserChambres() {
        Hotel hotelParis = GestionHotels.trouverHotel("Grand Hôtel Paris");
        if (hotelParis != null) {
            for (int i = 101; i <= 110; i++) {
                hotelParis.ajouterChambre(new ChambreSimple(i));
            }
            for (int i = 201; i <= 208; i++) {
                hotelParis.ajouterChambre(new ChambreDouble(i));
            }
            for (int i = 301; i <= 305; i++) {
                hotelParis.ajouterChambre(new Suite(i));
            }
            System.out.println("✅ Chambres initialisées pour : " + hotelParis.getNom());
        }


        Hotel hotelLyon = GestionHotels.trouverHotel("Hôtel Lyon Centre");
        if (hotelLyon != null) {
            for (int i = 501; i <= 510; i++) {
                hotelLyon.ajouterChambre(new ChambreSimple(i));
            }
            for (int i = 601; i <= 607; i++) {
                hotelLyon.ajouterChambre(new ChambreDouble(i));
            }
            for (int i = 701; i <= 703; i++) {
                hotelLyon.ajouterChambre(new Suite(i));
            }
            System.out.println("✅ Chambres initialisées pour : " + hotelLyon.getNom());
        }


        Hotel hotelMarseille = GestionHotels.trouverHotel("Hôtel Marseille Vue Mer");
        if (hotelMarseille != null) {
            for (int i = 801; i <= 806; i++) {
                hotelMarseille.ajouterChambre(new ChambreSimple(i));
            }
            for (int i = 901; i <= 905; i++) {
                hotelMarseille.ajouterChambre(new ChambreDouble(i));
            }
            for (int i = 1000; i <= 1003; i++) {
                hotelMarseille.ajouterChambre(new Suite(i));
            }
            System.out.println("✅ Chambres initialisées pour : " + hotelMarseille.getNom());
        }
    }

}