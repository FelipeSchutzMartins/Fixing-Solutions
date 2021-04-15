package com.fixingsolutions.domain;

import com.fixingsolutions.bean.OrcamentoDao;
import com.fixingsolutions.bean.ServicoDao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Os {

    private Integer id;
    private String titulo;
    private Integer status;
    private Date dataUltimaAtualizacao;
    private Date dataCriacao;
    private Orcamento orcamento;

    private String pattern = "dd-MM-yyyy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

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

    public String getDataUltimaAtualizacao() throws ParseException {
        return simpleDateFormat.format(dataUltimaAtualizacao);
    }

    public Date getDataUltimaAtualizacaoDate(){
        return this.dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) throws ParseException {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public String getDataCriacao() throws ParseException {
        return simpleDateFormat.format(dataCriacao);
    }

    public Date getDataCriacaoDate(){
        return this.dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) throws ParseException {
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
