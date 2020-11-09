package com.fixingsolutions.domain;

public class Funcionario{
    private Integer id;
    private Integer idCargo;
    private String email;
    private String password;
    private String nome;
    private boolean logado;

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }

    public void setIdCargo(Integer idCargo){
        this.idCargo = idCargo;
    }

    public Integer getIdCargo(){
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

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }
}
