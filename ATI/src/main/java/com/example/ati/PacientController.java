package com.example.ati;

import com.example.ati.domain.Pacient;
import com.example.ati.domain.exceptions.EmptyStringException;
import com.example.ati.domain.exceptions.NegativeNumberException;
import com.example.ati.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PacientController {
    private final ObservableList<Pacient> pacientModel = FXCollections.observableArrayList();
    @FXML
    private TableView<Pacient> pacientiTableView;
    @FXML
    private TableColumn<Pacient, Integer> idColumn;
    @FXML
    private TableColumn<Pacient, String> diagnosticColumn;
    @FXML
    private Button ticButton;
    @FXML
    private Button tiipButton;
    @FXML
    private Button timButton;
    Service service;

    public void initialize(Service service) {
        this.service = service;
        initModel();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        diagnosticColumn.setCellValueFactory(new PropertyValueFactory<>("diagnostic"));
        pacientiTableView.setItems(pacientModel);
    }

    private void initModel() {
        Iterable<Pacient> pacients = service.sortPacientAsteptareByGravitate();
        List<Pacient> pacientsList = StreamSupport.stream(pacients.spliterator(), false).collect(Collectors.toList());
        pacientModel.setAll(pacientsList);
    }

    public void onTicSelect(ActionEvent actionEvent) {
        Integer pacientID = pacientiTableView.getSelectionModel().getSelectedItem().getId();
        try {
            this.service.assignPatToPacient(pacientID,"TIC");
        } catch (EmptyStringException | NegativeNumberException e) {
            throw new RuntimeException(e);
        }
        initialize(service);
    }

    public void onTiipSelect(ActionEvent actionEvent) {
        Integer pacientID = pacientiTableView.getSelectionModel().getSelectedItem().getId();
        try {
            this.service.assignPatToPacient(pacientID,"TIIP");
        } catch (EmptyStringException | NegativeNumberException e) {
            throw new RuntimeException(e);
        }
        initialize(service);
    }

    public void onTimSelect(ActionEvent actionEvent) {
        Integer pacientID = pacientiTableView.getSelectionModel().getSelectedItem().getId();
        try {
            this.service.assignPatToPacient(pacientID,"TIM");
        } catch (EmptyStringException | NegativeNumberException e) {
            throw new RuntimeException(e);
        }
        initialize(service);
    }
}