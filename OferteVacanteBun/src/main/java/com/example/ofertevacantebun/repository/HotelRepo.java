package com.example.ofertevacantebun.repository;

import com.example.ofertevacantebun.domain.Client;
import com.example.ofertevacantebun.domain.Hotel;
import com.example.ofertevacantebun.domain.HotelType;
import com.example.ofertevacantebun.domain.Location;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class HotelRepo implements Repo<Hotel>{

    private String url;
    private String username;

    private String password;

    public HotelRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Hotel> findAll() {
        Set<Hotel> hotels = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from hotel");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                double hotelId = resultSet.getDouble("hotel_id");
                double locationId = resultSet.getDouble("location_id");
                String hotelName = resultSet.getString("hotel_name");
                int rooms = resultSet.getInt("rooms");
                double price = resultSet.getDouble("price");
                HotelType type= Hotel.stringToType(resultSet.getString("type"));

                Hotel hotel = new Hotel(hotelId,locationId,hotelName,rooms,price,type);
                hotels.add(hotel);
            }
            return hotels;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }
    @Override
    public Hotel save (Hotel entity){return null;}
}
