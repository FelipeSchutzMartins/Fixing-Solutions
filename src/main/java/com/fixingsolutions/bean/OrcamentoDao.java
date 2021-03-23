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

public class OrcamentoDao implements Dao<Orcamento>{

    @Override
    public Orcamento get(int id) throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM orcamento orc \n");
        comando.append("JOIN funcionario fun ON fun.id = orc.idFuncionario \n");
        comando.append("JOIN cargo ON fun.idCargo = cargo.id \n");
        comando.append("JOIN cliente cli ON orc.idCliente = cli.id \n");
        comando.append("WHERE orc.id = ? \n");

        Orcamento orcamento = new Orcamento();

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){

            orcamento.setId(rs.getInt("orc.id"));
            orcamento.setData(rs.getDate("orc.data"));
            orcamento.setValor(rs.getBigDecimal("orc.valor"));
            orcamento.setHorasPrevistas(rs.getInt("orc.horasPrevistas"));

            Funcionario funcionario = new Funcionario();
            funcionario.setId(rs.getInt("fun.id"));
            funcionario.setEmail(rs.getString("fun.email"));
            funcionario.setSenha(rs.getString("fun.password"));
            funcionario.setNome(rs.getString("fun.nome"));

            Cargo cargo = new Cargo();
            cargo.setId(rs.getInt("cargo.id"));
            cargo.setDescricao(rs.getString("cargo.descricao"));

            funcionario.setCargo(cargo);

            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("cli.id"));
            cliente.setEmail(rs.getString("cli.email"));
            cliente.setCpf(rs.getString("cli.cpf"));
            cliente.setNome(rs.getString("cli.nome"));
            cliente.setTelefone(rs.getString("cli.telefone"));

            orcamento.setFuncionario(funcionario);
            orcamento.setCliente(cliente);

        }

        dbConenection.close();

        return orcamento;
    }

    @Override
    public List<Orcamento> getAll() throws SQLException {

        List<Orcamento> orcamentos = new ArrayList<Orcamento>();
        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM orcamento orc \n");
        comando.append("JOIN funcionario fun ON fun.id = orc.idFuncionario \n");
        comando.append("JOIN cargo ON fun.idCargo = cargo.id \n");
        comando.append("JOIN cliente cli ON orc.idCliente = cli.id \n");

        Connection dbConenection = connection.abrirConexao();
        Statement stmt = dbConenection.createStatement();
        ResultSet rs = stmt.executeQuery(comando.toString());

        while(rs.next()) {
            Orcamento orcamento = new Orcamento();
            orcamento.setId(rs.getInt("orc.id"));
            orcamento.setData(rs.getDate("orc.data"));
            orcamento.setValor(rs.getBigDecimal("orc.valor"));
            orcamento.setHorasPrevistas(rs.getInt("orc.horasPrevistas"));

            Funcionario funcionario = new Funcionario();
            funcionario.setId(rs.getInt("fun.id"));
            funcionario.setEmail(rs.getString("fun.email"));
            funcionario.setSenha(rs.getString("fun.password"));
            funcionario.setNome(rs.getString("fun.nome"));

            Cargo cargo = new Cargo();
            cargo.setId(rs.getInt("cargo.id"));
            cargo.setDescricao(rs.getString("cargo.descricao"));

            funcionario.setCargo(cargo);

            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("cli.id"));
            cliente.setEmail(rs.getString("cli.email"));
            cliente.setCpf(rs.getString("cli.cpf"));
            cliente.setNome(rs.getString("cli.nome"));
            cliente.setTelefone(rs.getString("cli.telefone"));

            orcamento.setFuncionario(funcionario);
            orcamento.setCliente(cliente);

            orcamentos.add(orcamento);

        }

        dbConenection.close();

        return orcamentos;

    }

    @Override
    public void save(Orcamento orcamento) throws SQLException{

        StringBuilder comando = new StringBuilder();
        comando.append("INSERT INTO orcamento(data,valor,horasPrevistas,idFuncionario,idCliente) \n");
        comando.append("VALUES(?,?,?,?,?)");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setDate(1,new java.sql.Date(orcamento.getData().getTime()));
        preparedStatement.setBigDecimal(2,orcamento.getValor());
        preparedStatement.setInt(3,orcamento.getHorasPrevistas());
        preparedStatement.setInt(4,orcamento.getFuncionario().getId());
        preparedStatement.setInt(5,orcamento.getCliente().getId());

        executarUpdate(preparedStatement);

        if(orcamento.getServicos() != null){

            ServicoDao servicoDao = new ServicoDao();

            for(Object ob : orcamento.getServicos()){

                Servico servico = (Servico) ob;

                if(servico.getId() != null){

                    servicoDao.update(servico);

                }else{

                    servicoDao.save(servico,findIdByObject(orcamento));

                }

            }

        }

        dbConenection.close();

    }

    @Override
    public void update(Orcamento orcamento) throws SQLException{

        StringBuilder comando = new StringBuilder();
        comando.append("UPDATE orcamento\n");
        comando.append("SET valor  = ?,\n");
        comando.append("    horasPrevistas = ?,\n");
        comando.append("    idFuncionario = ?,\n");
        comando.append("    idCliente = ?\n");
        comando.append("WHERE id = ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setBigDecimal(1,orcamento.getValor());
        preparedStatement.setInt(2,orcamento.getHorasPrevistas());
        preparedStatement.setInt(3,orcamento.getFuncionario().getId());
        preparedStatement.setInt(4,orcamento.getCliente().getId());
        preparedStatement.setInt(5,orcamento.getId());

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    @Override
    public void delete(Integer id) throws SQLException{

        StringBuilder comando = new StringBuilder();
        comando.append("DELETE\n");
        comando.append("FROM orcamento\n");
        comando.append("WHERE id = ?");

        deleteFromMm(id);

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,id);

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    public Integer findIdByObject(Orcamento orcamento) throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM orcamento \n");
        comando.append("WHERE data = ? \n");
        comando.append("AND valor = ? \n");
        comando.append("AND horasPrevistas = ? \n");
        comando.append("AND idFuncionario = ? \n");
        comando.append("AND idCliente = ? \n");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setDate(1, new java.sql.Date(orcamento.getData().getTime()));
        preparedStatement.setBigDecimal(2, orcamento.getValor());
        preparedStatement.setInt(3, orcamento.getHorasPrevistas());
        preparedStatement.setInt(4, orcamento.getFuncionario().getId());
        preparedStatement.setInt(5, orcamento.getCliente().getId());
        ResultSet rs = preparedStatement.executeQuery();

        Integer id = null;

        if(rs.next()){

            id = rs.getInt("id");

        }

        dbConenection.close();

        return id;

    }

    public void deleteFromMm(Integer id) throws SQLException{

        StringBuilder comando = new StringBuilder();
        comando.append("DELETE\n");
        comando.append("FROM tiposervico_orcamento\n");
        comando.append("WHERE idOrcamento = ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,id);

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

}
