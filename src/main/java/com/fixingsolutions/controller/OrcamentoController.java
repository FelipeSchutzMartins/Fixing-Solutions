package com.fixingsolutions.controller;

import com.fixingsolutions.bean.CargoDao;
import com.fixingsolutions.bean.ClienteDao;
import com.fixingsolutions.bean.FuncionarioDao;
import com.fixingsolutions.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OrcamentoController {

    @PostMapping(value = "/criarOrcamento",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> criarOrcamento(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            Orcamento orcamento = new Orcamento();
            Cliente cliente = new Cliente();
            Funcionario funcionario = new Funcionario();

            List<?> servicos = new ArrayList<>();

            servicos = (ArrayList) params.get("servicos");

            for(int i=0;i<servicos.size()-1;i++){

                Servico servico = new Servico();

                LinkedHashMap hashMap = new LinkedHashMap();
                LinkedHashMap.

                servico.setDescricao(hashMap.));
                servico.setValor(.valor);

            }

            System.out.println();

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