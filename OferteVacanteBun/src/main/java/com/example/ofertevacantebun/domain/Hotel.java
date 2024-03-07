package com.example.ofertevacantebun.domain;

import java.util.Objects;

public class Hotel {
    private Double hotelId;
    private Double locationId;
    private String hotelName;
    private Integer rooms;
    private Double price;
    private HotelType type;

    public Hotel(Double id, Double lid, String name, Integer rooms, Double price, HotelType type) {
        this.hotelId = id;
        this.locationId = lid;
        this.hotelName = name;
        this.rooms = rooms;
        this.price = price;
        this.type = type;
    }

    public Double getHotelId() {
        return hotelId;
    }

    public void setHotelId(Double id) {
        this.hotelId = id;
    }

    public Double getLocationId() {
        return locationId;
    }



    public String getHotelName() {
        return hotelName;
    }



    public Integer getRooms() {
        return rooms;
    }



    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public HotelType getType() {
        return type;
    }



    public static HotelType stringToType(String type){
        if(type.equals("FAMILY"))return HotelType.FAMILY;
        if(type.equals("TEENAGERS"))return HotelType.TEENAGERS;
        if(type.equals("OLDPEOPLE"))return HotelType.OLDPEOPLE;
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(hotelId, hotel.hotelId) && Objects.equals(locationId, hotel.locationId) && Objects.equals(hotelName, hotel.hotelName) && Objects.equals(rooms, hotel.rooms) && Objects.equals(price, hotel.price) && type == hotel.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, locationId, hotelName, rooms, price, type);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + hotelId +
                ", lid=" + locationId +
                ", name='" + hotelName + '\'' +
                ", rooms=" + rooms +
                ", price=" + price +
                ", type=" +  type +
                '}';
    }
}
