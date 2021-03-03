package com.fixingsolutions.controller;

import com.fixingsolutions.bean.ClienteDao;
import com.fixingsolutions.bean.FuncionarioDao;
import com.fixingsolutions.bean.OrcamentoDao;
import com.fixingsolutions.bean.ServicoDao;
import com.fixingsolutions.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

            OrcamentoDao orcamentoDao = new OrcamentoDao();
            Orcamento orcamento = new Orcamento();
            orcamento.setCliente(cliente);
            orcamento.setFuncionario(funcionario);
            orcamento.setData(new Date());

            Number valor = (Number) params.get("valor");

            orcamento.setValor(new BigDecimal(valor.toString()));
            orcamento.setHorasPrevistas(new Integer((String) params.get("horasPrevistas")));
            orcamento.setServicos(servicos.toArray());

            orcamentoDao.save(orcamento);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @GetMapping("/buscarOrcamentos")
    public ResponseEntity<?> buscarOrcamento(){

        AjaxResponseBody resposta = new AjaxResponseBody();
        OrcamentoDao dao = new OrcamentoDao();
        try {
            List<Orcamento> orcamentos = dao.getAll();
            if (orcamentos.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orcamentos não encontrados");
            }

                resposta.setResult(orcamentos);

            }catch(Exception e){
            e.printStackTrace();
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

            Integer id = (Integer) params.get("id");


            OrcamentoDao dao = new  OrcamentoDao();
            dao.delete(id);

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

            ClienteDao clienteDao = new ClienteDao();
            Cliente cliente = clienteDao.get((Integer) params.get("cliente"));

            FuncionarioDao funcionarioDao = new FuncionarioDao();
            Funcionario funcionario = funcionarioDao.get((Integer) params.get("responsavel"));

            OrcamentoDao orcamentoDao = new OrcamentoDao();
            Orcamento orcamento = new Orcamento();
            orcamento.setCliente(cliente);
            orcamento.setFuncionario(funcionario);
            orcamento.setData(new Date());
            orcamento.setId((Integer) params.get("id"));

            Number valor = (Number) params.get("valor");

            orcamento.setValor(new BigDecimal(valor.toString()));
            orcamento.setHorasPrevistas(new Integer((String) params.get("horasPrevistas")));

            orcamentoDao.update(orcamento);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @PutMapping(value = "/buscarServicos",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> buscarServicos(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            ServicoDao servicoDao = new ServicoDao();

            List<Servico> servicos = servicoDao.findByOrcamento((Integer) params.get("id"));

            resposta.setResult(servicos);

        }catch(Exception e){
            e.printStackTrace();
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

    @DeleteMapping(value = "/excluirServico",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> excluirServico(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            Integer id = (Integer) params.get("id");

            ServicoDao dao = new  ServicoDao();
            dao.delete(id);

        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @PostMapping(value = "/salvarServicos",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> salvarServicos(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            List<?> paramsServicos = (ArrayList) params.get("servicos");
            ServicoDao servicoDao = new ServicoDao();

            for(int i=0;i<paramsServicos.size();i++){

                Servico servico = new Servico();

                LinkedHashMap ob = (LinkedHashMap) paramsServicos.get(i);

                servico.setDescricao((String) ob.get("descricao"));

                Object valor = ob.get("valor");

                servico.setValor(new BigDecimal(valor.toString()));
                servico.setId((Integer) ob.get("id"));

                if(servico.getId()!=null){

                    servicoDao.update(servico);

                }else{

                    servicoDao.save(servico,(Integer) params.get("id"));

                }

            }

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }



}
