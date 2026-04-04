public enum TypeService {
    PETIT_DEJEUNER("Petit-déjeuner"),
    BLANCHISSERIE("Blanchisserie"),
    ROOM_SERVICE("Room Service"),
    TRANSPORT("Transport");

    private final String libelle;

    TypeService(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}