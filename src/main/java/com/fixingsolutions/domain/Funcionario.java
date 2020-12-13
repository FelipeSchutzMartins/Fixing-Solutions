package com.fixingsolutions.domain;

public class Funcionario extends Usuario
{
    private Cargo cargo;
    private String senha;

    public void setCargo(Cargo cargo){
        this.cargo = cargo;
    }

    public Cargo getCargo(){
        return this.cargo;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getSenha(){
        return this.senha;
    }

}
