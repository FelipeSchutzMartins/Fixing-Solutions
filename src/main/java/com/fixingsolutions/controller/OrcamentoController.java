package com.fixingsolutions.controller;

import com.fixingsolutions.bean.ClienteDao;
import com.fixingsolutions.bean.FuncionarioDao;
import com.fixingsolutions.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

@RestController
public class OrcamentoController {

    @PostMapping(value = "/criarOrcamento",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> criarOrcamento(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            List<Servico> servicos = new ArrayList<>();
            List<?> paramsServicos = (ArrayList) params.get("servicos");

            for(int i=0;i<paramsServicos.size();i++){

                Servico servico = new Servico();

                LinkedHashMap ob = (LinkedHashMap) paramsServicos.get(i);

                servico.setDescricao((String) ob.get("descricao"));
                servico.setValor(new BigDecimal((String) ob.get("valor")));

                servicos.add(servico);

            }

            ClienteDao clienteDao = new ClienteDao();
            Cliente cliente = clienteDao.get((Integer) params.get("cliente"));

            FuncionarioDao funcionarioDao = new FuncionarioDao();
            Funcionario funcionario = funcionarioDao.get((Integer) params.get("responsavel"));

            Orcamento orcamento = new Orcamento();
            orcamento.setCliente(cliente);
            orcamento.setFuncionario(funcionario);
            orcamento.setData(new Date());
            orcamento.setValor(new BigDecimal((String) params.get("horasPrevistas")));
            orcamento.setHorasPrevistas();

        }catch (Exception e){
            System.out.println("Houve um problema a criar conta || e: "+e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @GetMapping("/buscarOrcamentos")
    public ResponseEntity<?> buscarOrcamento(){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @DeleteMapping(value = "/deletarOrcamento",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deletarOrcamento(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {



        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @PutMapping(value = "/editarOrcamento",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editarOrcamento(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {



        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @GetMapping("/buscarServicos")
    public ResponseEntity<?> buscarServicos(){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);


    }

    @GetMapping("/buscarResponsaveis")
    public ResponseEntity<?> buscarResponsaveis(){

        AjaxResponseBody resposta = new AjaxResponseBody();

        List<Funcionario> funcionarios;
        FuncionarioDao funcionarioDao = new FuncionarioDao();

        try {

            funcionarios = funcionarioDao.getAll();
            resposta.setResult(funcionarios);

        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);


    }

    @GetMapping("/buscarCliente")
    public ResponseEntity<?> buscarCliente(){

        AjaxResponseBody resposta = new AjaxResponseBody();

        List<Cliente> clientes;
        ClienteDao clienteDao = new ClienteDao();

        try {

            clientes = clienteDao.getAll();
            resposta.setResult(clientes);

        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);


    }

}
