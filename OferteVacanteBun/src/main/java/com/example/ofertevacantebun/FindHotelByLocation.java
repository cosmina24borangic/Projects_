package com.example.ofertevacantebun;

import com.example.ofertevacantebun.domain.Hotel;
import com.example.ofertevacantebun.domain.Location;
import com.example.ofertevacantebun.domain.SpecialOffer;
import com.example.ofertevacantebun.service.HotelService;
import com.example.ofertevacantebun.service.LocationService;
import com.example.ofertevacantebun.utils.HotelChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FindHotelByLocation {
    @FXML
    ComboBox<String> locationComboBox;
    @FXML
    TableView<Hotel> hotelTable;
    @FXML
    TableColumn<Hotel,String> nameColumn;
    @FXML
    TableColumn<Hotel,Integer> roomsColumn;
    @FXML
    TableColumn<Hotel,Double> priceColumn;
    @FXML
    TableColumn<Hotel,String> typeColumn;
    LocationService locationSrv;
    HotelService hotelSrv;

    ObservableList<Hotel> hotel = FXCollections.observableArrayList();
    ObservableList<String> location = FXCollections.observableArrayList();

    public void setService (LocationService locationSrv, HotelService hotelSrv)
    {
        this.locationSrv=locationSrv;
        this.hotelSrv=hotelSrv;
        locationComboBox.setItems(location);
       // hotelTable.setItems(hotel);
        for(Location l : locationSrv.getLocation()){
            location.add(l.getLocationName());
        }
    }

    public void selected(ActionEvent event) {
        if(locationComboBox.getSelectionModel().getSelectedItem()==null)
            return;
        String location = locationComboBox.getSelectionModel().getSelectedItem();
        Location loc = locationSrv.findByName(location);
        Double lid = loc.getLocationId();
        hotel.clear();
        /*Iterable<Hotel> h= hotelSrv.findHotelByLocation(lid);
        for (Hotel hot : h)
        {
            hotel.add(hot);
        }*/
        initModel(hotelSrv.findHotelByLocation(lid));
    }



    private void initModel(Iterable<Hotel> h) {
        List<Hotel> hotels = StreamSupport.stream(h.spliterator(), false)
                .collect(Collectors.toList());
        hotel.setAll(hotels);
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelName"));
        roomsColumn.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("rooms"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Hotel, Double>("price"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Hotel, String>("type"));
        hotelTable.setItems(hotel);
        hotelTable.setRowFactory(tv -> {
            TableRow row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                try {
                    Hotel selected = hotelTable.getSelectionModel().getSelectedItem();
                    hotelSrv.setHotel(selected);
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SpecialOffer.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setTitle("Oferte");
                    stage.setScene(scene);
                    SpecialOffers oferteController = fxmlLoader.getController();
                    oferteController.setService(hotelSrv);
                    stage.show();
                }
                catch (Exception e){e.printStackTrace();}
            });
            return row;});

        }
    }





