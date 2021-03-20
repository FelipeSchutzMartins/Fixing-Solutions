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

    private Connection connection = new Connection();

    @Override
    public Cargo get(int id) throws SQLException {
        String comando = "select * from cargo where id = ?";
        Cargo cargo = new Cargo();

        java.sql.Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
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
        Cargo cargo = null;
        String comando = "select * from cargo";
        java.sql.Connection dbConenection = connection.abrirConexao();
        Statement stmt = dbConenection.createStatement();
        ResultSet rs = stmt.executeQuery(comando);

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
        String comando = "insert into cargo(descricao) values ?";

        java.sql.Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setString(1, cargo.getDescricao());

        ResultSet rs = preparedStatement.executeQuery();
        dbConenection.close();

    }

    @Override
    public void update(Cargo cargo) throws SQLException {
        String comando = "update cargo set descricao = ? where id = ?";

        java.sql.Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setString(1, cargo.getDescricao());

        ResultSet rs = preparedStatement.executeQuery();

        dbConenection.close();

    }

    @Override
    public void delete(Integer id) throws SQLException{
        String comando = "delete from cargo where id = ?";

        java.sql.Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);

        ResultSet rs = preparedStatement.executeQuery();
        dbConenection.close();


    }
}
