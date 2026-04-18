import java.io.*;

public class DataPersistence {
    private static final String FICHIER_CLIENTS = "clients.ser";
    private static final String FICHIER_RESERVATIONS = "reservations.ser";
    private static final String FICHIER_FACTURES = "factures.ser";

    public static void sauvegarder(Object objet, String nomFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
            oos.writeObject(objet);
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde : " + e.getMessage());
        }
    }

    public static Object charger(String nomFichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFichier))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

}