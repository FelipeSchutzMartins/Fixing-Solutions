package com.fixingsolutions.controller;

import com.fixingsolutions.bean.*;
import com.fixingsolutions.domain.*;
import com.fixingsolutions.service.InputValidationService;
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
        OsDao dao = new OsDao();
        try{

            List<Os> ordemServico = dao.getAll();
            if (ordemServico.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orcamentos não encontrados");
            }

            List<Map> list = new ArrayList<Map>();

            for(int i=0;i<ordemServico.size();i++){

                Map<String,Object> map = new HashMap<String, Object>();
                map.put("id",ordemServico.get(i).getId());
                map.put("titulo",ordemServico.get(i).getTitulo());
                map.put("status",ordemServico.get(i).getStatus());
                map.put("dataUltimaAtualizacao",ordemServico.get(i).getDataUltimaAtualizacao());
                map.put("dataCriacao",ordemServico.get(i).getDataCriacao());
                map.put("orcamento",ordemServico.get(i).getOrcamento());

                list.add(map);

            }

            resposta.setResult(list);

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
            if(!InputValidationService.isValidStringInput(titulo)){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Titulo inválido");
            }

            OrcamentoDao orcamentoDao = new OrcamentoDao();
            Orcamento orcamento = orcamentoDao.get(idOrcamento);

            Os ordemServico = new Os();
            ordemServico.setOrcamento(orcamento);
            ordemServico.setTitulo(titulo);
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

    @PostMapping(value = "/alterarOrcamento",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> alterarOrcamento(@RequestBody Map<String,Object> params) {

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            List<?> paramsServicos = (ArrayList) params.get("servicos");

            if(!(paramsServicos instanceof List)){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Serviços inválidos");
            }

            Integer id = (Integer) params.get("id");
            if(id==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orçamento inválido");
            }

            Integer idCliente = (Integer) params.get("cliente");
            if(idCliente==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Cliente inválido");
            }

            Integer idFuncionario = (Integer) params.get("responsavel");
            if(idFuncionario==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Responsável inválido");
            }

            Integer horasPrevistas = Integer.parseInt((String) params.get("horasPrevistas"));
            if (horasPrevistas == null) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Horas prevístas inválida");
            }

            ClienteDao clienteDao = new ClienteDao();
            Cliente cliente = clienteDao.get((Integer) params.get("cliente"));

            FuncionarioDao funcionarioDao = new FuncionarioDao();
            Funcionario funcionario = funcionarioDao.get((Integer) params.get("responsavel"));

            OrcamentoDao orcamentoDao = new OrcamentoDao();
            Orcamento orcamento = orcamentoDao.get(id);
            orcamento.setCliente(cliente);
            orcamento.setFuncionario(funcionario);
            orcamento.setId(id);
            orcamento.setHorasPrevistas(horasPrevistas);

            orcamentoDao.update(orcamento);

            ServicoDao servicoDao = new ServicoDao();

            for(int i=0;i<paramsServicos.size();i++){

                Servico servico = new Servico();

                LinkedHashMap ob = (LinkedHashMap) paramsServicos.get(i);

                String descricao = (String) ob.get("descricao");

                if(descricao==null || descricao.isEmpty()){
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Descrição do serviço Nº"+(i+1)+" inválido");
                }

                String paramValor = (String) ob.get("valor");
                if(paramValor==null){
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Valor do serviço Nº"+i+" inválido");
                }

                servico.setDescricao(descricao);
                BigDecimal valorServico = new BigDecimal(paramValor);
                if(valorServico.compareTo(new BigDecimal("0")) <= 0){
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Valor do serviço Nº"+(i+1)+" inválido");
                }

                servico.setValor(valorServico);
                servico.setId((Integer) ob.get("id"));

                if(servico.getId()!=null){

                    servicoDao.update(servico);

                }else{

                    servicoDao.save(servico,(Integer) params.get("id"));

                }

            }

            Os.recalcularValorOrcamento((Integer) params.get("id"));

        }catch (NumberFormatException e){

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Horas prevístas inválida");

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }

        return ResponseEntity.ok(resposta);

    }

    @DeleteMapping(value = "/deletarOrdemServico",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deletarOrdemServico(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            Integer id = (Integer) params.get("id");
            if(id==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orçamento inválido");
            }

            OsDao dao = new  OsDao();
            dao.delete(id);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }
    @PostMapping(value = "/editarOs",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editarOs(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {
            Integer id = (Integer) params.get("id");
            if(id==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orcamento inválido");
            }

            String titulo = (String) params.get("titulo");
            if(!InputValidationService.isValidStringInput(titulo)){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Titulo inválido");
            }

            OsDao dao = new OsDao();

            Os ordemServico = dao.get(id);
            ordemServico.setTitulo(titulo);
            dao.update(ordemServico);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @PostMapping(value = "/ingressar",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> ingressar(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {
            Integer id = (Integer) params.get("id");
            if(id==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orcamento inválido");
            }

            OsDao osDao = new OsDao();
            FuncionarioDao funcionarioDao = new FuncionarioDao();
            OrcamentoDao orcamentoDao = new OrcamentoDao();

            Token token = FuncionarioDao.findToken();
            Funcionario funcionario = funcionarioDao.get(token.getUserId());

            Os ordemServico = osDao.get(id);
            Orcamento orcamento = ordemServico.getOrcamento();
            orcamento.setFuncionario(funcionario);
            orcamentoDao.update(orcamento);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @PostMapping(value = "/alterarStatus",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> alterarStatus(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {
            Integer id = (Integer) params.get("id");
            if(id==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orcamento inválido");
            }

            Integer status = (Integer) params.get("status");
            if(status==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Status inválido");
            }

            if(status<0 || status>3){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Status inválido");
            }

            OsDao osDao = new OsDao();
            Os ordemServico = osDao.get(id);
            ordemServico.setStatus(status);
            osDao.update(ordemServico);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }


}
