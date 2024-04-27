package tn.esprit;

import tn.esprit.controllers.WeatherForecast;
import tn.esprit.models.Activite;
import tn.esprit.models.Voyage;
import tn.esprit.services.ServiceActivite;
import tn.esprit.services.ServiceVoyage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ServiceVoyage.ItemNotFoundException, ServiceActivite.ItemNotFoundException {

        System.out.println(WeatherForecast.getLocationData("Tokyo"));

    }
}