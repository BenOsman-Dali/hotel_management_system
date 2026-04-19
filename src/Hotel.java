import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nom;
    private String adresse;
    private String ville;
    private int etoiles;
    private List<Chambre> chambres;

    public Hotel(String nom, String adresse, String ville, int etoiles) {
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.etoiles = etoiles;
        this.chambres = new ArrayList<>();
    }

    public String getNom() { return nom; }
    public String getAdresse() { return adresse; }
    public String getVille() { return ville; }
    public int getEtoiles() { return etoiles; }
    public List<Chambre> getChambres() { return chambres; }

    public void ajouterChambre(Chambre chambre) {
        chambres.add(chambre);
    }

    public Chambre trouverChambre(int numero) {
        for (Chambre c : chambres) {
            if (c.getNumero() == numero) return c;
        }
        return null;
    }

    public int getNombreChambres() { return chambres.size(); }

    public int getNombreChambresLibres() {
        int count = 0;
        for (Chambre c : chambres) {
            if (c.getEtat() == EtatChambre.LIBRE) count++;
        }
        return count;
    }
}