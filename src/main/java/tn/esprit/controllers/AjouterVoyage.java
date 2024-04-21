package tn.esprit.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import tn.esprit.models.Voyage;
import tn.esprit.services.ServiceVoyage;

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
        LocalDateTime date_debut = LocalDateTime.of(2002, 2, 25, 0, 0, 0);
        LocalDateTime date_fin = LocalDateTime.of(2003, 3, 25, 0, 0, 0);

        String type = "";
        if(VoyType1.isSelected()){type = VoyType1.getText();}
        else if (VoyType2.isSelected()){type = VoyType2.getText();}

        String file = TFvoy_image.getText();

        // Vérifier si le fichier existe
        if (!Files.exists(Paths.get(file))) {
            afficherErreur("Le fichier spécifié n'existe pas.");
            return;
        }

        Voyage new_voyage = new Voyage(ID,TFvoy_nom.getText(),Integer.parseInt(TFvoy_prix.getText()),TFvoy_destination.getText(),
                TFvoy_description.getText(),TFvoy_image.getText(), VoyAjout_date_debut.getValue().atStartOfDay(),
                VoyAjout_date_fin.getValue().atStartOfDay(),type);
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
    void initialize() {
    }

}
