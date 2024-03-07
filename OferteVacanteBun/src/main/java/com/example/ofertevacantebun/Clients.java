package com.example.ofertevacantebun;

import com.example.ofertevacantebun.domain.Hotel;
import com.example.ofertevacantebun.domain.Location;
import com.example.ofertevacantebun.domain.SpecialOffer;
import com.example.ofertevacantebun.service.HotelService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Clients {
    @FXML
    TableView<Hotel> hotelTable;
    @FXML
    TableView<Location> locationTable;
    @FXML
    TableView<SpecialOffer> dateTable;
    @FXML
    TableColumn<Hotel, String> hotelColumn;
    @FXML
    TableColumn<Location, String> locationColumn;
    @FXML
    TableColumn<SpecialOffer, LocalDate> startColumn;
    @FXML
    TableColumn<SpecialOffer, LocalDate> endColumn;

    HotelService srv;
    private Double current;
    ObservableList<Hotel> hotel = FXCollections.observableArrayList();
    ObservableList<Location> location = FXCollections.observableArrayList();
    ObservableList<SpecialOffer> specialOffer = FXCollections.observableArrayList();

    public void setService (HotelService srv, Double id){
        this.srv=srv;
        this.current=id;
        for(SpecialOffer s: srv.getOfferForClient(id)) {
            specialOffer.add(s);
            Hotel h=srv.findById(s.getHotelId());
            hotel.add(h);
            location.add(srv.findLocationById(h.getLocationId()));
        }
        initModel(hotel,location,specialOffer);
    }

    private void initModel(Iterable<Hotel> h, Iterable<Location> l, Iterable<SpecialOffer> s) {
        List<Hotel> hotels = StreamSupport.stream(h.spliterator(), false)
                .collect(Collectors.toList());
        hotel.setAll(hotels);
        List<Location> locations =StreamSupport.stream(l.spliterator(), false)
                .collect(Collectors.toList());
        location.setAll(locations);
        List<SpecialOffer> offers =StreamSupport.stream(s.spliterator(), false)
                .collect(Collectors.toList());
        specialOffer.setAll(offers);
    }

    @FXML
    public void initialize() {
        hotelColumn.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Location, String>("locationName"));
        startColumn.setCellValueFactory(new PropertyValueFactory<SpecialOffer, LocalDate>("startDate"));
        endColumn.setCellValueFactory(new PropertyValueFactory<SpecialOffer, LocalDate>("endDate"));
        hotelTable.setItems(hotel);
        locationTable.setItems(location);
        dateTable.setItems(specialOffer);
    }

    public void handleReservation(ActionEvent actionEvent) throws IOException {
        try{
            FXMLLoader userPage=new FXMLLoader(HelloApplication.class.getResource("Reservation.fxml"));
            Scene scene = new Scene(userPage.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            Reservations reservationController = userPage.getController();
            reservationController.setService(srv,current);
            stage.show();
           /// initModel();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
