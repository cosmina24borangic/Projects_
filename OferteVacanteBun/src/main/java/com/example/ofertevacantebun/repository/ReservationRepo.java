package com.example.ofertevacantebun.repository;

import com.example.ofertevacantebun.domain.Client;
import com.example.ofertevacantebun.domain.Hobby;
import com.example.ofertevacantebun.domain.Location;
import com.example.ofertevacantebun.domain.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ReservationRepo implements Repo<Reservation>{
    private String url;
    private String username;

    private String password;

    public ReservationRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Reservation> findAll() {
        Set<Reservation> reservations = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from reservation");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Double reservationId = resultSet.getDouble("reservation_id");
                Double clientId = resultSet.getDouble("client_id");
                Double hotelId= resultSet.getDouble("hotel_id");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                int noNights = resultSet.getInt("no_nights");
               Reservation res = new Reservation(reservationId,clientId,hotelId,startDate,noNights);
               reservations.add(res);
            }
            return reservations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public Reservation save(Reservation entity) {
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null");

        String sql = "insert into reservation (reservation_id,client_id,hotel_id,start_date,no_nights) values (?, ?, ?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1,entity.getReservationId());
            ps.setDouble(2, entity.getClientId());
            ps.setDouble(3, entity.getHotelId());
            ps.setDate(4, java.sql.Date.valueOf(entity.getStartDate()));
            ps.setInt(5, entity.getNoNights());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
