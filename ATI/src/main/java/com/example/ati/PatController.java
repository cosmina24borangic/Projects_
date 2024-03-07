package com.example.ati;

import com.example.ati.domain.Pat;
import com.example.ati.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class PatController {
    private final ObservableList<PatController> patModel = FXCollections.observableArrayList();
    public VBox patVB;
    Service service;

    public void initialize(Service service) {
        this.service = service;
        showPaturi();
    }

    private void showPaturi() {

        int totalOcupate = 0;
        Set<String> tipuri = new TreeSet<>();
        for (Pat pat : this.service.getAllPaturi()) {
            tipuri.add(String.valueOf(pat.getTip()));
            if(pat.getPacient() >= 1){
                totalOcupate++;
            }
        }
        patVB.getChildren().add(new Label("Paturi ocupate: " + totalOcupate));
        for (String tip : tipuri) {
            int ocupate = 0;
            int libere = 0;
            int categorie = 0;
            for (Pat pat : this.service.getAllPaturi()) {
                if (Objects.equals(tip, String.valueOf(pat.getTip())) && pat.getPacient() >= 1) {
                    ocupate++;
                }
                if (Objects.equals(tip, String.valueOf(pat.getTip()))) {
                    categorie++;
                }
            }
            patVB.getChildren().add(new Label(tip + " " + (categorie - ocupate) + " paturi libere"));
        }
    }
}