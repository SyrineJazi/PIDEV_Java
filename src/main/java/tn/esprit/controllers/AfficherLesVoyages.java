package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.interfaces.MyListener;
import tn.esprit.models.Voyage;
import tn.esprit.services.ServiceVoyage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherLesVoyages implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox chosenVoyageCard;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Label voyageNameLabel;

    @FXML
    private Label voyagePriceLabel;

    @FXML
    private ImageView voyageimg;
    @FXML
    private Button VoyEdit_btn;
    @FXML
    private ImageView delete_button;

    ArrayList<Voyage> list_voyages = new ArrayList<>();
    private ObservableList<Voyage> listeObservable_voyages = FXCollections.observableArrayList(getList_voyages());
    private MyListener myListener;
    private Image image;
    private ArrayList<Voyage> getList_voyages(){
        ServiceVoyage sv = new ServiceVoyage();
        return sv.getAll();
    }
    private int setChosenVoyage(Voyage voyage){
        voyageNameLabel.setText(voyage.getNom());
        voyagePriceLabel.setText(voyage.getPrix() + VoyageHome.CURRENCY);
        File imageFile = new File(voyage.getImage1());
        int ID = voyage.getId();
        // Check if the file exists
        if (imageFile.exists()) {
            // Load the image from the file
            Image image = new Image(imageFile.toURI().toString());
            voyageimg.setImage(image);
            chosenVoyageCard.setStyle("-fx-background-color: #eef4f3" + ";\n" +
                    "    -fx-background-radius: 30;");
        } else {
            // Handle case where image file is not found
            System.out.println("Image file not found: " + voyage.getImage1());
            // Optionally, you can set a default image or handle the situation differently
        }
        return ID;
    }
    @FXML
    public void OnVoyEdit(ActionEvent event) {
        // Retrieve the chosen voyage name
        String name = voyageNameLabel.getText();

        Voyage chosenvoyage = null; // Declare without initialization

        // Loop through the list of voyages to find the chosen voyage
        for (Voyage unit : listeObservable_voyages) {
            if (unit.getNom().equals(name)) {
                chosenvoyage = unit; // Assign the found voyage
                System.out.println("the thing worked");
                break; // Break out of the loop after finding the voyage
            }
        }

        // Check if the chosen voyage was found
        if (chosenvoyage != null) {
            // Pass the chosen voyage to the next page
            System.out.println("IM NAVIGATING");
            navigateToModifierVoyage(chosenvoyage);

        } else {
            // Handle case where the chosen voyage is not found
            System.out.println("Chosen voyage not found: " + name);
        }
    }

    private void navigateToModifierVoyage(Voyage chosenVoyage) {
        // Load the FXML file of the next page
        System.out.println("I NAVIGATED");

        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/ModifierVoyage.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Get the controller of the next page
        ModifierVoyage Controller = loader.getController();
        // Pass the chosen voyage to the next page controller
        Controller.getNameToSearchWith(chosenVoyage);
        Controller.setData(chosenVoyage);
        // Display the next page
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void navigateToAjouterVoyage(MouseEvent event) {
        // Load the FXML file of the next page
        System.out.println("I NAVIGATED");

        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/AjouterVoyage.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        /*
        Stage currentStage = (Stage) root.getScene().getWindow();
        currentStage.close();

         */
    }

    @FXML
    void deleteVoyage(MouseEvent event) {
        // Retrieve the chosen voyage name
        String name = voyageNameLabel.getText();

        Voyage chosenvoyage = null; // Declare without initialization
        // Loop through the list of voyages to find the chosen voyage
        for (Voyage unit : listeObservable_voyages) {
            if (unit.getNom().equals(name)) {
                chosenvoyage = unit; // Assign the found voyage
                System.out.println("the thing worked");
                break; // Break out of the loop after finding the voyage
            }
        }
        // Check if the chosen voyage was found
        if (chosenvoyage != null) {
            // Pass the chosen voyage to the next page
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de la suppression");
            alert.setContentText("Etes-vous sûre de vouloir supprimer cet item ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                System.out.println("IM deleting");
                ServiceVoyage sv = new ServiceVoyage();
                sv.delete(chosenvoyage.getId());

                // Update the observable list by removing the deleted voyage
                listeObservable_voyages.remove(chosenvoyage);
                // Update the UI by rebuilding the grid
                buildGrid();

            }
            else{
                alert.close();
            }

        } else {
            // Handle case where the chosen voyage is not found
            System.out.println("Chosen voyage not found: " + name);
        }

    }
    private boolean isGridEmpty(){
        ObservableList<Node> children = grid.getChildren();
        return children.isEmpty();
    }
    private void buildGrid() {
        if(!isGridEmpty()){
            grid.getChildren().clear(); // Clear the grid
        }
        int column = 0;
        int row = 1;
        try {
            for (Voyage voyage : listeObservable_voyages) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Voyage_item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                VoyageItem controller = fxmlLoader.getController();
                controller.setData(voyage, myListener);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
     if(!listeObservable_voyages.isEmpty()){
         setChosenVoyage(listeObservable_voyages.get(0));
         myListener = new MyListener(){
             @Override
             public void onClickListener(Voyage voyage) {
                 setChosenVoyage(voyage);
             }
         };
     }
     buildGrid();
    }




















}
