package com.example.ofertevacantebun.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class SpecialOffer {

    private double specialOfferId;
    private double hotelId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int percent;

    public SpecialOffer(double specialOfferId, double hotelId, LocalDate startDate, LocalDate endDate, int percent) {
        this.specialOfferId = specialOfferId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
    }

    public double getSpecialOfferId() {
        return specialOfferId;
    }

    public void setSpecialOfferId(double specialOfferId) {
        this.specialOfferId = specialOfferId;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialOffer that = (SpecialOffer) o;
        return Double.compare(that.specialOfferId, specialOfferId) == 0 && Double.compare(that.hotelId, hotelId) == 0 && percent == that.percent && startDate.equals(that.startDate) && endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialOfferId, hotelId, startDate, endDate, percent);
    }

    @Override
    public String toString() {
        return "SpecialOffer{" +
                "specialOfferId=" + specialOfferId +
                ", hotelId=" + hotelId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", percent=" + percent +
                '}';
    }
}
