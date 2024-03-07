package com.example.ofertevacantebun.service;

import com.example.ofertevacantebun.domain.Location;
import com.example.ofertevacantebun.repository.Repo;

import java.util.Objects;

public class LocationService {

    private Repo<Location> locationRepo;

    public LocationService(Repo<Location> locationRepo) {
        this.locationRepo = locationRepo;
    }

    public Iterable<Location> printLocation() {
        return locationRepo.findAll();
    }

   /* public Location findById( double id){
        if(locationRepo.findOne(id) != null)
            return locationRepo.findOne(id);
        else
            throw new RuntimeException("id invalid");
    }*/

    public Location findByName (String Name){
        Iterable<Location> locations = locationRepo.findAll();
        for (Location location : locations)
            if(Objects.equals(location.getLocationName(), Name))
                return location;
        return null;
    }


    public Iterable<Location> getLocation(){
        return locationRepo.findAll();
    }

}
