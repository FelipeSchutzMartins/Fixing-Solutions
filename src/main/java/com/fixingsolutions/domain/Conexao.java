package com.fixingsolutions.domain;

import java.sql.Connection;

public class Conexao {

    private Connection conexao;

    public Connection abrirConexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexao = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/fixing_solutions?"
                    +"user=root&password=root&useTimezone=true&serverTimezone=UTC");
        }catch(Exception e) {
            e.printStackTrace();
        }
        return conexao;
    }

}