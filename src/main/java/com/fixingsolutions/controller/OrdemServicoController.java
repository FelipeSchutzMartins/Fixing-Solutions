package com.fixingsolutions.controller;

import com.fixingsolutions.bean.ClienteDao;
import com.fixingsolutions.bean.FuncionarioDao;
import com.fixingsolutions.bean.OrcamentoDao;
import com.fixingsolutions.bean.OsDao;
import com.fixingsolutions.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class OrdemServicoController {

    @GetMapping("/buscarOs")
    public ResponseEntity<?> buscarOs(){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try{

        }catch (Exception e){

            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");

        }

        return ResponseEntity.ok(resposta);

    }

    @PostMapping(value = "/criarOs",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> criarOs(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            Integer idOrcamento = (Integer) params.get("orcamento");
            if(idOrcamento==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orcamento inválido");
            }

            String titulo = (String) params.get("titulo");
            if(titulo==null||titulo==""){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Titulo inválido");
            }

            OrcamentoDao orcamentoDao = new OrcamentoDao();
            Orcamento orcamento = orcamentoDao.get(idOrcamento);

            Os ordemServico = new Os();
            ordemServico.setOrcamento(orcamento);
            ordemServico.setTitulo(titulo
            );
            ordemServico.setStatus(1);
            ordemServico.setDataCriacao(new Date());
            ordemServico.setDataUltimaAtualizacao(new Date());

            OsDao ordemServicoDao = new OsDao();
            ordemServicoDao.save(ordemServico);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

}
