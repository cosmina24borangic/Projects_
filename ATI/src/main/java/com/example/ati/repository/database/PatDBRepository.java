package com.example.ati.repository.database;

import com.example.ati.domain.Pat;
import com.example.ati.domain.TipPat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatDBRepository extends AbstractDBRepository<Integer, Pat> {
    @Override
    protected PreparedStatement getSaveStatement(Connection connection, Pat entity) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getRemoveStatement(Connection connection, Integer integer) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Pat entity, Pat newEntity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE paturi SET pacient = ? WHERE id = ?");
        statement.setInt(1, newEntity.getPacient());
        statement.setInt(2, Math.toIntExact(entity.getId()));
        return statement;
    }

    @Override
    protected String getTable() {
        return "paturi";
    }

    @Override
    protected Pat createEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        TipPat tip = TipPat.valueOf(resultSet.getString("tip"));
        Boolean ventilatie = resultSet.getBoolean("ventilatie");
        Integer pacient = resultSet.getInt("pacient");
        Pat pat = new Pat(id, tip, ventilatie, pacient);
        pat.setId(id);
        return pat;
    }
}