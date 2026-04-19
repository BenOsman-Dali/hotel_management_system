public enum TypeService {
    PETIT_DEJEUNER("Petit-déjeuner", 15.0),
    BLANCHISSERIE("Blanchisserie", 20.0),
    ROOM_SERVICE("Room Service", 25.0),
    TRANSPORT("Transport", 50.0);

    private final String libelle;
    private final double prixBase;

    TypeService(String libelle, double prixBase) {
        this.libelle = libelle;
        this.prixBase = prixBase;
    }

    // ✅ AJOUTER CETTE MÉTHODE
    public String getLibelle() {
        return libelle;
    }

    // ✅ CETTE MÉTHODE DOIT AUSSI EXISTER
    public double getPrixBase() {
        return prixBase;
    }
}