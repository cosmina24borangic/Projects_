package com.example.ofertevacantebun.utils;

import com.example.ofertevacantebun.domain.Hotel;

public class HotelChangeEvent implements Events{
    private ChangeEventType type;
    private Hotel oldData, data;

    public HotelChangeEvent(ChangeEventType type, Hotel data) {
        this.type = type;
        this.data = data;
    }

    public HotelChangeEvent(ChangeEventType type, Hotel oldData, Hotel data) {
        this.type = type;
        this.oldData = oldData;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Hotel getOldData() {
        return oldData;
    }

    public Hotel getData() {
        return data;
    }
}
