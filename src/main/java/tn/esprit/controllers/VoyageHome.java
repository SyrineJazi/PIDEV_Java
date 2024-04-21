package tn.esprit.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VoyageHome extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static final String CURRENCY = "DT";


    @Override
    public void start(Stage primaryStage) {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterVoyage.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VoyageListInterface.fxml"));
        try
        {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
