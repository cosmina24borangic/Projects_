package com.example.ofertevacantebun.repository;

import com.example.ofertevacantebun.domain.Client;
import com.example.ofertevacantebun.domain.Location;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class LocationRepo implements Repo<Location>{
    private String url;
    private String username;

    private String password;

    public LocationRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Location> findAll() {
        Set<Location> locations = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from location");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Double locationId = resultSet.getDouble("location_id");
                String locationName = resultSet.getString("location_name");

                Location location = new Location(locationId,locationName);
                locations.add(location);
            }
            return locations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

   /* @Override
    public Location findOne(Double id) {
        if(id == null)
            throw new IllegalArgumentException("Id must not be null");

        String sql = "SELECT * FROM location where location.location_id = ?";
        Location location;

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDouble(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String locationName = resultSet.getString("location_name");

                location = new Location(id, locationName);
                return location;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }*/

    @Override
    public Location save (Location entity){return null;}
}
