package com.example.ofertevacantebun.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private double reservationId;
    private double clientId;
    private double hotelId;
    private LocalDate startDate;
    private int noNights;

    public Reservation(double reservationId, double clientId, double hotelId, LocalDate startDate, int noNights) {
        this.reservationId = reservationId;
        this.clientId = clientId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.noNights = noNights;
    }

    public double getReservationId() {
        return reservationId;
    }

    public void setReservationId(double reservationId) {
        this.reservationId = reservationId;
    }

    public double getClientId() {
        return clientId;
    }

    public void setClientId(double clientId) {
        this.clientId = clientId;
    }

    public double getHotelId() {
        return hotelId;
    }

    public void setHotelId(double hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getNoNights() {
        return noNights;
    }

    public void setNoNights(int noNights) {
        this.noNights = noNights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Double.compare(that.reservationId, reservationId) == 0 && Double.compare(that.clientId, clientId) == 0 && Double.compare(that.hotelId, hotelId) == 0 && noNights == that.noNights && Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, clientId, hotelId, startDate, noNights);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", clientId=" + clientId +
                ", hotelId=" + hotelId +
                ", startDate=" + startDate +
                ", noNights=" + noNights +
                '}';
    }
}
