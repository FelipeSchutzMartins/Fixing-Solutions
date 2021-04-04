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
import java.nio.charset.Charset;
import java.util.Random;

public class FuncionarioDao implements Dao<Funcionario> {

  @Override
  public Funcionario get(int id) throws SQLException{

    StringBuilder comando = new StringBuilder();
    comando.append("SELECT \n");
    comando.append(" * \n");
    comando.append("FROM funcionario fun \n");
    comando.append("JOIN cargo ON fun.idCargo = cargo.id \n");
    comando.append("WHERE fun.id = ? \n");

    Funcionario funcionario = new Funcionario();

    Connection dbConenection = connection.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
    preparedStatement.setInt(1,id);
    ResultSet rs = preparedStatement.executeQuery();

    if(rs.next()){

      funcionario.setId(rs.getInt("fun.id"));
      funcionario.setEmail(rs.getString("fun.email"));
      funcionario.setSenha(rs.getString("fun.password"));
      funcionario.setNome(rs.getString("fun.nome"));

      Cargo cargo = new Cargo();
      cargo.setId(rs.getInt("cargo.id"));
      cargo.setDescricao(rs.getString("cargo.descricao"));

      funcionario.setCargo(cargo);
    }

    dbConenection.close();

    return funcionario;
  }

  @Override
  public List<Funcionario> getAll() throws SQLException {

    List<Funcionario> funcionarios = new ArrayList<Funcionario>();
    Funcionario funcionario;
    StringBuilder comando = new StringBuilder();
    comando.append("SELECT \n");
    comando.append(" * \n");
    comando.append("FROM funcionario fun \n");
    comando.append("JOIN cargo ON fun.idCargo = cargo.id \n");

    Connection dbConenection = connection.abrirConexao();
    Statement stmt = dbConenection.createStatement();
    ResultSet rs = stmt.executeQuery(comando.toString());

    while(rs.next()) {

        Cargo cargo = new Cargo();
        cargo.setId(rs.getInt("cargo.id"));
        cargo.setDescricao(rs.getString("cargo.descricao"));

        funcionario = new Funcionario();
        funcionario.setId(rs.getInt("fun.id"));
        funcionario.setCargo(cargo);
        funcionario.setNome(rs.getString("fun.nome"));
        funcionario.setEmail(rs.getString("fun.email"));
        funcionario.setSenha(rs.getString("fun.password"));
        funcionarios.add(funcionario);

    }

    dbConenection.close();

    return funcionarios;

  }

  @Override
  public void save(Funcionario funcionario) throws SQLException{

    StringBuilder comando = new StringBuilder();
    comando.append("INSERT INTO funcionario(nome,email,password,idCargo) \n");
    comando.append("VALUES(?,?,?,?)");

    Connection dbConenection = connection.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
    preparedStatement.setString(1,funcionario.getNome());
    preparedStatement.setString(2,funcionario.getEmail());
    preparedStatement.setString(3,funcionario.getSenha());
    preparedStatement.setInt(4,funcionario.getCargo().getId());

    executarUpdate(preparedStatement);

    dbConenection.close();

  }

  @Override
  public void update(Funcionario funcionario) throws SQLException{

    StringBuilder comando = new StringBuilder();
    comando.append("UPDATE funcionario\n");
    comando.append("SET nome = ?,\n");
    comando.append("    email = ?,\n");
    comando.append("    password = ?,\n");
    comando.append("    idCargo = ?\n");
    comando.append("WHERE id = ?");

    Connection dbConenection = connection.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
    preparedStatement.setString(1,funcionario.getNome());
    preparedStatement.setString(2,funcionario.getEmail());
    preparedStatement.setString(3,funcionario.getSenha());
    preparedStatement.setInt(4,funcionario.getCargo().getId());
    preparedStatement.setInt(5,funcionario.getId());

    executarUpdate(preparedStatement);

    dbConenection.close();

  }

  @Override
  public void delete(Integer id) throws SQLException{

    StringBuilder comando = new StringBuilder();
    comando.append("DELETE\n");
    comando.append("FROM funcionario\n");
    comando.append("WHERE id = ?");

    Connection dbConenection = connection.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
    preparedStatement.setInt(1,id);

    executarUpdate(preparedStatement);

    dbConenection.close();

  }

  public Funcionario findByEmailSenha(String email,String senha) throws SQLException{

    StringBuilder comando = new StringBuilder();
    comando.append("SELECT \n");
    comando.append(" * \n");
    comando.append("FROM funcionario fun \n");
    comando.append("JOIN cargo ON fun.idCargo = cargo.id \n");
    comando.append("WHERE fun.email = ? \n");
    comando.append("AND fun.password = ?");

    Funcionario funcionario = new Funcionario();

    Connection dbConenection = connection.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
    preparedStatement.setString(1,email);
    preparedStatement.setString(2,senha);
    ResultSet rs = preparedStatement.executeQuery();

    if(rs.next()) {

      funcionario.setId(rs.getInt("id"));

      gerarToken(funcionario.getId());

      Cargo cargo = new Cargo();
      cargo.setId(rs.getInt("cargo.id"));
      cargo.setDescricao(rs.getString("cargo.descricao"));

      funcionario.setCargo(cargo);
      funcionario.setEmail(rs.getString("email"));
      funcionario.setSenha(rs.getString("password"));
      funcionario.setNome(rs.getString("nome"));
    }

    dbConenection.close();
    return funcionario;

  }

  public void gerarToken(Integer id) throws SQLException {

    StringBuilder comando = new StringBuilder();
    comando.append("INSERT INTO token(code,user_id) \n");
    comando.append("VALUES(?,?)");

    Connection dbConenection = connection.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());

    byte[] array = new byte[10];
    new Random().nextBytes(array);
    String code = new String(array, Charset.forName("UTF-8"));

    preparedStatement.setString(1,code);
    preparedStatement.setInt(2,id);

    deletarToken();

    executarUpdate(preparedStatement);

    dbConenection.close();

  }

  public static Token findToken() throws SQLException {

    Token token = new Token();
    StringBuilder comando = new StringBuilder();
    comando.append("SELECT \n");
    comando.append(" * \n");
    comando.append("FROM token\n");

    Connection dbConenection = connection.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());
    ResultSet rs = preparedStatement.executeQuery();

    if(rs.next()){

      token.setId(rs.getInt("id"));
      token.setUserId(rs.getInt("user_id"));
      token.setCode(rs.getString("code"));

    }

    dbConenection.close();

    return token;
  }

  public Token deletarToken() throws SQLException {

    Token token = new Token();
    StringBuilder comando = new StringBuilder();
    comando.append("delete \n");
    comando.append("FROM token\n");

    Connection dbConenection = connection.abrirConexao();
    PreparedStatement preparedStatement  = dbConenection.prepareStatement(comando.toString());

    executarUpdate(preparedStatement);

    dbConenection.close();

    return token;
  }

}
