package tn.esprit;

import tn.esprit.models.Activite;
import tn.esprit.models.Voyage;
import tn.esprit.services.ServiceActivite;
import tn.esprit.services.ServiceVoyage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ServiceVoyage.ItemNotFoundException, ServiceActivite.ItemNotFoundException {

        // THE MENU
        ServiceVoyage sv = new ServiceVoyage();
        ServiceActivite sa = new ServiceActivite();
        Scanner scanner = new Scanner(System.in);
        String word ="";
        int choice ;
        do {
            System.out.println("== MENU ==");
            System.out.println("---------------VOYAGES--------------------");
            System.out.println("1. Afficher tous les voyages");
            System.out.println("2. Ajouter un voyage");
            System.out.println("3. Modifier un voyage");
            System.out.println("4. Supprimer un voyage");
            System.out.println("---------------ACTIVITES------------------");
            System.out.println("5. Afficher tous les activites");
            System.out.println("6. Ajouter un activite");
            System.out.println("7. Modifier un activite");
            System.out.println("8. Supprimer un activite");
            System.out.println("============ TRI et RECHERCHE =========");
            System.out.println("9. Trier les voyages");
            System.out.println("10. Trier les activités");
            System.out.println("11. Chercher un voyage par Nom/destination");
            System.out.println("12. Chercher une activité par Nom");
            System.out.println("0. Quitter");
            System.out.println("Entrez votre choix :");
            choice = scanner.nextInt();
            switch(choice){
                case 1 :
                    System.out.println("---- Liste des voyages ----");
                    //System.out.println(sv.getAll());
                    sv.displayAll(sv.getAll());
                    break;
                case 2 :
                    System.out.println("---- Ajout ----");
                    Voyage new_voyage = sv.saisie();
                    sv.add(new_voyage);
                    break;
                case 3 :
                    System.out.println("---- Modification ----");
                    Voyage update_voyage = sv.saisie();
                    sv.update(update_voyage);
                    break;
                case 4 :
                    System.out.println("---- Suppression ----");
                    System.out.println("ID du voyage à supprimer :");
                    int voyageID = scanner.nextInt();
                    boolean result = sv.delete(voyageID);
                    break;
                case 5 :
                    System.out.println("---- Liste des activites ----");
                    //System.out.println(sa.getAll());
                    sa.displayAll(sa.getAll());
                    break;
                case 6 :
                    System.out.println("---- Ajout ----");
                    Activite new_activite = sa.saisie();
                    sa.add(new_activite);
                    break;
                case 7 :
                    System.out.println("---- Modification ----");
                    Activite update_activite = sa.saisie();
                    sa.update(update_activite);
                    break;
                case 8 :
                    System.out.println("---- Suppression ----");
                    System.out.println("ID du l'activté à supprimer :");
                    int activiteID = scanner.nextInt();
                    boolean result_activite = sa.delete(activiteID);
                    break;
                case 9 :
                    ArrayList<Voyage> voyages_sorted = sv.trierVoyagePar_type_nom_destination();
                    sv.displayAll(voyages_sorted);
                    break;
                case 10 :
                    ArrayList<Activite> activite_sorted = sa.trierParType_nom();
                    sa.displayAll(activite_sorted);
                    break;
                case 11:
                    System.out.println("Search...");
                    scanner.nextLine();
                    word = scanner.nextLine();
                    ArrayList<Voyage> found_voyages = sv.findByNom_ouDestination(word);
                    sv.displayAll(found_voyages);
                    break;
                case 12:
                    System.out.println("Search...");
                    scanner.nextLine();
                    word = scanner.nextLine();
                    ArrayList<Activite> found_activites = sa.findByNom(word);
                    sa.displayAll(found_activites);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }while(choice != 0);

    }
}