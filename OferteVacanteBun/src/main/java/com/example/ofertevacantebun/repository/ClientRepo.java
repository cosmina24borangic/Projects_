package com.example.ofertevacantebun.repository;

import com.example.ofertevacantebun.domain.Client;
import com.example.ofertevacantebun.domain.Hobby;
import com.example.ofertevacantebun.domain.Location;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClientRepo implements Repo<Client>{
    private String url;
    private String username;

    private String password;

    public ClientRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Client> findAll() {
        Set<Client> clients = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from client");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Double clientId = resultSet.getDouble("client_id");
                String clientName = resultSet.getString("client_name");
                int fidelity= resultSet.getInt("fidelity");
                int age = resultSet.getInt("age");
                Hobby hobby = Client.stringToType(resultSet.getString("hobby"));
                Client client = new Client(clientId,clientName,fidelity,age,hobby);
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client save (Client entity){return null;}
}
