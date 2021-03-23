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

        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM Cliente\n");
        comando.append("WHERE id = ? \n");

        Cliente cliente = new Cliente();

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
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
        Cliente cliente;

        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM Cliente\n");

        Connection dbConenection = connection.abrirConexao();
        Statement stmt = dbConenection.createStatement();
        ResultSet rs = stmt.executeQuery(comando.toString());

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

        StringBuilder comando = new StringBuilder();
        comando.append("INSERT INTO cliente(nome,email,cpf,telefone)\n");
        comando.append("VALUES(?,?,?,?)");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setString(1,cliente.getNome());
        preparedStatement.setString(2,cliente.getEmail());
        preparedStatement.setString(3,cliente.getCpf());
        preparedStatement.setString(4,cliente.getTelefone());

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    @Override
    public void update(Cliente cliente) throws SQLException{

        StringBuilder comando = new StringBuilder();
        comando.append("UPDATE cliente\n");
        comando.append("SET nome = ?,\n");
        comando.append("    email = ?,\n");
        comando.append("    cpf = ?,\n");
        comando.append("    telefone = ?\n");
        comando.append("WHERE id = ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setString(1,cliente.getNome());
        preparedStatement.setString(2,cliente.getEmail());
        preparedStatement.setString(3,cliente.getCpf());
        preparedStatement.setString(4,cliente.getTelefone());
        preparedStatement.setInt(5,cliente.getId());

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    @Override
    public void delete(Integer id) throws SQLException{

        StringBuilder comando = new StringBuilder();
        comando.append("DELETE\n");
        comando.append("FROM cliente\n");
        comando.append("WHERE id = ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,id);

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

}
