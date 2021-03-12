package com.fixingsolutions.bean;

import com.fixingsolutions.domain.*;
import com.fixingsolutions.repository.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OsDao implements Dao<Os> {

    static Conexao conexao = new Conexao();

    @Override
    public Os get(int id) throws SQLException {
        String comando = "select * from ordemServico os join orcamento orc on os.idOrcamento = orc.id join funcionario fun on fun.id = orc.idFuncionario join cargo on fun.idCargo = cargo.id join cliente cli on orc.idCliente = cli.id where orc.id = ?";
        Os ordemServico = new Os();

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();

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
        List<Os> listaOs = new ArrayList<Os>();
        String comando = "select * from ordemServico os join orcamento orc on os.idOrcamento = orc.id join funcionario fun on fun.id = orc.idFuncionario join cargo on fun.idCargo = cargo.id join cliente cli on orc.idCliente = cli.id";

        Connection dbConenection = conexao.abrirConexao();
        Statement stmt = dbConenection.createStatement();
        ResultSet rs = stmt.executeQuery(comando);

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

        String comando = "insert into ordemServico(status,titulo,dataInicio,dataUltimaAtualizacao,idOrcamento) values(?,?,?,?,?)";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,os.getStatus());
        preparedStatement.setString(2,os.getTitulo());
        preparedStatement.setDate(3,new java.sql.Date(os.getDataCriacao().getTime()));
        preparedStatement.setDate(4,new java.sql.Date(os.getDataUltimaAtualizacao().getTime()));
        preparedStatement.setInt(5,os.getOrcamento().getId());

        int rs = preparedStatement.executeUpdate();

        dbConenection.close();

    }

    @Override
    public void update(Os os) throws SQLException {

        String comando = "update ordemServico set idOrcamento = ?, status = ?, titulo = ?, dataInicio = ?, dataUltimaAtualizacao = ? where id = ?";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,os.getOrcamento().getId());
        preparedStatement.setInt(2,os.getStatus());
        preparedStatement.setString(3,os.getTitulo());
        preparedStatement.setDate(4, new java.sql.Date(os.getDataCriacao().getTime()));
        preparedStatement.setDate(5,new java.sql.Date(os.getDataUltimaAtualizacao().getTime()));
        preparedStatement.setInt(6,os.getId());

        Integer rs = preparedStatement.executeUpdate();

        dbConenection.close();

    }

    @Override
    public void delete(Integer id) throws SQLException {

        String comando = "delete from ordemservico where id = ?";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);

        int rs = preparedStatement.executeUpdate();

        dbConenection.close();

    }
}
