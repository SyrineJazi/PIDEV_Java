package tn.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Voyage;
import tn.esprit.services.ServiceVoyage;

public class ModifierVoyage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Blog_btn;

    @FXML
    private Button Event_btn;

    @FXML
    private Button Home_btn;

    @FXML
    private Button ListVoyage_btn;

    @FXML
    private Button Spot_btn;

    @FXML
    private TextField TFvoy_description_old;

    @FXML
    private TextField TFvoy_destination_old;

    @FXML
    private TextField TFvoy_image_old;

    @FXML
    private TextField TFvoy_nom_old;

    @FXML
    private TextField TFvoy_prix_old;

    @FXML
    private HBox VosVoyages_btn;

    @FXML
    private DatePicker VoyAjout_date_debut_old;

    @FXML
    private DatePicker VoyAjout_date_fin_old;

    @FXML
    private RadioButton VoyType1_old;

    @FXML
    private RadioButton VoyType2_old;

    @FXML
    private Button modifier_btn;

    @FXML
    private ToggleGroup type_voyage;

    ServiceVoyage sv = new ServiceVoyage();
    ArrayList<Voyage> list_voyages = sv.getAll();

    private String nameToSearchWith ="";
    void getNameToSearchWith(Voyage voyage){
        nameToSearchWith = voyage.getNom();
        System.out.println("the word is :" + nameToSearchWith);
    }
    void setData(Voyage voyage){
        TFvoy_description_old.setText(voyage.getDescription());
        TFvoy_destination_old.setText(voyage.getDestination());
        TFvoy_image_old.setText(voyage.getImage1());
        TFvoy_nom_old.setText(voyage.getNom());
        TFvoy_prix_old.setText(String.valueOf(voyage.getPrix()));
        VoyAjout_date_debut_old.setValue(voyage.getDate_debut().toLocalDate());
        VoyAjout_date_fin_old.setValue(voyage.getDate_fin().toLocalDate());
        VoyType1_old.isSelected();
        System.out.println("WE ARE PUTTING STUFF HERE");
    }

    @FXML
    void EditVoyage(ActionEvent event) {
        int ID = 0;
        Voyage chosenvoyage = null;
        for (Voyage unit : list_voyages) {
            System.out.println("the unit is named : " + unit.getNom());
            if (unit.getNom().toLowerCase().equals(nameToSearchWith.toLowerCase())) {
                chosenvoyage = unit;
                ID = unit.getId();
                break; // Break out of the loop after finding the voyage
            }
        }
        if (chosenvoyage == null) {
            System.out.println("it's NULLLLLL");
        }
        String type = "";
        if (VoyType1_old.isSelected()) {
            type = VoyType1_old.getText();
        } else if (VoyType2_old.isSelected()) {
            type = VoyType2_old.getText();
        }

        String file = TFvoy_image_old.getText();

        // Vérifier si le fichier existe
        if (!Files.exists(Paths.get(file))) {
            afficherErreur("Le fichier spécifié n'existe pas.");
            return;
        }

        Voyage update = new Voyage(ID, TFvoy_nom_old.getText(), Integer.parseInt(TFvoy_prix_old.getText()), TFvoy_destination_old.getText(),
                TFvoy_description_old.getText(), TFvoy_image_old.getText(), VoyAjout_date_debut_old.getValue().atStartOfDay(),
                VoyAjout_date_fin_old.getValue().atStartOfDay(), type);
        try {
            ServiceVoyage serviceVoyage = new ServiceVoyage();
            serviceVoyage.update(update);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le voyage a été modifié avec succés.");
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void afficherErreur(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    void choisirImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imagePath = file.toURI().toString();
            Image image = new Image(imagePath);
            //imageviewFile.setImage(image);
            TFvoy_image_old.setText(file.getAbsolutePath());
        }
    }

    @FXML
    void navigateToAfficherLesVoyages(ActionEvent event) {
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
        assert Blog_btn != null : "fx:id=\"Blog_btn\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert Event_btn != null : "fx:id=\"Event_btn\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert Home_btn != null : "fx:id=\"Home_btn\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert ListVoyage_btn != null : "fx:id=\"ListVoyage_btn\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert Spot_btn != null : "fx:id=\"Spot_btn\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert TFvoy_description_old != null : "fx:id=\"TFvoy_description_old\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert TFvoy_destination_old != null : "fx:id=\"TFvoy_destination_old\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert TFvoy_image_old != null : "fx:id=\"TFvoy_image_old\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert TFvoy_nom_old != null : "fx:id=\"TFvoy_nom_old\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert TFvoy_prix_old != null : "fx:id=\"TFvoy_prix_old\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert VosVoyages_btn != null : "fx:id=\"VosVoyages_btn\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert VoyAjout_date_debut_old != null : "fx:id=\"VoyAjout_date_debut_old\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert VoyAjout_date_fin_old != null : "fx:id=\"VoyAjout_date_fin_old\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert VoyType1_old != null : "fx:id=\"VoyType1_old\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert VoyType2_old != null : "fx:id=\"VoyType2_old\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert modifier_btn != null : "fx:id=\"modifier_btn\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";
        assert type_voyage != null : "fx:id=\"type_voyage\" was not injected: check your FXML file 'ModifierVoyage.fxml'.";

    }

}
