package tn.esprit.controler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/AjouterBlog.fxml"));
        try {
            Parent root =loader.load();
            Scene scene = new Scene(root);
           // scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("ajout blog ");
            primaryStage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
