package tn.esprit.controler;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/ChatBot.fxml"));
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
    //public static String chatGPT(String message) {
//String url="https://api.openai.com/v1/chat/completions";
//String apikey="";
  //String model = "gpt-3.5-turbo";
 //  return ;}
}
