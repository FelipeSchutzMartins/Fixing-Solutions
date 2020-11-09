package com.fixingsolutions.bean;

import com.fixingsolutions.domain.Conexao;
import com.fixingsolutions.domain.Cargo;
import com.fixingsolutions.repository.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CargoDao implements Dao<Cargo> {

    private Conexao conexao = new Conexao();

    @Override
    public Cargo get(int id) throws SQLException {
        String comando = "select * from cargo where id = ?";
        Cargo cargo = new Cargo();

        Connection dbConenection = conexao.abrirConexao();
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
        Connection dbConenection = conexao.abrirConexao();
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

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setString(1, cargo.getDescricao());

        ResultSet rs = preparedStatement.executeQuery();
        dbConenection.close();

    }

    @Override
    public void update(Cargo cargo) throws SQLException {
        String comando = "update cargo set descricao = ? where id = ?";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setString(1, cargo.getDescricao());

        ResultSet rs = preparedStatement.executeQuery();

        dbConenection.close();

    }

    @Override
    public void delete(Cargo cargo) throws SQLException{
        String comando = "delete from cargo where id = ?";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,cargo.getId());

        ResultSet rs = preparedStatement.executeQuery();
        dbConenection.close();


    }
}
