import java.io.*;

public class DataPersistence {

    // ✅ Utiliser un chemin absolu dans le dossier du projet
    private static final String DOSSIER_SAUVEGARDE = "sauvegarde/";

    public static void sauvegarder(Object objet, String nomFichier) {
        try {
            // ✅ Créer le dossier s'il n'existe pas
            File dossier = new File(DOSSIER_SAUVEGARDE);
            if (!dossier.exists()) {
                dossier.mkdirs();
            }

            String cheminComplet = DOSSIER_SAUVEGARDE + nomFichier;
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminComplet));
            oos.writeObject(objet);
            oos.close();
            System.out.println("✅ Sauvegarde réussie : " + cheminComplet);
        } catch (IOException e) {
            System.out.println("❌ Erreur sauvegarde : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Object charger(String nomFichier) {
        try {
            String cheminComplet = DOSSIER_SAUVEGARDE + nomFichier;
            File fichier = new File(cheminComplet);

            if (!fichier.exists()) {
                System.out.println("ℹ️  Aucun fichier de sauvegarde trouvé : " + cheminComplet);
                return null;
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminComplet));
            Object objet = ois.readObject();
            ois.close();
            System.out.println("✅ Chargement réussi : " + cheminComplet);
            return objet;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Erreur chargement : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}