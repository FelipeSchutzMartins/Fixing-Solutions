package com.fixingsolutions.domain;

import com.fixingsolutions.bean.OrcamentoDao;
import com.fixingsolutions.bean.ServicoDao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Os {

    private Integer id;
    private String titulo;
    private Integer status;
    private Date dataUltimaAtualizacao;
    private Date dataCriacao;
    private Orcamento orcamento;

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static void recalcularValorOrcamento(Integer id) throws Exception{

        OrcamentoDao orcamentoDao = new OrcamentoDao();
        ServicoDao servicoDao   = new ServicoDao();

        Orcamento orcamento = orcamentoDao.get(id);

        List<Servico> servicos = servicoDao.findByOrcamento(orcamento.getId());

        BigDecimal valor = new BigDecimal("0");

        for(Servico servico : servicos){

            valor = valor.add(servico.getValor());

        }

        orcamento.setValor(valor);

        orcamentoDao.update(orcamento);

    }

}
