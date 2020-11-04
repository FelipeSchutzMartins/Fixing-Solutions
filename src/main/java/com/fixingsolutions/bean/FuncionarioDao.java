package com.fixingsolutions.bean;

import com.fixingsolutions.domain.Conexao;
import com.fixingsolutions.repository.Dao;
import com.fixingsolutions.domain.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao implements Dao<Funcionario> {
  private Conexao conexao = new Conexao();

  @Override
  public Funcionario get(int id){
    String comando = "select * from funcionario where id = ?";
    Funcionario funcionario = new Funcionario();

    try{
      Connection dbConenection = conexao.abrirConexao();
      PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
      preparedStatement.setInt(1,id);
      ResultSet rs = preparedStatement.executeQuery();

      if(rs.next()){

        funcionario.setId(rs.getInt("id"));
        funcionario.setEmail(rs.getString("email"));
        funcionario.setPassword(rs.getString("password"));
        funcionario.setNome(rs.getString("nome"));
        funcionario.setIdCargo(rs.getInt("idCargo"));

      }

      dbConenection.close();
    }catch (Exception e){

      System.out.println("Erro ao buscar funcionario"+e);

    }
    return funcionario;
  }

  @Override
  public List<Funcionario> getAll(){

    List<Funcionario> funcionarios = new ArrayList<Funcionario>();
    Funcionario funcionario = null;
    String comando = "select * from funcionario";
    try{
      Connection dbConenection = conexao.abrirConexao();
      Statement stmt = dbConenection.createStatement();
      ResultSet rs = stmt.executeQuery(comando);

      while(rs.next()) {
        funcionario = new Funcionario();
        funcionario.setId(rs.getInt("id"));
        funcionario.setIdCargo(rs.getInt("idCargo"));
        funcionario.setNome(rs.getString("nome"));
        funcionario.setEmail(rs.getString("email"));
        funcionario.setPassword(rs.getString("password-"));
        funcionarios.add(funcionario);
      }

      dbConenection.close();

    }catch(Exception e){

      System.out.println("Erro ao buscar funcionarios"+e);

    }

    return funcionarios;

  }

  @Override
  public void save(Funcionario funcionario){
    String comando = "insert into funcionario(nome,email,password,idCargo) values ?,?,?,?";

    try{
      Connection dbConenection = conexao.abrirConexao();
      PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
      preparedStatement.setString(1,funcionario.getNome());
      preparedStatement.setString(2,funcionario.getEmail());
      preparedStatement.setString(3,funcionario.getPassword());
      preparedStatement.setInt(4,funcionario.getIdCargo());

      ResultSet rs = preparedStatement.executeQuery();

      dbConenection.close();

    }catch (Exception e){

      System.out.println("Erro ao inserir funcionario"+e);

    }

  }

  @Override
  public void update(Funcionario funcionario){
    String comando = "update funcionario set nome = ?,email = ?,password = ?, idCargo = ? where id = ?";

    try{
      Connection dbConenection = conexao.abrirConexao();
      PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
      preparedStatement.setString(1,funcionario.getNome());
      preparedStatement.setString(2,funcionario.getEmail());
      preparedStatement.setString(3,funcionario.getPassword());
      preparedStatement.setInt(4,funcionario.getIdCargo());
      preparedStatement.setInt(5,funcionario.getId());

      ResultSet rs = preparedStatement.executeQuery();

      dbConenection.close();

    }catch (Exception e){

      System.out.println("Erro ao atualizar funcionario"+e);

    }

  }

  @Override
  public void delete(Funcionario funcionario){
    String comando = "delete from funcionario where id = ?";

    try{
      Connection dbConenection = conexao.abrirConexao();
      PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando);
      preparedStatement.setInt(1,funcionario.getId());

      ResultSet rs = preparedStatement.executeQuery();

      dbConenection.close();

    }catch (Exception e){

      System.out.println("Erro ao deletar funcionario"+e);

    }

  }


}
