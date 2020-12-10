package com.fixingsolutions.bean;

import com.fixingsolutions.domain.Cargo;
import com.fixingsolutions.domain.Conexao;
import com.fixingsolutions.repository.Dao;
import com.fixingsolutions.domain.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao implements Dao<Funcionario> {
  private Conexao conexao = new Conexao();

  @Override
  public Funcionario get(int id) throws SQLException{
    String comando = "select * from funcionario fun join cargo car on fun.idCargo = car.id where fun.id = ?";
    Funcionario funcionario = new Funcionario();

    Connection dbConenection = conexao.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
    preparedStatement.setInt(1,id);
    ResultSet rs = preparedStatement.executeQuery();

    if(rs.next()){

      funcionario.setId(rs.getInt("fun.id"));
      funcionario.setEmail(rs.getString("fun.email"));
      funcionario.setPassword(rs.getString("fun.password"));
      funcionario.setNome(rs.getString("fun.nome"));

      Cargo cargo = new Cargo();
      cargo.setId(rs.getInt("car.id"));
      cargo.setDescricao(rs.getString("car.descricao"));

      funcionario.setCargo(cargo);
    }

    dbConenection.close();

    return funcionario;
  }

  @Override
  public List<Funcionario> getAll() throws SQLException {

    List<Funcionario> funcionarios = new ArrayList<Funcionario>();
    Funcionario funcionario = null;
    String comando = "select * from funcionario fun join cargo car on fun.idCargo = car.id ";

    Connection dbConenection = conexao.abrirConexao();
    Statement stmt = dbConenection.createStatement();
    ResultSet rs = stmt.executeQuery(comando);

    while(rs.next()) {

        Cargo cargo = new Cargo();
        cargo.setId(rs.getInt("car.id"));
        cargo.setDescricao(rs.getString("car.descricao"));

        funcionario = new Funcionario();
        funcionario.setId(rs.getInt("fun.id"));
        funcionario.setCargo(cargo);
        funcionario.setNome(rs.getString("fun.nome"));
        funcionario.setEmail(rs.getString("fun.email"));
        funcionario.setPassword(rs.getString("fun.password"));
        funcionarios.add(funcionario);

    }

    dbConenection.close();

    return funcionarios;

  }

  @Override
  public void save(Funcionario funcionario) throws SQLException{

    String comando = "insert into funcionario(nome,email,password,idCargo) values (?,?,?,?)";
    Connection dbConenection = conexao.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
    preparedStatement.setString(1,funcionario.getNome());
    preparedStatement.setString(2,funcionario.getEmail());
    preparedStatement.setString(3,funcionario.getPassword());
    preparedStatement.setInt(4,funcionario.getCargo().getId());

    int rs = preparedStatement.executeUpdate();

    dbConenection.close();

  }

  @Override
  public void update(Funcionario funcionario) throws SQLException{
    String comando = "update funcionario set nome = ?,email = ?,password = ?, idCargo = ?  where id = ?";

    Connection dbConenection = conexao.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
    preparedStatement.setString(1,funcionario.getNome());
    preparedStatement.setString(2,funcionario.getEmail());
    preparedStatement.setString(3,funcionario.getPassword());
    preparedStatement.setInt(4,funcionario.getCargo().getId());
    preparedStatement.setInt(5,funcionario.getId());

    Integer rs = preparedStatement.executeUpdate();

    dbConenection.close();

  }

  @Override
  public void delete(Integer id) throws SQLException{
    String comando = "delete from funcionario where id = ?";

    Connection dbConenection = conexao.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
    preparedStatement.setInt(1,id);

    ResultSet rs = preparedStatement.executeQuery();

    dbConenection.close();

  }

  public Funcionario findByEmailSenha(String email,String senha) throws SQLException{

    String comando = "select * from funcionario fun join cargo car on fun.idCargo = car.id where fun.email = ? and fun.password   = ?";
    Funcionario funcionario = new Funcionario();

    Connection dbConenection = conexao.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
    preparedStatement.setString(1,email);
    preparedStatement.setString(2,senha);
    ResultSet rs = preparedStatement.executeQuery();

    if(rs.next()) {

      Cargo cargo = new Cargo();
      cargo.setId(rs.getInt("car.id"));
      cargo.setDescricao(rs.getString("car.descricao"));

      funcionario.setCargo(cargo);
      funcionario.setId(rs.getInt("id"));
      funcionario.setEmail(rs.getString("email"));
      funcionario.setPassword(rs.getString("password"));
      funcionario.setNome(rs.getString("nome"));
    }

    dbConenection.close();
    return funcionario;

  }


}
