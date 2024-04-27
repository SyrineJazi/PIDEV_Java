module PIDEV.voyage {

    requires javafx.fxml;
    requires javafx.controls;
    requires okhttp3;
    requires com.google.gson;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires json.simple;

    opens tn.esprit.controllers;
}