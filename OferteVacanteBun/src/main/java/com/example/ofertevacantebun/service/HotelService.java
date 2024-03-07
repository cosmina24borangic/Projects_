package com.example.ofertevacantebun.service;

import com.example.ofertevacantebun.domain.*;
import com.example.ofertevacantebun.repository.Repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HotelService {

    private Repo<Location> locationRepo;
    private Repo<Hotel> hotelRepo;

    private Repo<SpecialOffer> sorepo;
    private Repo<Client> crepo;
    private Repo<Location> lrepo;
    private Repo<Reservation> rrepo;
    private Hotel currenthotel;

    public HotelService(Repo<Location> locationRepo, Repo<Hotel> hotelRepo,Repo<SpecialOffer> sorepo, Repo<Client> crepo, Repo<Location> lrepo,Repo<Reservation> rrepo) {
        this.locationRepo = locationRepo;
        this.hotelRepo = hotelRepo;
        this.sorepo=sorepo;
        this.crepo=crepo;
        this.lrepo=lrepo;
        this.rrepo=rrepo;
    }

    public Location findLocationById(Double id){
        Iterable<Location> locations=lrepo.findAll();
        for(Location l: locations)
            if(Objects.equals(l.getLocationId(), id))
                return l;
        return null;
    }

    public Iterable<Hotel> findHotelByLocation (Double location){
        Set<Hotel> hotels = new HashSet<>();
        Iterable<Hotel> allHotels = hotelRepo.findAll();
        for (Hotel hotel : allHotels)
        {
            if (Objects.equals(hotel.getLocationId(), location))
                hotels.add(hotel);
        }
        return hotels;
    }

    public Hotel findById(Double id){
        Iterable<Hotel> allHotels = hotelRepo.findAll();
        for(Hotel h: allHotels)
            if(Objects.equals(h.getHotelId(), id))
                return h;
        return null;
    }

    public void setHotel (Hotel hotel){this.currenthotel=hotel;}

    public Iterable<Hotel> getHotels()
    {
        return hotelRepo.findAll();
    }

    public Iterable<SpecialOffer> getOfferByDate(LocalDate startDate, LocalDate endDate){
        System.out.println(startDate);
        System.out.println(endDate);
        Iterable<SpecialOffer> all = sorepo.findAll();
       // System.out.println(all);
        Set<SpecialOffer> bun= new HashSet<>();
        for (SpecialOffer so: all)
        {
            //System.out.println(so);

            if (so.getHotelId()==currenthotel.getHotelId() && so.getStartDate().isAfter(startDate) && so.getEndDate().isBefore(endDate)) {
                bun.add(so);
               System.out.println(so);
            }
        }
        return bun;
    }
    public Iterable<SpecialOffer> getOffers()
    {
        return sorepo.findAll();
    }

    public Iterable<Client> getClients()
    {
        return crepo.findAll();
    }

    public Client getClientById (Double id){
        Iterable<Client> clients = crepo.findAll();
        for(Client client :clients)
            if(client.getClientId()==id)
                return client;
        return null;
    }



    public Iterable<SpecialOffer> getOfferForClient (Double id)
    {
        Client client = getClientById(id);
        Iterable<SpecialOffer> offers = sorepo.findAll();
        Set<SpecialOffer> forClient = new HashSet<>();
        for( SpecialOffer so: offers)
        {
            if(client.getFidelityGrade() > so.getPercent())
                forClient.add(so);
        }
        return forClient;
    }

    public double calculateIdForReservation()
    {
        Double id=0.0;
        Iterable<Reservation> res=rrepo.findAll();
        for(Reservation r: res)
            if(r.getReservationId()>id)
                id=r.getReservationId();
        id++;
        return id;
    }
    public void saveReservation(Double client, Double hotel, LocalDate date, Integer noNights)
    {
        Double id=calculateIdForReservation();
        Reservation res = new Reservation(id,client,hotel,date,noNights);
        rrepo.save(res);
    }
}
