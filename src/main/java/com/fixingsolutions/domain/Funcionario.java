package com.fixingsolutions.domain;

public class Funcionario extends Usuario
{
    private Integer idCargo;
    private String password;

    public void setIdCargo(Integer idCargo){
        this.idCargo = idCargo;
    }

    public Integer getIdCargo(){
        return this.idCargo;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

}
