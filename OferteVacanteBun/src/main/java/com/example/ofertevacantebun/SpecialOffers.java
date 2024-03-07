package com.example.ofertevacantebun;

import com.example.ofertevacantebun.domain.SpecialOffer;
import com.example.ofertevacantebun.service.HotelService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.util.Date;

public class SpecialOffers {
    @FXML
    DatePicker startDate;
    @FXML
    DatePicker endDate;
    @FXML
    ListView<SpecialOffer> offerList;

   HotelService srv;
    ObservableList<SpecialOffer> SpecialOffer = FXCollections.observableArrayList();

    public void setService (HotelService srv)
    {
        this.srv=srv;
        for(SpecialOffer l : srv.getOffers()){
            SpecialOffer.add(l);
        }
    }

    public void initModel(){
        offerList.setItems(SpecialOffer);
    }

    public void findOffers(ActionEvent actionEvent){
        if (endDate.getValue() == null || startDate.getValue() == null)
            return;
        LocalDate sd = startDate.getValue();
        LocalDate ed = endDate.getValue();
        SpecialOffer.clear();
        for(SpecialOffer so: srv.getOfferByDate(sd,ed))
        {
            SpecialOffer.add(so);
        }
        initModel();
    }


}
