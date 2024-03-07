package com.example.ofertevacantebun;

import com.example.ofertevacantebun.domain.*;
import com.example.ofertevacantebun.repository.*;
import com.example.ofertevacantebun.service.HotelService;
import com.example.ofertevacantebun.service.LocationService;

public class Main {

    public static void main(String[] args){
        Repo<Location> repol = new LocationRepo("jdbc:postgresql://localhost:5432/oferte_vacante", "postgres", "1234");
        Repo<Hotel> repoh = new HotelRepo("jdbc:postgresql://localhost:5432/oferte_vacante", "postgres", "1234");
        Repo<SpecialOffer> reposo = new SpecialOfferRepo("jdbc:postgresql://localhost:5432/oferte_vacante", "postgres", "1234");
        Repo<Client> repoc = new ClientRepo("jdbc:postgresql://localhost:5432/oferte_vacante", "postgres", "1234");
        LocationService srvl = new LocationService(repol);
        Repo<Reservation> repor = new ReservationRepo("jdbc:postgresql://localhost:5432/oferte_vacante", "postgres", "1234");
        HotelService srvh= new HotelService(repol,repoh,reposo,repoc,repol,repor);
        srvl.getLocation();
        ///System.out.println(srvl.findByName("Bali"));
        ///Location loc=srvl.findByName("Bali");
        ///Double locid=loc.getLocationId();
        ///System.out.println(srvh.getHotels());
        ///System.out.println(srvh.findHotelByLocation(locid));
        System.out.println(srvh.getClients());
        //System.out.println(srvh.getOfferByDate('2023-02-02',"2023-02-05"));
    }
}
