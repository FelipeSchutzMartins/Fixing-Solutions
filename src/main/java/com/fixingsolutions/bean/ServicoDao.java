package com.fixingsolutions.bean;

import com.fixingsolutions.domain.Cargo;
import com.fixingsolutions.domain.Conexao;
import com.fixingsolutions.domain.Servico;
import com.fixingsolutions.repository.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDao implements Dao<Servico> {

    private Conexao conexao = new Conexao();

    @Override
    public Servico get(int id) throws SQLException {
        String comando = "select * from tiposervico where id = ?";
        Servico servico = new Servico();

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();

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

        List<Servico> servicos = new ArrayList<Servico>();
        Servico servico = null;
        String comando = "select * from tiposervico";
        Connection dbConenection = conexao.abrirConexao();
        Statement stmt = dbConenection.createStatement();
        ResultSet rs = stmt.executeQuery(comando);

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
        String comando = "insert into tiposervico(descricao,valor) values (?,?)";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setString(1, servico.getDescricao());
        preparedStatement.setBigDecimal(2, servico.getValor());

        int rs = preparedStatement.executeUpdate();
        dbConenection.close();

    }

    public void save(Servico servico,Integer idOrcamento) throws SQLException {

        save(servico);

        servico.setId(findByDescricao(servico.getDescricao()).getId());

        String comando = "insert into tiposervico_orcamento(idTipoServico,idOrcamento) values (?,?)";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1, servico.getId());
        preparedStatement.setInt(2, idOrcamento);

        int rs = preparedStatement.executeUpdate();
        dbConenection.close();

    }

    @Override
    public void update(Servico servico) throws SQLException {
        String comando = "update tiposervico set descricao = ?,valor = ? where id = ?";

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setString(1, servico.getDescricao());
        preparedStatement.setBigDecimal(2, servico.getValor());
        preparedStatement.setInt(3, servico.getId());

        int rs = preparedStatement.executeUpdate();

        dbConenection.close();

    }

    @Override
    public void delete(Integer id) throws SQLException{
        String comando = "delete from tiposervico where id = ?";

        deleteFromMm(id);

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);

        int rs = preparedStatement.executeUpdate();
        dbConenection.close();

    }

    public Servico findByDescricao(String descricao) throws SQLException {
        String comando = "select * from tiposervico where descricao = ?";
        Servico servico = new Servico();

        Connection dbConenection = conexao.abrirConexao();
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
        Connection dbConenection = conexao.abrirConexao();
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

        Connection dbConenection = conexao.abrirConexao();
        PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
        preparedStatement.setInt(1,id);

        int rs = preparedStatement.executeUpdate();
        dbConenection.close();

    }

}
