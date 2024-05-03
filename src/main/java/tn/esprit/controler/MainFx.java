package tn.esprit.controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


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
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/BlogList.fxml"));
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
