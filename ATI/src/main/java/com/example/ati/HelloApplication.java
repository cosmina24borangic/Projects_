package com.example.ati;

import com.example.ati.domain.Pacient;
import com.example.ati.domain.Pat;
import com.example.ati.repository.Repository;
import com.example.ati.repository.database.PacientDBRepository;
import com.example.ati.repository.database.PatDBRepository;
import com.example.ati.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Repository<Integer, Pacient> pacientRepository = new PacientDBRepository();
        Repository<Integer, Pat> patRepository = new PatDBRepository();
        Service service = new Service(pacientRepository, patRepository);

        FXMLLoader paturiLoader = new FXMLLoader(HelloApplication.class.getResource("paturi-view.fxml"));
        Scene paturiScene = new Scene(paturiLoader.load(),600,400);
        PatController patController = paturiLoader.getController();
        patController.initialize(service);
        stage.setTitle("Paturi");
        stage.setScene(paturiScene);
        stage.show();

        FXMLLoader pacientiLoader = new FXMLLoader(HelloApplication.class.getResource("pacienti-view.fxml"));
        Stage pacientiStage = new Stage();
        Scene pacientiScene = new Scene(pacientiLoader.load(),600,400);
        PacientController pacientController = pacientiLoader.getController();
        pacientController.initialize(service);
        pacientiStage.setTitle("Pacienti");
        pacientiStage.setScene(pacientiScene);
        pacientiStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}