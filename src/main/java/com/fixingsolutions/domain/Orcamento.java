package com.fixingsolutions.domain;


import java.math.BigDecimal;
import java.sql.Date;

public class Orcamento {

    private Integer id;
    private Date data;
    private Integer horasPrevistas;
    private BigDecimal valor;
    private Funcionario funcionario;
    private Cliente cliente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getHorasPrevistas() {
        return horasPrevistas;
    }

    public void setHorasPrevistas(Integer horasPrevistas) {
        this.horasPrevistas = horasPrevistas;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
