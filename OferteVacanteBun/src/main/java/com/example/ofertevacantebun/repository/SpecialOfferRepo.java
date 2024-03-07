package com.example.ofertevacantebun.repository;

import com.example.ofertevacantebun.SpecialOffers;
import com.example.ofertevacantebun.domain.Client;
import com.example.ofertevacantebun.domain.Hotel;
import com.example.ofertevacantebun.domain.HotelType;
import com.example.ofertevacantebun.domain.SpecialOffer;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SpecialOfferRepo implements Repo<SpecialOffer>{
    private String url;
    private String username;

    private String password;

    public SpecialOfferRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<SpecialOffer> findAll() {
        Set<SpecialOffer> SpecialOffers = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from special_offer");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                double soId = resultSet.getDouble("special_offer_id");
                double hId = resultSet.getDouble("hotel_id");
                LocalDate sd = resultSet.getDate("start_date").toLocalDate();
                LocalDate ed = resultSet.getDate("end_date").toLocalDate();
                int price = resultSet.getInt("percent");


                SpecialOffer sp = new SpecialOffer(soId,hId,sd,ed,price);
                SpecialOffers.add(sp);
            }
            return SpecialOffers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SpecialOffers;
    }

    @Override
    public SpecialOffer save (SpecialOffer entity){return null;}
}
