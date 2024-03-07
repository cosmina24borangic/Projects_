package com.example.ati.domain;

public class Pacient extends Entity<Integer>{
    private Integer varsta;
    private Boolean prematur;
    private String diagnostic;
    private Integer gravitate;

    public Pacient(Integer integer, Integer varsta, Boolean prematur, String diagnostic, Integer gravitate) {
        super(integer);
        this.varsta = varsta;
        this.prematur = prematur;
        this.diagnostic = diagnostic;
        this.gravitate = gravitate;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }

    public Boolean getPrematur() {
        return prematur;
    }

    public void setPrematur(Boolean prematur) {
        this.prematur = prematur;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public Integer getGravitate() {
        return gravitate;
    }

    public void setGravitate(Integer gravitate) {
        this.gravitate = gravitate;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "varsta=" + varsta +
                ", prematur=" + prematur +
                ", diagnostic='" + diagnostic + '\'' +
                ", gravitate=" + gravitate +
                '}';
    }
}