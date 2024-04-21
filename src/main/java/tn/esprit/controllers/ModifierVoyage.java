package tn.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Voyage;

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

    void setData(Voyage voyage){
        TFvoy_description_old.setText(voyage.getDescription());

        TFvoy_destination_old.setText(voyage.getDestination());

        TFvoy_image_old.setText(voyage.getImage1());

        TFvoy_nom_old.setText(voyage.getNom());

        TFvoy_prix_old.setText(String.valueOf(voyage.getPrix()));;
        VoyAjout_date_debut_old.setValue(voyage.getDate_debut().toLocalDate());


        VoyAjout_date_fin_old.setValue(voyage.getDate_fin().toLocalDate());

        VoyType1_old.isSelected();
        System.out.println("WE ARE PUTTING STUFF HERE");

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
