package com.example.ati.domain;

public class Pat extends Entity<Integer>{
    TipPat tip;
    private Boolean ventilatie;
    private Integer pacient;

    public Pat(Integer id, TipPat tip, Boolean ventilatie, Integer pacient) {
        super(id);
        this.tip = tip;
        this.ventilatie = ventilatie;
        this.pacient = pacient;
    }

    public TipPat getTip() {
        return tip;
    }

    public void setTip(TipPat tip) {
        this.tip = tip;
    }

    public Boolean getVentilatie() {
        return ventilatie;
    }

    public void setVentilatie(Boolean ventilatie) {
        this.ventilatie = ventilatie;
    }

    public Integer getPacient() {
        return pacient;
    }

    public void setPacient(Integer pacient) {
        this.pacient = pacient;
    }

    @Override
    public String toString() {
        return "Pat{" +
                "tipPat=" + tip +
                ", vent=" + ventilatie +
                ", pacient=" + pacient +
                '}';
    }
}