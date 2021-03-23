package com.fixingsolutions.bean;

import com.fixingsolutions.domain.*;
import com.fixingsolutions.repository.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CargoDao implements Dao<Cargo> {

    @Override
    public Cargo get(int id) throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM cargo\n");
        comando.append("WHERE id = ? \n");

        Cargo cargo = new Cargo();

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){

            cargo.setId(rs.getInt("id"));
            cargo.setDescricao(rs.getString("descricao"));

        }
        dbConenection.close();
        return cargo;
    }

    @Override
    public List<Cargo> getAll() throws SQLException{

        List<Cargo> cargos = new ArrayList<Cargo>();
        Cargo cargo;

        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM cargo\n");

        Connection dbConenection = connection.abrirConexao();
        Statement stmt = dbConenection.createStatement();
        ResultSet rs = stmt.executeQuery(comando.toString());

        while(rs.next()) {
            cargo = new Cargo();
            cargo.setId(rs.getInt("id"));
            cargo.setDescricao(rs.getString("descricao"));
            cargos.add(cargo);
        }

        dbConenection.close();

        return cargos;

    }

    @Override
    public void save(Cargo cargo) throws SQLException{

        StringBuilder comando = new StringBuilder();
        comando.append("INSERT INTO cargo(descricao) \n");
        comando.append("VALUES ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setString(1, cargo.getDescricao());

        executarUpdate(preparedStatement);
        dbConenection.close();

    }

    @Override
    public void update(Cargo cargo) throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("UPDATE cargo\n");
        comando.append("SET descricao = ?,\n");
        comando.append("WHERE id = ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setString(1, cargo.getDescricao());

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    @Override
    public void delete(Integer id) throws SQLException{

        StringBuilder comando = new StringBuilder();
        comando.append("DELETE\n");
        comando.append("FROM cargo\n");
        comando.append("WHERE id = ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,id);

        executarUpdate(preparedStatement);

        dbConenection.close();


    }
}
