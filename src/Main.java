import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Admin adminConnecte;

    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════╗");
        System.out.println("║   SYSTÈME DE GESTION HÔTELIER - ADMIN      ");
        System.out.println("════════════════════════════════════════════╝");

        // ✅ ÉTAPE 1 : Initialiser les hôtels (déjà fait dans static de GestionHotels)
        // ✅ ÉTAPE 2 : Initialiser les chambres pour chaque hôtel
        GestionChambres.initialiserChambres();

        // ✅ ÉTAPE 3 : Charger les données sauvegardées
        chargerDonnees();

        if (!authentifier()) {
            System.out.println("❌ Échec de l'authentification. Programme terminé.");
            scanner.close();
            return;
        }

        afficherMenuPrincipal();
        scanner.close();
    }

    private static boolean authentifier() {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│           AUTHENTIFICATION ADMIN           │");
        System.out.println("└────────────────────────────────────────────┘");
        System.out.print("👤 Username : ");
        String username = scanner.nextLine();
        System.out.print(" Password : ");
        String password = scanner.nextLine();

        adminConnecte = GestionAdmins.authentifier(username, password);
        if (adminConnecte != null) {
            System.out.println("\n✅ Connexion réussie ! Bienvenue " + adminConnecte.getPrenom() + " " + adminConnecte.getNom());
        }
        return adminConnecte != null;
    }

    private static void afficherMenuPrincipal() {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║              MENU PRINCIPAL                ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. 👥 Gérer les clients                    ║");
            System.out.println("║ 2. 🚪 Gérer les chambres                   ║");
            System.out.println("║ 3.  Gérer les réservations               ║");
            System.out.println("║ 4. 💰 Gérer la facturation                 ║");
            System.out.println("║ 5. 📊 Voir les statistiques                ║");
            System.out.println("║ 6. 💾 Sauvegarder les données              ║");
            System.out.println("║ 7. 🚪 Déconnexion / Quitter                ║");
            System.out.println("║ 8. 🏨 Gérer les hôtels                     ║");
            System.out.println("║ 9. 📅 Voir le planning                     ║");
            System.out.println("║ 10. ⭐ Gérer les avis                      ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("👉 Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choix) {
                    case 1: gererClients(); break;
                    case 2: gererChambres(); break;
                    case 3: gererReservations(); break;
                    case 4: gererFacturation(); break;
                    case 5: afficherStatistiques(); break;
                    case 6: sauvegarderDonnees(); break;
                    case 7:
                        sauvegarderDonnees();
                        continuer = false;
                        System.out.println("\n👋 Merci d'avoir utilisé le système. Au revoir " + adminConnecte.getPrenom() + "!");
                        break;
                    case 8: gererHotels(); break;
                    case 9: afficherPlanning(); break;
                    case 10: gererAvis(); break;
                    default:
                        System.out.println("❌ Choix invalide. Veuillez réessayer.");
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur : " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
    private static void gererHotels() {
        GestionHotels.afficherHotels();
    }

    private static void afficherPlanning() {
        try {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║          PLANNING DES RÉSERVATIONS         ║");
            System.out.println("╚════════════════════════════════════════════╝");

            // ✅ ÉTAPE 1 : Sélectionner l'hôtel
            System.out.println("\n┌────────────────────────────────────────────┐");
            System.out.println("│         SÉLECTIONNER UN HÔTEL               │");
            System.out.println("└────────────────────────────────────────────┘");
            GestionHotels.afficherHotels();
            System.out.print("👉 Nom de l'hôtel : ");
            String nomHotel = scanner.nextLine();
            Hotel hotel = GestionHotels.trouverHotel(nomHotel);

            if (hotel == null) {
                System.out.println("❌ Hôtel non trouvé");
                return;
            }
            System.out.println("✅ Hôtel sélectionné : " + hotel.getNom());

            // ✅ ÉTAPE 2 : Sélectionner l'année
            System.out.print("📅 Année (ex: 2026) : ");
            int annee = scanner.nextInt();
            scanner.nextLine();

            // ✅ ÉTAPE 3 : Sélectionner le mois
            System.out.println("📋 Mois disponibles:");
            System.out.println("1. Janvier  | 2. Février  | 3. Mars");
            System.out.println("4. Avril    | 5. Mai      | 6. Juin");
            System.out.println("7. Juillet  | 8. Août     | 9. Septembre");
            System.out.println("10. Octobre | 11. Novembre| 12. Décembre");
            System.out.print("👉 Mois (1-12) : ");
            int mois = scanner.nextInt();
            scanner.nextLine();

            if (mois < 1 || mois > 12) {
                System.out.println("❌ Mois invalide (doit être entre 1 et 12)");
                return;
            }

            // ✅ ÉTAPE 4 : Afficher le planning
            PlanningVisuel.afficherPlanning(hotel, GestionReservations.getReservations(), annee, mois);

        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }

    private static void gererAvis() {
        System.out.println("\n=== GESTION DES AVIS ===");
        System.out.println("1. Laisser un avis");
        System.out.println("2. Voir les avis d'un hôtel");
        System.out.print("Choix : ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        if (choix == 1) {
            System.out.print("Nom du client : ");
            String nom = scanner.nextLine();
            Client client = GestionClients.trouverClient(nom);
            if (client != null) {
                Hotel hotel = GestionHotels.getHotels().get(0);
                System.out.print("Note (1-5) : ");
                int note = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Commentaire : ");
                String commentaire = scanner.nextLine();
                client.laisserAvis(hotel, note, commentaire);
            }
        } else if (choix == 2) {
            Hotel hotel = GestionHotels.getHotels().get(0);
            GestionAvis.afficherAvis(hotel);
        }
    }

    private static void gererClients() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║           GESTION DES CLIENTS              ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. ➕ Ajouter un client                    ║");
            System.out.println("║ 2. 📋 Voir tous les clients                ║");
            System.out.println("║ 3.  Voir l'historique d'un client        ║");
            System.out.println("║ 4. 🔙 Retour au menu principal             ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("👉 Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.print("📝 Nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("📝 Prénom : ");
                    String prenom = scanner.nextLine();
                    System.out.print("📧 Email : ");
                    String email = scanner.nextLine();
                    Client c = new Client(nom, prenom, email);
                    GestionClients.ajouterClient(c);
                    System.out.println("✅ Client ajouté avec succès !");
                    break;
                case 2:
                    System.out.println("\n┌────────────────────────────────────────────┐");
                    System.out.println("│              LISTE DES CLIENTS             │");
                    System.out.println("└────────────────────────────────────────────┘");
                    for (Client client : GestionClients.getlisteClients()) {
                        System.out.println("• " + client.getPrenom() + " " + client.getNom() +
                                " | Email: " + client.getEmail() +
                                " | Séjours: " + client.getNombreSejours() +
                                " | Réduction: " + (client.getTauxReduction() * 100) + "%");
                    }
                    break;
                case 3:
                    System.out.print("📧 Email du client : ");
                    String emailRech = scanner.nextLine();
                    Client clientTrouve = GestionClients.trouverClient(emailRech);
                    if (clientTrouve != null) {
                        clientTrouve.afficherHistorique();
                    } else {
                        System.out.println("❌ Client non trouvé");
                    }
                    break;
                case 4:
                    retour = true;
                    break;
                default:
                    System.out.println("❌ Choix invalide");
            }
        }
    }

    private static void gererChambres() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║           GESTION DES CHAMBRES             ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. 📋 Voir toutes les chambres             ║");
            System.out.println("║ 2. 🔧 Mettre en maintenance                ║");
            System.out.println("║ 3. ✅ Libérer une chambre                  ║");
            System.out.println("║ 4. 🔙 Retour au menu principal             ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("👉 Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("\n┌────────────────────────────────────────────┐");
                    System.out.println("│            ÉTAT DES CHAMBRES               │");
                    System.out.println("└────────────────────────────────────────────┘");
                    // ✅ Utiliser GestionChambres.getChambres()
                    for (Chambre ch : GestionChambres.getChambres()) {
                        String etatIcon = ch.getEtat() == EtatChambre.LIBRE ? "🟢" :
                                ch.getEtat() == EtatChambre.OCCUPEE ? "🔴" : "🟠";
                        System.out.println(etatIcon + " Chambre " + ch.getNumero() +
                                " (" + ch.getType() + ") | " + ch.getEtat() +
                                " | " + ch.getPrixParNuit() + "€/nuit | Cap: " + ch.getCapacite());
                    }
                    break;
                case 2:
                    System.out.print("🔢 Numéro de chambre : ");
                    int numMaint = scanner.nextInt();
                    scanner.nextLine();
                    Chambre chMaint = GestionChambres.trouverChambre(numMaint);
                    if (chMaint != null) {
                        chMaint.setEtat(EtatChambre.MAINTENANCE);
                        System.out.println("✅ Chambre " + numMaint + " mise en maintenance");
                    } else {
                        System.out.println("❌ Chambre non trouvée");
                    }
                    break;
                case 3:
                    System.out.print("🔢 Numéro de chambre : ");
                    int numLib = scanner.nextInt();
                    scanner.nextLine();
                    Chambre chLib = GestionChambres.trouverChambre(numLib);
                    if (chLib != null) {
                        chLib.setEtat(EtatChambre.LIBRE);
                        System.out.println("✅ Chambre " + numLib + " libérée");
                    } else {
                        System.out.println("❌ Chambre non trouvée");
                    }
                    break;
                case 4:
                    retour = true;
                    break;
                default:
                    System.out.println("❌ Choix invalide");
            }
        }
    }

    private static void gererReservations() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║         GESTION DES RÉSERVATIONS           ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. ➕ Créer une réservation                ║");
            System.out.println("║ 2. 📋 Voir toutes les réservations         ║");
            System.out.println("║ 3. ➕ Ajouter un service                   ║");
            System.out.println("║ 4. ❌ Annuler une réservation              ║");
            System.out.println("║ 5. 🔙 Retour au menu principal             ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("👉 Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    try {
                        // ✅ ÉTAPE 1 : Sélectionner l'hôtel
                        System.out.println("\n┌────────────────────────────────────────────┐");
                        System.out.println("│         SÉLECTIONNER UN HÔTEL               │");
                        System.out.println("└────────────────────────────────────────────┘");
                        GestionHotels.afficherHotels();
                        System.out.print("👉 Nom de l'hôtel : ");
                        String nomHotel = scanner.nextLine();
                        Hotel hotel = GestionHotels.trouverHotel(nomHotel);

                        if (hotel == null) {
                            System.out.println("❌ Hôtel non trouvé");
                            break;
                        }
                        System.out.println("✅ Hôtel sélectionné : " + hotel.getNom());

                        // ✅ ÉTAPE 2 : Sélectionner le client
                        System.out.print("📧 Email client : ");
                        String email = scanner.nextLine();
                        Client client = GestionClients.trouverClient(email);
                        if (client == null) {
                            System.out.println("❌ Client non trouvé");
                            break;
                        }

                        // ✅ ÉTAPE 3 : Afficher les chambres de l'hôtel sélectionné
                        System.out.println("\n┌────────────────────────────────────────────┐");
                        System.out.println("│         CHAMBRES DISPONIBLES                │");
                        System.out.println("└────────────────────────────────────────────┘");
                        for (Chambre ch : hotel.getChambres()) {
                            String etatIcon = ch.getEtat() == EtatChambre.LIBRE ? "🟢" :
                                    ch.getEtat() == EtatChambre.OCCUPEE ? "🔴" : "🟠";
                            System.out.println(etatIcon + " Chambre " + ch.getNumero() +
                                    " (" + ch.getType() + ") | " + ch.getEtat() +
                                    " | " + ch.getPrixParNuit() + "€/nuit | Cap: " + ch.getCapacite());
                        }

                        // ✅ ÉTAPE 4 : Sélectionner la chambre
                        System.out.print("🔢 Numéro chambre : ");
                        int numCh = scanner.nextInt();
                        scanner.nextLine();
                        Chambre chambre = hotel.trouverChambre(numCh);
                        if (chambre == null) {
                            System.out.println("❌ Chambre non trouvée dans cet hôtel");
                            break;
                        }

                        // ✅ ÉTAPE 5 : Dates et personnes
                        System.out.print("📅 Date début (YYYY-MM-DD) : ");
                        LocalDate debut = LocalDate.parse(scanner.nextLine());
                        System.out.print("📅 Date fin (YYYY-MM-DD) : ");
                        LocalDate fin = LocalDate.parse(scanner.nextLine());
                        System.out.print("👥 Nombre de personnes : ");
                        int personnes = scanner.nextInt();
                        scanner.nextLine();

                        // ✅ ÉTAPE 6 : Créer la réservation
                        Reservation r = GestionReservations.creerReservation(client, chambre, hotel, debut, fin, personnes);
                        System.out.println("✅ Réservation créée avec succès ! ID: " + r.getId());

                    } catch (Exception e) {
                        System.out.println("❌ Erreur : " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n┌────────────────────────────────────────────┐");
                    System.out.println("│          LISTE DES RÉSERVATIONS            │");
                    System.out.println("└────────────────────────────────────────────┘");
                    for (Reservation r : GestionReservations.getReservations()) {
                        if (!r.isEstAnnulee()) {
                            System.out.println("• Réservation N°" + r.getId() +
                                    " | Client: " + r.getClient().getPrenom() + " " + r.getClient().getNom() +
                                    " | Chambre: " + r.getChambre().getNumero() +
                                    " | Du: " + r.getDateDebut() + " au " + r.getDateFin() +
                                    " | Personnes: " + r.getNombrePersonnes());
                        }
                    }
                    break;

                case 3:
                    System.out.println("📋 Services disponibles:");
                    System.out.println("1. Petit-déjeuner (15€)");
                    System.out.println("2. Blanchisserie (20€)");
                    System.out.println("3. Room Service (25€)");
                    System.out.println("4. Transport (50€)");
                    System.out.print("👉 Choix du service : ");
                    int serviceChoix = scanner.nextInt();
                    System.out.print("🔢 Quantité : ");
                    int quantite = scanner.nextInt();
                    scanner.nextLine();

                    TypeService typeService = null;
                    switch (serviceChoix) {
                        case 1: typeService = TypeService.PETIT_DEJEUNER; break;
                        case 2: typeService = TypeService.BLANCHISSERIE; break;
                        case 3: typeService = TypeService.ROOM_SERVICE; break;
                        case 4: typeService = TypeService.TRANSPORT; break;
                    }

                    if (typeService != null && !GestionReservations.getReservations().isEmpty()) {
                        Reservation derniere = GestionReservations.getReservations().get(GestionReservations.getReservations().size() - 1);
                        derniere.ajouterService(new Service(typeService, quantite));
                        System.out.println("✅ Service ajouté !");
                    }
                    break;

                case 4:
                    System.out.print("🔢 ID de la réservation à annuler : ");
                    int idAnnuler = scanner.nextInt();
                    scanner.nextLine();
                    Reservation rAnnuler = GestionReservations.trouverReservation(idAnnuler);
                    if (rAnnuler != null && !rAnnuler.isEstAnnulee()) {
                        rAnnuler.annuler();
                        System.out.println("✅ Réservation annulée !");
                    } else {
                        System.out.println("❌ Réservation non trouvée ou déjà annulée");
                    }
                    break;

                case 5:
                    retour = true;
                    break;

                default:
                    System.out.println("❌ Choix invalide");
            }
        }
    }

    private static void gererFacturation() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║           GESTION FACTURATION              ║");
            System.out.println("════════════════════════════════════════════╣");
            System.out.println("║ 1. 📄 Générer une facture                  ║");
            System.out.println("║ 2. 💳 Payer une facture                    ║");
            System.out.println("║ 3.  Voir toutes les factures             ║");
            System.out.println("║ 4. 🔙 Retour au menu principal             ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print(" Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    if (GestionReservations.getReservations().isEmpty()) {
                        System.out.println("❌ Aucune réservation disponible");
                        break;
                    }
                    Reservation r = GestionReservations.getReservations().get(0);
                    Facture f = GestionFacturation.genererFacture(r);
                    f.afficher();
                    break;
                case 2:
                    System.out.println("💳 Modes de paiement:");
                    System.out.println("1. Carte Bancaire");
                    System.out.println("2. Espèces");
                    System.out.println("3. Virement");
                    System.out.print("👉 Choix : ");
                    int mode = scanner.nextInt();
                    scanner.nextLine();

                    Paiement paiement = null;
                    switch (mode) {
                        case 1:
                            System.out.print("🔢 Numéro carte (16 chiffres) : ");
                            String carte = scanner.nextLine();
                            paiement = new PaiementCarte(carte);
                            break;
                        case 2:
                            paiement = new PaiementEspece();
                            break;
                        case 3:
                            System.out.print(" IBAN : ");
                            String iban = scanner.nextLine();
                            paiement = new PaiementVirement(iban);
                            break;
                    }

                    if (paiement != null && !GestionReservations.getReservations().isEmpty()) {
                        Facture facture = GestionFacturation.genererFacture(GestionReservations.getReservations().get(0));
                        GestionFacturation.payerFacture(facture, paiement);
                    }
                    break;
                case 3:
                    System.out.println("\n────────────────────────────────────────────┐");
                    System.out.println("│            LISTE DES FACTURES              │");
                    System.out.println("└────────────────────────────────────────────┘");
                    for (Facture fact : GestionFacturation.getFactures()) {
                        fact.afficher();
                    }
                    break;
                case 4:
                    retour = true;
                    break;
                default:
                    System.out.println("❌ Choix invalide");
            }
        }
    }

    private static void afficherStatistiques() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║            STATISTIQUES GLOBALES           ║");
        System.out.println("╠════════════════════════════════════════════╣");

        double taux = Statistiques.getTauxOccupation(GestionReservations.getReservations(),
                GestionChambres.getChambres().size());
        System.out.println("📊 Taux d'occupation : " + String.format("%.2f", taux) + "%");

        double revenu = Statistiques.getRevenuTotal(GestionFacturation.getFactures());
        System.out.println(" Revenu total : " + String.format("%.2f", revenu) + "€");

        Client plusFidele = Statistiques.getClientPlusFidele(GestionClients.getlisteClients());
        if (plusFidele != null) {
            System.out.println("🏆 Client le plus fidèle : " + plusFidele.getPrenom() + " " + plusFidele.getNom() +
                    " (" + plusFidele.getNombreSejours() + " séjours)");
        }

        long annulations = GestionReservations.getReservations().stream()
                .filter(Reservation::isEstAnnulee)
                .count();
        System.out.println("❌ Nombre d'annulations : " + annulations);

        System.out.println("╚════════════════════════════════════════════╝");
        System.out.print("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    // Dans la méthode sauvegarderDonnees() :
    private static void sauvegarderDonnees() {
        DataPersistence.sauvegarder(GestionClients.getlisteClients(), "clients.ser");
        DataPersistence.sauvegarder(GestionReservations.getReservations(), "reservations.ser");
        DataPersistence.sauvegarder(GestionFacturation.getFactures(), "factures.ser");
        System.out.println("✅ Données sauvegardées avec succès !");
    }

    // Dans la méthode chargerDonnees() :
    private static void chargerDonnees() {
        Object clientsData = DataPersistence.charger("clients.ser");
        Object reservationsData = DataPersistence.charger("reservations.ser");
        Object facturesData = DataPersistence.charger("factures.ser");
        if (clientsData != null) {
            System.out.println("✅ Données chargées depuis la sauvegarde précédente");
        } else {
            System.out.println("ℹ️  Aucune sauvegarde précédente trouvée");
        }
    }
}