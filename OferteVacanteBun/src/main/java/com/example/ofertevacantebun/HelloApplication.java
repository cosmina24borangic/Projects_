package com.example.ofertevacantebun;

import com.example.ofertevacantebun.domain.*;
import com.example.ofertevacantebun.repository.*;
import com.example.ofertevacantebun.service.HotelService;
import com.example.ofertevacantebun.service.LocationService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;

public class HelloApplication extends Application {

    Repo<Location> repol = new LocationRepo("jdbc:postgresql://localhost:5432/oferte_vacante", "postgres", "1234");
    Repo<Hotel> repoh = new HotelRepo("jdbc:postgresql://localhost:5432/oferte_vacante", "postgres", "1234");
    Repo<SpecialOffer> reposo = new SpecialOfferRepo("jdbc:postgresql://localhost:5432/oferte_vacante", "postgres", "1234");
    LocationService srvl = new LocationService(repol);
    Repo<Client> repoc = new ClientRepo("jdbc:postgresql://localhost:5432/oferte_vacante", "postgres", "1234");
    Repo<Reservation> repor = new ReservationRepo("jdbc:postgresql://localhost:5432/oferte_vacante", "postgres", "1234");
    HotelService srvh= new HotelService(repol,repoh,reposo,repoc,repol, repor);
   // public static void main(String[] args) {
   //     launch();
   // }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FindHotelByLocation.fxml"));
        Scene scene =new Scene(fxmlLoader.load());
        stage.setTitle("Hotels");
        stage.setScene(scene);
        FindHotelByLocation findController = fxmlLoader.getController();
        findController.setService(srvl,srvh);
        stage.show();
        stage.show();
        Parameters param = getParameters();
        List<String> list = param.getRaw();
        System.out.println(list.size());
        for(String clientId : list) {
            double id = Double.parseDouble(clientId);
            //System.out.println(id);
            getUsers(id);
        }
    }

    public void getUsers(Double clientId) {

        for (Client c : srvh.getClients()) {
            //System.out.println(c.getClientName());
            if (clientId== c.getClientId()) {
                try {
                    ///System.out.println(c.getClientName());
                    Stage stageClient = new Stage();
                    // System.out.println(c.getName());
                    FXMLLoader fxmlLoaderClient = new FXMLLoader(HelloApplication.class.getResource("Clients.fxml"));
                    Scene scene = new Scene(fxmlLoaderClient.load());
                    stageClient.setTitle(c.getClientName());
                    stageClient.setScene(scene);
                    Clients clientController = fxmlLoaderClient.getController();
                    clientController.setService(srvh, clientId);
                    stageClient.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}