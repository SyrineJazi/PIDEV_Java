package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Voyage;
import tn.esprit.services.ServiceVoyage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class AjouterVoyage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker VoyAjout_date_debut;

    @FXML
    private DatePicker VoyAjout_date_fin;

    @FXML
    private TextField TFvoy_description;

    @FXML
    private TextField TFvoy_destination;

    @FXML
    private TextField TFvoy_image;

    @FXML
    private TextField TFvoy_nom;

    @FXML
    private TextField TFvoy_prix;

    @FXML
    private RadioButton VoyType1;

    @FXML
    private RadioButton VoyType2;


    @FXML
    private Button confirmer_ajoutVoy_btn;

    @FXML
    void AjouterVoyage(ActionEvent event) {
        Random random = new Random();
        int ID = random.nextInt(Integer.MAX_VALUE) + 1;

        // Radio buttons pour le type
        String type = "";
        if(VoyType1.isSelected()){type = VoyType1.getText();}
        else if (VoyType2.isSelected()){type = VoyType2.getText();}

        String file = TFvoy_image.getText();

        // Control de saisie -DEBUT
        if (!Files.exists(Paths.get(file))) {
            afficherErreur("Le fichier spécifié n'existe pas.");
            return;
        }

        String voyageNom = TFvoy_nom.getText();
        String voyagePrixText = TFvoy_prix.getText();
        String voyageDestination = TFvoy_destination.getText();
        String voyageDescription = TFvoy_description.getText();
        String voyageImage = TFvoy_image.getText();

        LocalDate debutDate = VoyAjout_date_debut.getValue();
        LocalDate finDate = VoyAjout_date_fin.getValue();

        // Verifier L'input
        if (voyageNom.isEmpty() || voyagePrixText.isEmpty() || voyageDestination.isEmpty() ||
                voyageDescription.isEmpty() || voyageImage.isEmpty() || debutDate == null || finDate == null) {
            afficherErreur("Veuillez remplir tous les champs.");
            return;
        }

        // TFvoy_prix est positive
        int voyagePrix;
        try {
            voyagePrix = Integer.parseInt(voyagePrixText);
            if (voyagePrix <= 0) {
                afficherErreur("Le prix du voyage doit être un nombre positif.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Le prix du voyage doit être un nombre entier.");
            return;
        }

        LocalDate dateToday = java.time.LocalDate.now();

        // Valider la chronologie des dates
        if (!debutDate.isBefore(finDate)) {
            afficherErreur("La date de début doit être antérieure à la date de fin.");
            return;
        }
        //Valider que la date de début choisi n'est pas dans le passé
        if(debutDate.isBefore(dateToday)){
            afficherErreur("Vous pouvez pas voyager dans le passé (｡T ω T｡)");
            return;
        }

        LocalDateTime debutDateTime = debutDate.atStartOfDay();
        LocalDateTime finDateTime = finDate.atStartOfDay();


        // Control de saisie -FIN

        Voyage new_voyage = new Voyage(ID, voyageNom, voyagePrix, voyageDestination, voyageDescription,
                voyageImage, debutDateTime, finDateTime, type);

        try {
            ServiceVoyage serviceVoyage = new ServiceVoyage();
            serviceVoyage.add(new_voyage);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le voyage a été ajouté avec succés.");
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void choisirImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imagePath = file.toURI().toString();
            Image image = new Image(imagePath);
            //imageviewFile.setImage(image);
            TFvoy_image.setText(file.getAbsolutePath());
        }
    }
    private void afficherErreur(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
    @FXML
    private void navigateToAfficherLesVoyages(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VoyageListInterface.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
    }

}
