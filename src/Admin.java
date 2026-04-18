import java.io.Serializable;

public class Admin implements Serializable {
    private String username;
    private String password;
    private String nom;
    private String prenom;

    public Admin(String username, String password, String nom, String prenom) {
        this.username = username;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }

    public boolean authentifier(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}