package com.example.ofertevacantebun;

import com.example.ofertevacantebun.domain.Hotel;
import com.example.ofertevacantebun.domain.Location;
import com.example.ofertevacantebun.domain.SpecialOffer;
import com.example.ofertevacantebun.service.HotelService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Reservations {
    @FXML
    TableView<Hotel> hotelTable;
    @FXML
    TableColumn<Hotel, String> hotelColumn;
    @FXML
    DatePicker startDate;
    @FXML
    TextField noNightsField;
    @FXML
    private Label messageLabel;

    HotelService srv;

    private Double current;

    ObservableList<Hotel> hotel = FXCollections.observableArrayList();

    public void setService(HotelService srv, Double id)
    {
        this.srv=srv;
        this.current=id;
        for(Hotel h: srv.getHotels())
            hotel.add(h);
        initModel(hotel);
    }

    private void initModel(Iterable<Hotel> h) {
        List<Hotel> hotels = StreamSupport.stream(h.spliterator(), false)
                .collect(Collectors.toList());
        hotel.setAll(hotels);
        messageLabel.setText("");
    }

    @FXML
    public void initialize() {
        hotelColumn.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelName"));
        hotelTable.setItems(hotel);
    }

    public void handleAdd(ActionEvent actionEvent) {
        try{
//            messageLabel.setText(srvu.findOne(14L).toString());
            ///Long id1= srvu.getIdCurent();
            Hotel selected= hotelTable.getSelectionModel().getSelectedItem();
            ///Long id2= selected.getID();
            LocalDate date= startDate.getValue();
            if(date.isAfter(LocalDate.now())) {
                int nights = Integer.parseInt(noNightsField.getText());
                srv.saveReservation(current, selected.getHotelId(), date, nights);
                messageLabel.setText("Succesful reservation");
            }
            else messageLabel.setText("Unsuccesful reservation");
            //initModel(hotel);
        }catch (Exception e){
            messageLabel.setText("Unsuccesful reservation");
        }
    }
}
