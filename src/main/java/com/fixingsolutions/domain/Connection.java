package com.fixingsolutions.domain;
public class Connection {

    private java.sql.Connection connection;

    public java.sql.Connection abrirConexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/fixing_solutions?"
                    +"user=root&password=root&useTimezone=true&serverTimezone=UTC");
        }catch(Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}