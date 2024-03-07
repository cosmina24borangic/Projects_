package com.example.ofertevacantebun.domain;

import java.util.Objects;

public class Client {
    private double clientId;
    private String clientName;
    private int fidelityGrade;
    private int clientAge;
    private Hobby clientHobby;

    public Client(double clientId, String clientName, int fidelityGrade, int clientAge, Hobby clientHobby) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.fidelityGrade = fidelityGrade;
        this.clientAge = clientAge;
        this.clientHobby = clientHobby;
    }

    public double getClientId() {
        return clientId;
    }

    public void setClientId(double clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getFidelityGrade() {
        return fidelityGrade;
    }

    public void setFidelityGrade(int fidelityGrade) {
        this.fidelityGrade = fidelityGrade;
    }

    public int getClientAge() {
        return clientAge;
    }

    public void setClientAge(int clientAge) {
        this.clientAge = clientAge;
    }

    public Hobby getClientHobby() {
        return clientHobby;
    }

    public void setClientHobby(Hobby clientHobby) {
        this.clientHobby = clientHobby;
    }


    public static Hobby stringToType(String type){
        if(type.equals("READING"))return Hobby.READING;
        if(type.equals("HIKING"))return Hobby.HIKING;
        if(type.equals("WALKING"))return Hobby.WALKING;
        if(type.equals("MUSIC"))return Hobby.MUSIC;
        if(type.equals("EXTREME_SPORTS"))return Hobby.EXTREME_SPORTS;
        return null;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Double.compare(client.clientId, clientId) == 0 && fidelityGrade == client.fidelityGrade && clientAge == client.clientAge && Objects.equals(clientName, client.clientName) && clientHobby == client.clientHobby;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientName, fidelityGrade, clientAge, clientHobby);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", fidelityGrade=" + fidelityGrade +
                ", clientAge=" + clientAge +
                ", clientHobby=" + clientHobby +
                '}';
    }
}
