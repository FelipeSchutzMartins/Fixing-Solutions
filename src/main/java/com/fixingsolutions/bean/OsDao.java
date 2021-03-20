package com.fixingsolutions.bean;

import com.fixingsolutions.domain.*;
import com.fixingsolutions.repository.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OsDao implements Dao<Os> {

    @Override
    public Os get(int id) throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM ordemServico os \n");
        comando.append("JOIN orcamento orc ON os.idOrcamento = orc.id \n");
        comando.append("JOIN funcionario fun ON fun.id = orc.idFuncionario \n");
        comando.append("JOIN cargo ON fun.idCargo = cargo.id \n");
        comando.append("JOIN cliente cli ON orc.idCliente = cli.id \n");
        comando.append("WHERE orc.id = ? \n");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();

        Os ordemServico = new Os();
        if(rs.next()){

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

            ordemServico.setOrcamento(orcamento);
            ordemServico.setId(rs.getInt("os.id"));
            ordemServico.setStatus(rs.getInt("os.status"));
            ordemServico.setDataCriacao(rs.getDate("os.dataInicio"));
            ordemServico.setTitulo(rs.getString("os.titulo"));
            ordemServico.setDataUltimaAtualizacao(rs.getDate("os.dataUltimaAtualizacao"));

        }

        dbConenection.close();

        return ordemServico;
    }

    @Override
    public List<Os> getAll() throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM ordemServico os\n");
        comando.append("JOIN orcamento orc ON os.idOrcamento = orc.id\n");
        comando.append("JOIN funcionario fun ON fun.id = orc.idFuncionario\n");
        comando.append("JOIN cargo ON fun.idCargo = cargo.id\n");
        comando.append("JOIN cliente cli ON orc.idCliente = cli.id\n");

        Connection dbConenection = connection.abrirConexao();
        Statement stmt = dbConenection.createStatement();
        ResultSet rs = stmt.executeQuery(comando.toString());

        List<Os> listaOs = new ArrayList<Os>();
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

            Os ordemServico = new Os();
            ordemServico.setOrcamento(orcamento);
            ordemServico.setId(rs.getInt("os.id"));
            ordemServico.setStatus(rs.getInt("os.status"));
            ordemServico.setDataCriacao(rs.getDate("os.dataInicio"));
            ordemServico.setTitulo(rs.getString("os.titulo"));
            ordemServico.setDataUltimaAtualizacao(rs.getDate("os.dataUltimaAtualizacao"));

            listaOs.add(ordemServico);

        }

        dbConenection.close();

        return listaOs;
    }

    @Override
    public void save(Os os) throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("INSERT INTO ordemServico(status, titulo, dataInicio, dataUltimaAtualizacao, idOrcamento) \n");
        comando.append("VALUES(?,?,?,?,?)");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,os.getStatus());
        preparedStatement.setString(2,os.getTitulo());
        preparedStatement.setDate(3,new Date(os.getDataCriacao().getTime()));
        preparedStatement.setDate(4,new Date(os.getDataUltimaAtualizacao().getTime()));
        preparedStatement.setInt(5,os.getOrcamento().getId());

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    @Override
    public void update(Os os) throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("UPDATE ordemServico\n");
        comando.append("SET idOrcamento = ?,\n");
        comando.append("    status = ?,\n");
        comando.append("    titulo = ?,\n");
        comando.append("    dataInicio = ?,\n");
        comando.append("    dataUltimaAtualizacao = ?\n");
        comando.append("WHERE id = ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,os.getOrcamento().getId());
        preparedStatement.setInt(2,os.getStatus());
        preparedStatement.setString(3,os.getTitulo());
        preparedStatement.setDate(4, new Date(os.getDataCriacao().getTime()));
        preparedStatement.setDate(5,new Date(os.getDataUltimaAtualizacao().getTime()));
        preparedStatement.setInt(6,os.getId());

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    @Override
    public void delete(Integer id) throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("DELETE\n");
        comando.append("FROM ordemservico\n");
        comando.append("WHERE id = ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,id);

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    public static List<Os> findWhereDateCriacaoBetween(java.util.Date dataIni,java.util.Date dataFin) throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM ordemServico os");
        comando.append("JOIN orcamento orc ON os.idOrcamento = orc.id");
        comando.append("JOIN funcionario fun ON fun.id = orc.idFuncionario");
        comando.append("JOIN cargo ON fun.idCargo = cargo.id");
        comando.append("JOIN cliente cli ON orc.idCliente = cli.id");
        comando.append("WHERE os.dataInicio BETWEEN ? AND ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setDate(1,new Date(dataIni.getTime()));
        preparedStatement.setDate(2,new Date(dataFin.getTime()));
        ResultSet rs = preparedStatement.executeQuery();

        List<Os> listaOs = new ArrayList<Os>();
        while (rs.next()) {

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

            Os ordemServico = new Os();
            ordemServico.setOrcamento(orcamento);
            ordemServico.setId(rs.getInt("os.id"));
            ordemServico.setStatus(rs.getInt("os.status"));
            ordemServico.setDataCriacao(rs.getDate("os.dataInicio"));
            ordemServico.setTitulo(rs.getString("os.titulo"));
            ordemServico.setDataUltimaAtualizacao(rs.getDate("os.dataUltimaAtualizacao"));

            listaOs.add(ordemServico);

        }

        dbConenection.close();

        return listaOs;
    }


}
