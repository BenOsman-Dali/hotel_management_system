import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

            // ✅ ÉTAPE PRÉALABLE : Sélectionner l'hôtel (pour toutes les options sauf retour)
            Hotel hotelSelectionne = null;
            if (choix >= 1 && choix <= 3) {
                System.out.println("\n┌────────────────────────────────────────────┐");
                System.out.println("│         SÉLECTIONNER UN HÔTEL               │");
                System.out.println("└────────────────────────────────────────────┘");
                GestionHotels.afficherHotels();
                System.out.print("👉 Nom de l'hôtel : ");
                String nomHotel = scanner.nextLine();
                hotelSelectionne = GestionHotels.trouverHotel(nomHotel);

                if (hotelSelectionne == null) {
                    System.out.println("❌ Hôtel non trouvé");
                    continue;
                }
                System.out.println("✅ Hôtel sélectionné : " + hotelSelectionne.getNom());
            }

            switch (choix) {
                case 1:
                    // ✅ Voir les chambres de l'hôtel sélectionné
                    System.out.println("\n┌────────────────────────────────────────────┐");
                    System.out.println("│         CHAMBRES - " + hotelSelectionne.getNom().toUpperCase());
                    System.out.println("└────────────────────────────────────────────┘");

                    String RESET = "\u001B[0m";
                    String GREEN = "\u001B[32m";
                    String RED = "\u001B[31m";
                    String YELLOW = "\u001B[33m";

                    for (Chambre ch : hotelSelectionne.getChambres()) {
                        String couleur;
                        String etatText;

                        if (ch.getEtat() == EtatChambre.LIBRE) {
                            couleur = GREEN;
                            etatText = "LIBRE      ";
                        } else if (ch.getEtat() == EtatChambre.OCCUPEE) {
                            couleur = RED;
                            etatText = "OCCUPEE    ";
                        } else {
                            couleur = YELLOW;
                            etatText = "MAINTENANCE";
                        }

                        System.out.println(couleur + "●" + RESET + " Chambre " + ch.getNumero() +
                                " (" + ch.getType() + ") | " + couleur + etatText + RESET +
                                " | " + ch.getPrixParNuit() + "€/nuit | Cap: " + ch.getCapacite());
                    }
                    break;

                case 2:
                    // ✅ Mettre en maintenance (hôtel sélectionné)
                    System.out.println("\n┌────────────────────────────────────────────┐");
                    System.out.println("│         CHAMBRES DISPONIBLES                │");
                    System.out.println("└────────────────────────────────────────────┘");
                    for (Chambre ch : hotelSelectionne.getChambres()) {
                        if (!ch.isEnMaintenance()) {
                            System.out.println("• Chambre " + ch.getNumero() + " (" + ch.getType() + ")");
                        }
                    }

                    System.out.print("🔢 Numéro de chambre à mettre en maintenance : ");
                    int numMaint = scanner.nextInt();
                    scanner.nextLine();

                    Chambre chMaint = hotelSelectionne.trouverChambre(numMaint);
                    if (chMaint != null) {
                        if (chMaint.isEnMaintenance()) {
                            System.out.println("⚠️  Cette chambre est déjà en maintenance");
                        } else {
                            chMaint.setEtat(EtatChambre.MAINTENANCE);
                            System.out.println("✅ Chambre " + numMaint + " mise en maintenance");
                        }
                    } else {
                        System.out.println("❌ Chambre non trouvée dans cet hôtel");
                    }
                    break;

                case 3:
                    // ✅ Libérer une chambre (hôtel sélectionné)
                    System.out.println("\n┌────────────────────────────────────────────┐");
                    System.out.println("│         CHAMBRES EN MAINTENANCE             │");
                    System.out.println("└────────────────────────────────────────────┘");
                    boolean foundMaintenance = false;
                    for (Chambre ch : hotelSelectionne.getChambres()) {
                        if (ch.isEnMaintenance()) {
                            System.out.println("• Chambre " + ch.getNumero() + " (" + ch.getType() + ")");
                            foundMaintenance = true;
                        }
                    }
                    if (!foundMaintenance) {
                        System.out.println("Aucune chambre en maintenance dans cet hôtel");
                    }

                    System.out.print("🔢 Numéro de chambre à libérer : ");
                    int numLib = scanner.nextInt();
                    scanner.nextLine();

                    Chambre chLib = hotelSelectionne.trouverChambre(numLib);
                    if (chLib != null) {
                        if (chLib.isEnMaintenance()) {
                            chLib.setEtat(EtatChambre.LIBRE);
                            System.out.println("✅ Chambre " + numLib + " libérée");
                        } else {
                            System.out.println("⚠️  Cette chambre n'est pas en maintenance");
                        }
                    } else {
                        System.out.println("❌ Chambre non trouvée dans cet hôtel");
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
            System.out.println("║ 3. ❌ Annuler une réservation              ║");
            System.out.println("║ 4. 🔙 Retour au menu principal             ║");
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

                        // ✅ ÉTAPE 3 : Afficher les chambres disponibles
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

                        System.out.print("🔢 Numéro chambre : ");
                        int numCh = scanner.nextInt();
                        scanner.nextLine();
                        Chambre chambre = hotel.trouverChambre(numCh);
                        if (chambre == null) {
                            System.out.println("❌ Chambre non trouvée dans cet hôtel");
                            break;
                        }


                        System.out.print("📅 Date début (YYYY-MM-DD) : ");
                        LocalDate debut = LocalDate.parse(scanner.nextLine());
                        System.out.print("📅 Date fin (YYYY-MM-DD) : ");
                        LocalDate fin = LocalDate.parse(scanner.nextLine());
                        System.out.print("👥 Nombre de personnes : ");
                        int personnes = scanner.nextInt();
                        scanner.nextLine();

                        Reservation r = GestionReservations.creerReservation(client, chambre, hotel, debut, fin, personnes);
                        System.out.println("\n✅ Réservation créée avec succès ! ID: " + r.getId());


                        System.out.println("\n┌────────────────────────────────────────────┐");
                        System.out.println("│         AJOUTER DES SERVICES ?              │");
                        System.out.println("└────────────────────────────────────────────┘");
                        System.out.print("👉 Voulez-vous ajouter des services à cette réservation ? (oui/non) : ");
                        String reponse = scanner.nextLine();

                        if (reponse.equalsIgnoreCase("oui") || reponse.equalsIgnoreCase("o")) {
                            boolean continuerServices = true;
                            while (continuerServices) {
                                System.out.println("\n┌────────────────────────────────────────────┐");
                                System.out.println("│         SERVICES DISPONIBLES                │");
                                System.out.println("└────────────────────────────────────────────┘");
                                System.out.println("1. 🥐 Petit-déjeuner (15€/pers/jour)");
                                System.out.println("2. 🧺 Blanchisserie (20€/article)");
                                System.out.println("3. 🍽️  Room Service (25€/commande)");
                                System.out.println("4. 🚗 Transport (50€/trajet)");
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
                                    default:
                                        System.out.println("❌ Service invalide");
                                        continue;
                                }

                                if (typeService != null) {
                                    r.ajouterService(new Service(typeService, quantite));
                                    System.out.println("✅ Service ajouté : " + typeService.getLibelle() + " × " + quantite);
                                }


                                System.out.print("\n👉 Voulez-vous ajouter un autre service ? (oui/non) : ");
                                String reponseContinue = scanner.nextLine();
                                if (!reponseContinue.equalsIgnoreCase("oui") && !reponseContinue.equalsIgnoreCase("o")) {
                                    continuerServices = false;
                                }
                            }


                            System.out.println("\n┌────────────────────────────────────────────┐");
                            System.out.println("│         RÉCAPITULATIF DES SERVICES          │");
                            System.out.println("└────────────────────────────────────────────┘");
                            double totalServices = 0;
                            if (r.getServices().isEmpty()) {
                                System.out.println("Aucun service ajouté");
                            } else {
                                for (Service s : r.getServices()) {
                                    System.out.println("• " + s.getType().getLibelle() + " × " + s.getQuantite() +
                                            " = " + s.getPrixTotal() + "€");
                                    totalServices += s.getPrixTotal();
                                }
                                System.out.println("─────────────────────────────────────────────");
                                System.out.println("💰 Total Services : " + totalServices + "€");
                            }
                        } else {
                            System.out.println("ℹ️  Aucun service ajouté à cette réservation");
                        }

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

                case 4:
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
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. 📄 Générer une facture                  ║");
            System.out.println("║ 2. 💳 Payer une facture                    ║");
            System.out.println("║ 3. 📋 Voir toutes les factures             ║");
            System.out.println("║ 4. 🔙 Retour au menu principal             ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("👉 Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    genererUneFacture();
                    break;
                case 2:
                    payerUneFacture();
                    break;
                case 3:
                    voirToutesFactures();
                    break;
                case 4:
                    retour = true;
                    break;
                default:
                    System.out.println("❌ Choix invalide");
            }
        }
    }


    private static void genererUneFacture() {
        List<Reservation> reservations = GestionReservations.getReservations();

        if (reservations.isEmpty()) {
            System.out.println("❌ Aucune réservation disponible");
            return;
        }

        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│         RÉSERVATIONS À FACTURER            │");
        System.out.println("└────────────────────────────────────────────┘");

        int index = 1;
        for (Reservation r : reservations) {
            if (!r.isEstAnnulee()) {
                System.out.println("[" + index + "] Réservation N°" + r.getId() +
                        " | Client: " + r.getClient().getPrenom() + " " + r.getClient().getNom() +
                        " | Chambre: " + r.getChambre().getNumero() +
                        " | Du: " + r.getDateDebut() + " au " + r.getDateFin());
                index++;
            }
        }

        if (index == 1) {
            System.out.println("✅ Toutes les réservations ont déjà été facturées");
            return;
        }

        System.out.print("\n👉 Numéro de la réservation à facturer (1-" + (index-1) + ") : ");
        int choixRes = scanner.nextInt();
        scanner.nextLine();

        if (choixRes < 1 || choixRes > reservations.size()) {
            System.out.println("❌ Choix invalide");
            return;
        }

        int compteur = 0;
        Reservation reservationChoisie = null;
        for (Reservation r : reservations) {
            if (!r.isEstAnnulee()) {
                compteur++;
                if (compteur == choixRes) {
                    reservationChoisie = r;
                    break;
                }
            }
        }

        if (reservationChoisie == null) {
            System.out.println("❌ Réservation non trouvée");
            return;
        }


        boolean dejaFacturee = false;
        for (Facture f : GestionFacturation.getFactures()) {
            if (f.getReservation() == reservationChoisie) {
                dejaFacturee = true;
                break;
            }
        }

        if (dejaFacturee) {
            System.out.println("⚠️  Cette réservation a déjà une facture générée");
            System.out.print("Voulez-vous générer une nouvelle facture ? (oui/non) : ");
            String reponse = scanner.nextLine();
            if (!reponse.equalsIgnoreCase("oui")) {
                return;
            }
        }


        Facture facture = GestionFacturation.genererFacture(reservationChoisie);
        System.out.println("\n✅ Facture N°" + facture.getNumero() + " générée avec succès !");
        facture.afficher();
    }

    private static void payerUneFacture() {
        List<Facture> factures = GestionFacturation.getFactures();

        if (factures.isEmpty()) {
            System.out.println("❌ Aucune facture disponible");
            return;
        }


        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│            FACTURES À PAYER                │");
        System.out.println("└────────────────────────────────────────────┘");

        int index = 1;
        for (Facture f : factures) {
            if (f.getPaiement() == null) {
                System.out.println("[" + index + "] Facture N°" + f.getNumero() +
                        " | Client: " + f.getReservation().getClient().getNom() +
                        " | Montant: " + f.getTotalGeneral() + "€");
                index++;
            }
        }

        if (index == 1) {
            System.out.println("✅ Toutes les factures ont déjà été payées");
            return;
        }


        System.out.print("\n👉 Numéro de la facture à payer (1-" + (index-1) + ") : ");
        int choixFacture = scanner.nextInt();
        scanner.nextLine();

        if (choixFacture < 1 || choixFacture > factures.size()) {
            System.out.println("❌ Choix invalide");
            return;
        }


        int compteur = 0;
        Facture factureChoisie = null;
        for (Facture f : factures) {
            if (f.getPaiement() == null) {
                compteur++;
                if (compteur == choixFacture) {
                    factureChoisie = f;
                    break;
                }
            }
        }

        if (factureChoisie == null) {
            System.out.println("❌ Facture non trouvée");
            return;
        }


        System.out.println("\n💳 Modes de paiement:");
        System.out.println("1. Carte Bancaire");
        System.out.println("2. Espèces");
        System.out.println("3. Virement");
        System.out.print("👉 Choix : ");
        int mode = scanner.nextInt();
        scanner.nextLine();

        Paiement paiement = null;
        switch (mode) {
            case 1:
                System.out.print("🔢 Numéro carte (8 chiffres) : ");
                String carte = scanner.nextLine();
                paiement = new PaiementCarte(carte);
                break;
            case 2:
                paiement = new PaiementEspece();
                break;
            case 3:
                System.out.print("🏦 IBAN : ");
                String iban = scanner.nextLine();
                paiement = new PaiementVirement(iban);
                break;
            default:
                System.out.println("❌ Mode de paiement invalide");
                return;
        }


        GestionFacturation.payerFacture(factureChoisie, paiement);
        System.out.println("✅ Paiement enregistré avec succès !");
    }


    private static void voirToutesFactures() {
        List<Facture> factures = GestionFacturation.getFactures();

        if (factures.isEmpty()) {
            System.out.println("❌ Aucune facture générée");
            return;
        }

        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│            TOUTES LES FACTURES             │");
        System.out.println("└────────────────────────────────────────────┘");

        for (Facture f : factures) {
            System.out.println("Facture N°" + f.getNumero() +
                    " | Client: " + f.getReservation().getClient().getNom() +
                    " | Montant: " + f.getTotalGeneral() + "€" +
                    " | Statut: " + (f.getPaiement() != null ? "✅ Payée" : "⏳ En attente"));
        }

        System.out.print("\n👉 Voulez-vous afficher le détail d'une facture ? (oui/non) : ");
        String reponse = scanner.nextLine();

        if (reponse.equalsIgnoreCase("oui")) {
            System.out.print("🔢 Numéro de facture : ");
            int numFacture = scanner.nextInt();
            scanner.nextLine();

            for (Facture f : factures) {
                if (f.getNumero() == numFacture) {
                    f.afficher();
                    return;
                }
            }
            System.out.println("❌ Facture non trouvée");
        }
    }

    private static void afficherStatistiques() {
        try {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║            STATISTIQUES GLOBALES           ║");
            System.out.println("╚════════════════════════════════════════════╝");


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


            List<Reservation> reservationsHotel = new ArrayList<>();
            for (Reservation r : GestionReservations.getReservations()) {
                if (r.getHotel() != null && r.getHotel().getNom().equals(hotel.getNom())) {
                    reservationsHotel.add(r);
                }
            }


            List<Facture> facturesHotel = new ArrayList<>();
            for (Facture f : GestionFacturation.getFactures()) {
                if (f.getReservation().getHotel() != null &&
                        f.getReservation().getHotel().getNom().equals(hotel.getNom())) {
                    facturesHotel.add(f);
                }
            }


            boolean retour = false;
            while (!retour) {
                System.out.println("\n╔════════════════════════════════════════════╗");
                System.out.println("║         STATISTIQUES - " + hotel.getNom().toUpperCase());
                System.out.println("╠════════════════════════════════════════════╣");
                System.out.println("║ 1. 📊 Taux d'occupation                    ║");
                System.out.println("║ 2. 💰 Revenu total                         ║");
                System.out.println("║ 3. 🏆 Chambre la plus réservée             ║");
                System.out.println("║ 4. 👥 Client le plus fidèle                ║");
                System.out.println("║ 5. ❌ Nombre d'annulations                 ║");
                System.out.println("║ 6. 📈 Toutes les statistiques              ║");
                System.out.println("║ 7. 🔙 Retour au menu principal             ║");
                System.out.println("╚════════════════════════════════════════════╝");
                System.out.print("👉 Votre choix : ");

                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        afficherTauxOccupation(hotel, reservationsHotel);
                        break;
                    case 2:
                        afficherRevenuTotal(facturesHotel);
                        break;
                    case 3:
                        afficherChambrePlusReservee(hotel, reservationsHotel);
                        break;
                    case 4:
                        afficherClientPlusFidele(hotel, reservationsHotel);
                        break;
                    case 5:
                        afficherNombreAnnulations(reservationsHotel);
                        break;
                    case 6:
                        afficherToutesStatistiques(hotel, reservationsHotel, facturesHotel);
                        break;
                    case 7:
                        retour = true;
                        break;
                    default:
                        System.out.println("❌ Choix invalide");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }

    private static void afficherTauxOccupation(Hotel hotel, List<Reservation> reservations) {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│         TAUX D'OCCUPATION                   │");
        System.out.println("└────────────────────────────────────────────┘");

        int totalChambres = hotel.getChambres().size();
        long chambresOccupees = reservations.stream()
                .filter(r -> !r.isEstAnnulee())
                .count();

        double taux = totalChambres > 0 ? (chambresOccupees * 100.0) / totalChambres : 0;

        System.out.println("🏨 Hôtel : " + hotel.getNom());
        System.out.println("📊 Total chambres : " + totalChambres);
        System.out.println("🔴 Chambres occupées : " + chambresOccupees);
        System.out.println("🟢 Chambres libres : " + (totalChambres - chambresOccupees));
        System.out.println("─────────────────────────────────────────────");
        System.out.println("📈 Taux d'occupation : " + String.format("%.2f", taux) + "%");
    }

    private static void afficherRevenuTotal(List<Facture> factures) {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│         REVENU TOTAL                        │");
        System.out.println("└────────────────────────────────────────────┘");

        double revenuTotal = factures.stream()
                .mapToDouble(Facture::getTotalGeneral)
                .sum();

        double revenuPaye = factures.stream()
                .filter(f -> f.getPaiement() != null)
                .mapToDouble(Facture::getTotalGeneral)
                .sum();

        double revenuEnAttente = revenuTotal - revenuPaye;

        System.out.println("💰 Revenu total généré : " + String.format("%.2f", revenuTotal) + "€");
        System.out.println("✅ Revenu encaissé : " + String.format("%.2f", revenuPaye) + "€");
        System.out.println("⏳ Revenu en attente : " + String.format("%.2f", revenuEnAttente) + "€");
    }

    private static void afficherChambrePlusReservee(Hotel hotel, List<Reservation> reservations) {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│     CHAMBRE LA PLUS RÉSERVÉE                │");
        System.out.println("└────────────────────────────────────────────┘");

        if (reservations.isEmpty()) {
            System.out.println("ℹ️  Aucune réservation pour cet hôtel");
            return;
        }

        java.util.Map<Integer, Integer> comptage = new java.util.HashMap<>();
        for (Reservation r : reservations) {
            if (!r.isEstAnnulee()) {
                int numChambre = r.getChambre().getNumero();
                comptage.put(numChambre, comptage.getOrDefault(numChambre, 0) + 1);
            }
        }

        if (comptage.isEmpty()) {
            System.out.println("ℹ️  Aucune réservation active");
            return;
        }

        int chambrePlusReservee = 0;
        int maxReservations = 0;
        for (java.util.Map.Entry<Integer, Integer> entry : comptage.entrySet()) {
            if (entry.getValue() > maxReservations) {
                maxReservations = entry.getValue();
                chambrePlusReservee = entry.getKey();
            }
        }

        Chambre ch = hotel.trouverChambre(chambrePlusReservee);
        System.out.println("🏆 Chambre N°" + chambrePlusReservee +
                " (" + (ch != null ? ch.getType() : "Inconnue") + ")");
        System.out.println("📊 Nombre de réservations : " + maxReservations);
    }

    private static void afficherClientPlusFidele(Hotel hotel, List<Reservation> reservations) {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│     CLIENT LE PLUS FIDÈLE                   │");
        System.out.println("└────────────────────────────────────────────┘");

        if (reservations.isEmpty()) {
            System.out.println("ℹ️  Aucune réservation pour cet hôtel");
            return;
        }

        java.util.Map<Client, Integer> comptage = new java.util.HashMap<>();
        for (Reservation r : reservations) {
            if (!r.isEstAnnulee()) {
                Client client = r.getClient();
                comptage.put(client, comptage.getOrDefault(client, 0) + 1);
            }
        }

        if (comptage.isEmpty()) {
            System.out.println("ℹ️  Aucun client trouvé");
            return;
        }

        Client clientPlusFidele = null;
        int maxSejours = 0;
        for (java.util.Map.Entry<Client, Integer> entry : comptage.entrySet()) {
            if (entry.getValue() > maxSejours) {
                maxSejours = entry.getValue();
                clientPlusFidele = entry.getKey();
            }
        }

        if (clientPlusFidele != null) {
            System.out.println("🏆 Client : " + clientPlusFidele.getPrenom() + " " + clientPlusFidele.getNom());
            System.out.println("📧 Email : " + clientPlusFidele.getEmail());
            System.out.println("📊 Réservations dans cet hôtel : " + maxSejours);
            System.out.println("🎁 Réduction fidélité : " + (clientPlusFidele.getTauxReduction() * 100) + "%");
        }
    }

    private static void afficherNombreAnnulations(List<Reservation> reservations) {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│         NOMBRE D'ANNULATIONS                │");
        System.out.println("└────────────────────────────────────────────┘");

        long annulations = reservations.stream()
                .filter(Reservation::isEstAnnulee)
                .count();

        long totales = reservations.size();
        double pourcentage = totales > 0 ? (annulations * 100.0) / totales : 0;

        System.out.println("❌ Réservations annulées : " + annulations);
        System.out.println("📋 Total réservations : " + totales);
        System.out.println("📈 Taux d'annulation : " + String.format("%.2f", pourcentage) + "%");
    }

    private static void afficherToutesStatistiques(Hotel hotel, List<Reservation> reservations, List<Facture> factures) {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║         STATISTIQUES COMPLÈTES              ║");
        System.out.println("║         " + hotel.getNom().toUpperCase());
        System.out.println("╚════════════════════════════════════════════╝");

        // Taux d'occupation
        int totalChambres = hotel.getChambres().size();
        long chambresOccupees = reservations.stream().filter(r -> !r.isEstAnnulee()).count();
        double taux = totalChambres > 0 ? (chambresOccupees * 100.0) / totalChambres : 0;
        System.out.println("📊 Taux d'occupation : " + String.format("%.2f", taux) + "%");

        // Revenu
        double revenuTotal = factures.stream().mapToDouble(Facture::getTotalGeneral).sum();
        System.out.println("💰 Revenu total : " + String.format("%.2f", revenuTotal) + "€");

        // Annulations
        long annulations = reservations.stream().filter(Reservation::isEstAnnulee).count();
        System.out.println("❌ Annulations : " + annulations);

        // Total réservations
        System.out.println("📋 Total réservations : " + reservations.size());

        // Total chambres
        System.out.println("🚪 Total chambres : " + totalChambres);

        System.out.println("═══════════════════════════════════════════");
    }

    // Dans la méthode sauvegarderDonnees() :
    private static void sauvegarderDonnees() {
        System.out.println("\n💾 Sauvegarde en cours...");

        DataPersistence.sauvegarder(GestionClients.getlisteClients(), "clients.ser");
        DataPersistence.sauvegarder(GestionReservations.getReservations(), "reservations.ser");
        DataPersistence.sauvegarder(GestionFacturation.getFactures(), "factures.ser");
        DataPersistence.sauvegarder(GestionHotels.getHotels(), "hotels.ser");
        DataPersistence.sauvegarder(GestionAvis.getAvis(), "avis.ser");

        System.out.println("✅ Toutes les données ont été sauvegardées avec succès !");
    }

    private static void chargerDonnees() {
        System.out.println("\n📂 Chargement des données sauvegardées...");

        // ✅ Charger et ASSIGNER les clients
        @SuppressWarnings("unchecked")
        List<Client> clientsCharges = (List<Client>) DataPersistence.charger("clients.ser");
        if (clientsCharges != null) {
            GestionClients.setlisteClients(clientsCharges);
            System.out.println("✅ " + clientsCharges.size() + " client(s) chargé(s)");
        }


        List<Reservation> reservationsChargees = (List<Reservation>) DataPersistence.charger("reservations.ser");
        if (reservationsChargees != null) {
            GestionReservations.setReservations(reservationsChargees);
            System.out.println("✅ " + reservationsChargees.size() + " réservation(s) chargée(s)");
        }


        List<Facture> facturesChargees = (List<Facture>) DataPersistence.charger("factures.ser");
        if (facturesChargees != null) {
            GestionFacturation.setFactures(facturesChargees);
            System.out.println("✅ " + facturesChargees.size() + " facture(s) chargée(s)");
        }


        List<Hotel> hotelsCharges = (List<Hotel>) DataPersistence.charger("hotels.ser");
        if (hotelsCharges != null) {
            GestionHotels.setHotels(hotelsCharges);
            System.out.println("✅ " + hotelsCharges.size() + " hôtel(s) chargé(s)");
        }

        List<Avis> avisCharges = (List<Avis>) DataPersistence.charger("avis.ser");
        if (avisCharges != null) {
            GestionAvis.setAvis(avisCharges);
            System.out.println("✅ " + avisCharges.size() + " avis chargé(s)");
        }

        System.out.println("ℹ️  Prêt à démarrer !");
    }
}