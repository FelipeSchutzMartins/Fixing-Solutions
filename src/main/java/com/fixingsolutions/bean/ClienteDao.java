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

public class ClienteDao implements Dao<Cliente> {
    @Override
    public Cliente get(int id) throws SQLException {
        String comando = "select * from Cliente where id = ?";
        Cliente cliente = new Cliente();

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){

            cliente.setId(rs.getInt("id"));
            cliente.setEmail(rs.getString("email"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setNome(rs.getString("nome"));
            cliente.setTelefone(rs.getString("telefone"));

        }

        dbConenection.close();

        return cliente;
    }

    @Override
    public List<Cliente> getAll() throws SQLException {

        List<Cliente> clientes = new ArrayList<Cliente>();
        Cliente cliente = null;
        String comando = "select * from cliente";

        java.sql.Connection dbConenection = connection.abrirConexao();
        Statement stmt = dbConenection.createStatement();
        ResultSet rs = stmt.executeQuery(comando);

        while(rs.next()) {
            cliente = new Cliente();
            cliente.setId(rs.getInt("id"));
            cliente.setEmail(rs.getString("email"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setNome(rs.getString("nome"));
            cliente.setTelefone(rs.getString("telefone"));
            clientes.add(cliente);
        }

        dbConenection.close();

        return clientes;

    }

    @Override
    public void save(Cliente cliente) throws SQLException{

        String comando = "insert into cliente(nome,email,cpf,telefone) values (?,?,?,?)";
        java.sql.Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setString(1,cliente.getNome());
        preparedStatement.setString(2,cliente.getEmail());
        preparedStatement.setString(3,cliente.getCpf());
        preparedStatement.setString(4,cliente.getTelefone());

        int rs = preparedStatement.executeUpdate();

        dbConenection.close();

    }

    @Override
    public void update(Cliente cliente) throws SQLException{
        String comando = "update cliente set nome = ?,email = ?,cpf = ?, telefone = ?  where id = ?";

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setString(1,cliente.getNome());
        preparedStatement.setString(2,cliente.getEmail());
        preparedStatement.setString(3,cliente.getCpf());
        preparedStatement.setString(4,cliente.getTelefone());
        preparedStatement.setInt(5,cliente.getId());

        Integer rs = preparedStatement.executeUpdate();

        dbConenection.close();

    }

    @Override
    public void delete(Integer id) throws SQLException{
        String comando = "delete from cliente where id = ?";

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);

        int rs = preparedStatement.executeUpdate();

        dbConenection.close();

    }

}
