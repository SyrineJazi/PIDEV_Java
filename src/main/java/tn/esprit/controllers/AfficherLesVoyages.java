package tn.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tn.esprit.interfaces.MyListener;
import tn.esprit.models.Voyage;
import tn.esprit.services.ServiceVoyage;

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

    ArrayList<Voyage> list_voyages = new ArrayList<>();
    private MyListener myListener;
    private Image image;
    private ArrayList<Voyage> getList_voyages(){
        ServiceVoyage sv = new ServiceVoyage();
        return sv.getAll();
    }
    private void setChosenVoyage(Voyage voyage){
        voyageNameLabel.setText(voyage.getNom());
        voyagePriceLabel.setText(voyage.getPrix() + VoyageHome.CURRENCY);
        File imageFile = new File(voyage.getImage1());

        // Check if the file exists
        if (imageFile.exists()) {
            // Load the image from the file
            Image image = new Image(imageFile.toURI().toString());
            voyageimg.setImage(image);
            chosenVoyageCard.setStyle("-fx-background-color: #60BEB3" + ";\n" +
                    "    -fx-background-radius: 30;");
        } else {
            // Handle case where image file is not found
            System.out.println("Image file not found: " + voyage.getImage1());
            // Optionally, you can set a default image or handle the situation differently
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
     list_voyages = getList_voyages();
     if(!list_voyages.isEmpty()){
         setChosenVoyage(list_voyages.get(0));
         myListener = new MyListener(){
             @Override
             public void onClickListener(Voyage voyage) {
                 setChosenVoyage(voyage);
             }
         };
     }
     int column = 0;
     int row = 1;
     try{
         for(Voyage voyage : list_voyages){
             FXMLLoader fxmlLoader = new FXMLLoader();
             fxmlLoader.setLocation(getClass().getResource("/Voyage_item.fxml"));
             AnchorPane anchorPane = fxmlLoader.load();

             VoyageItem controller = fxmlLoader.getController();
             controller.setData(voyage,myListener);
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
     }
     catch (IOException e) {
         e.printStackTrace();
     }



    }

}
