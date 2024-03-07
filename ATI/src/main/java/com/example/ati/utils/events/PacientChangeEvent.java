package com.example.ati.utils.events;

import com.example.ati.domain.Pacient;

public class PacientChangeEvent implements Event{
    private final ChangeEventType type;
    private final Pacient data;
    private Pacient oldData;

    public PacientChangeEvent(ChangeEventType type, Pacient data) {
        this.type = type;
        this.data = data;
    }

    public PacientChangeEvent(ChangeEventType type, Pacient data, Pacient oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Pacient getData() {
        return data;
    }

    public Pacient getOldData() {
        return oldData;
    }
}