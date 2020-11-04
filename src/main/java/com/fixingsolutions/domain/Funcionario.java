package com.fixingsolutions.domain;

public class Funcionario{
    private int id;
    private int idCargo;
    private String email;
    private String password;
    private String nome;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setIdCargo(int idCargo){
        this.idCargo = idCargo;
    }

    public int getIdCargo(){
        return this.idCargo;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
