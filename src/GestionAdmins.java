import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class GestionAdmins implements Serializable {
    private static List<Admin> admins = new ArrayList<>();

    static {
        admins.add(new Admin("admin", "admin123", "Administrateur", "Principal"));
        admins.add(new Admin("manager", "manager123", "Gérant", "Hôtel"));
    }

    public static Admin authentifier(String username, String password) {
        for (Admin a : admins) {
            if (a.authentifier(username, password)) {
                return a;
            }
        }
        return null;
    }

    public static List<Admin> getAdmins() {
        return admins;
    }
}