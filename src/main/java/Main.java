import tn.esprit.utils.MyDataBase;

import java.util.Date;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Date now = new Date();

        // Utilisation de la méthode getTime() pour obtenir le timestamp en millisecondes
        long timestamp = now.getTime();


           MyDataBase myDatabase = new MyDataBase();
                Connection connection = myDatabase.getCnx();

                if (connection != null) {
                    System.out.println("Connexion à la base de données établie avec succès !");

                } else {
                    System.out.println("Erreur lors de la connexion à la base de données.");
                }


        // Affichage du timestamp
        System.out.println("Timestamp en millisecondes : " + timestamp);
        System.out.println("Hello world!");
    }
}