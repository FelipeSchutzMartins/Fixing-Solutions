package com.fixingsolutions.domain;

public class Funcionario extends Usuario
{
    private Cargo cargo;
    private String password;

    public void setCargo(Cargo cargo){
        this.cargo = cargo;
    }

    public Cargo getCargo(){
        return this.cargo;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

}
