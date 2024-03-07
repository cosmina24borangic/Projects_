package com.example.ati.repository.database;

import com.example.ati.PacientController;
import com.example.ati.domain.Pacient;
import com.example.ati.domain.Pat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PacientDBRepository extends AbstractDBRepository<Integer, Pacient> {
    @Override
    protected PreparedStatement getSaveStatement(Connection connection, Pacient entity) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getRemoveStatement(Connection connection, Integer integer) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Pacient entity, Pacient newEntity) throws SQLException {
        return null;
    }

    @Override
    protected String getTable() {
        return "pacienti";
    }

    @Override
    protected Pacient createEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        Integer varsta = resultSet.getInt("varsta");
        Boolean prematur = resultSet.getBoolean("prematur");
        String diagnostic = resultSet.getString("diagnostic");
        Integer gravitate = resultSet.getInt("gravitate");
        Pacient pacient = new Pacient(id, varsta, prematur, diagnostic, gravitate);
        pacient.setId(id);
        return pacient;
    }
}