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

public class ServicoDao implements Dao<Servico> {

    @Override
    public Servico get(int id) throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM tiposervico");
        comando.append("WHERE id = ? \n");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();

        Servico servico = new Servico();
        if(rs.next()){

            servico.setId(rs.getInt("id"));
            servico.setValor(rs.getBigDecimal("valor"));
            servico.setDescricao(rs.getString("descricao"));

        }

        dbConenection.close();
        return servico;
    }

    @Override
    public List<Servico> getAll() throws SQLException{

        StringBuilder comando = new StringBuilder();
        comando.append("SELECT \n");
        comando.append(" * \n");
        comando.append("FROM tiposervico");

        Connection dbConenection = connection.abrirConexao();
        Statement stmt = dbConenection.createStatement();
        ResultSet rs = stmt.executeQuery(comando.toString());

        List<Servico> servicos = new ArrayList<Servico>();
        Servico servico;

        while(rs.next()) {
            servico = new Servico();
            servico.setId(rs.getInt("id"));
            servico.setDescricao(rs.getString("descricao"));
            servico.setValor(rs.getBigDecimal("valor"));
            servicos.add(servico);
        }

        dbConenection.close();

        return servicos;

    }

    @Override
    public void save(Servico servico) throws SQLException{

        StringBuilder comando = new StringBuilder();
        comando.append("INSERT INTO tiposervico(descricao,valor) \n");
        comando.append("VALUES(?,?)");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setString(1, servico.getDescricao());
        preparedStatement.setBigDecimal(2, servico.getValor());

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    public void save(Servico servico,Integer idOrcamento) throws SQLException {

        save(servico);

        servico.setId(findByDescricao(servico.getDescricao()).getId());

        StringBuilder comando = new StringBuilder();
        comando.append("INSERT INTO tiposervico_orcamento(idTipoServico,idOrcamento) \n");
        comando.append("VALUES(?,?)");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1, servico.getId());
        preparedStatement.setInt(2, idOrcamento);

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    @Override
    public void update(Servico servico) throws SQLException {

        StringBuilder comando = new StringBuilder();
        comando.append("UPDATE tiposervico\n");
        comando.append("SET descricao = ?,\n");
        comando.append("    valor = ?,\n");
        comando.append("WHERE id = ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setString(1, servico.getDescricao());
        preparedStatement.setBigDecimal(2, servico.getValor());
        preparedStatement.setInt(3, servico.getId());

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    @Override
    public void delete(Integer id) throws SQLException{

        deleteFromMm(id);

        StringBuilder comando = new StringBuilder();
        comando.append("DELETE\n");
        comando.append("FROM tiposervico\n");
        comando.append("WHERE id = ?");

        Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
        preparedStatement.setInt(1,id);

        executarUpdate(preparedStatement);

        dbConenection.close();

    }

    public Servico findByDescricao(String descricao) throws SQLException {
        String comando = "select * from tiposervico where descricao = ?";
        Servico servico = new Servico();

        java.sql.Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setString(1,descricao);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){

            servico.setId(rs.getInt("id"));
            servico.setValor(rs.getBigDecimal("valor"));
            servico.setDescricao(rs.getString("descricao"));

        }
        dbConenection.close();
        return servico;
    }

    public List<Servico> findByOrcamento(Integer idOrcamento) throws SQLException{

        List<Servico> servicos = new ArrayList<Servico>();
        Servico servico = null;
        String comando = "select * from tiposervico ts join tiposervico_orcamento tsc on tsc.idTipoServico = ts.id where tsc.idOrcamento = ?";
        java.sql.Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,idOrcamento);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()) {
            servico = new Servico();
            servico.setId(rs.getInt("ts.id"));
            servico.setDescricao(rs.getString("ts.descricao"));
            servico.setValor(rs.getBigDecimal("ts.valor"));
            servicos.add(servico);
        }

        dbConenection.close();

        return servicos;

    }

    public void deleteFromMm(Integer id) throws SQLException{
        String comando = "delete from tiposervico_orcamento where idTipoServico = ?";

        java.sql.Connection dbConenection = connection.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);

        int rs = preparedStatement.executeUpdate();
        dbConenection.close();

    }

}
