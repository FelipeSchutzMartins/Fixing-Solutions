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

            if (paramsServicos == null) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Serviços inválidos");
            }

            if (!(paramsServicos instanceof List)) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Serviços inválidos");
            }

            if(paramsServicos.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Serviços inválidos");
            }

            Integer idCliente = (Integer) params.get("cliente");
            if (idCliente == null) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Cliente inválido");
            }

            Integer idFuncionario = (Integer) params.get("responsavel");
            if (idFuncionario == null) {
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

            Double valor = Double.parseDouble((String)params.get("valor"));
            if (valor == null || valor <= 0) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Valor inválido");
            }


            for (int i = 0; i < paramsServicos.size(); i++) {

                Servico servico = new Servico();

                LinkedHashMap ob = (LinkedHashMap) paramsServicos.get(i);

                String descricao = (String) ob.get("descricao");
                if (descricao == null || descricao.isEmpty()) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Descrição do serviço #" + (i + 1) + " inválido");
                }

                String paramValor = (String) ob.get("valor");
                if (paramValor == null) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Valor do serviço #" + (i + 1) + " inválido");
                }

                BigDecimal valorServico = new BigDecimal(paramValor);
                if (valorServico.compareTo(new BigDecimal("0")) <= 0) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Valor do serviço #" + (i + 1) + " inválido");
                }

                servico.setDescricao(descricao);
                servico.setValor(valorServico);

                servicos.add(servico);

            }

            ClienteDao clienteDao = new ClienteDao();
            Cliente cliente = clienteDao.get(idCliente);

            FuncionarioDao funcionarioDao = new FuncionarioDao();
            Funcionario funcionario = funcionarioDao.get(idFuncionario);

            OrcamentoDao orcamentoDao = new OrcamentoDao();
            Orcamento orcamento = new Orcamento();
            orcamento.setCliente(cliente);
            orcamento.setFuncionario(funcionario);
            orcamento.setData(new Date());

            orcamento.setValor(new BigDecimal(valor.toString()));
            orcamento.setHorasPrevistas(horasPrevistas);
            orcamento.setServicos(servicos.toArray());

            orcamentoDao.save(orcamento);
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
            if(id==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orçamento inválido");
            }

            OrcamentoDao dao = new  OrcamentoDao();
            dao.delete(id);

        }catch(Exception e){
            e.printStackTrace();
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

            Integer horasPrevistas = Integer.parseInt(params.get("horasPrevistas").toString());
            if(horasPrevistas==null){
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

            Integer id = (Integer) params.get("id");
            if(id==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orçamento inválido");
            }

            ServicoDao servicoDao = new ServicoDao();
            List<Servico> servicos = servicoDao.findByOrcamento(id);
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

            Integer id = (Integer) params.get("idServico");
            if(id==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Serviço inválido");
            }

            ServicoDao dao = new  ServicoDao();
            dao.delete(id);

            Os.recalcularValorOrcamento((Integer) params.get("idOrcamento"));

        }catch(Exception e){
            e.printStackTrace();
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

            if(!(paramsServicos instanceof List)){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Serviços inválidos");
            }

            if(paramsServicos.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Serviços inválidos");
            }

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

                String paramValor = ob.get("valor").toString();
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

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @PostMapping(value = "/aprovar",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> aprovar(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            Integer id = (Integer) params.get("id");
            if(id==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Orçamento inválido");
            }

            OrcamentoDao orcamentoDao = new OrcamentoDao();

            Orcamento orcamento = orcamentoDao.get(id);
            orcamento.setAprovado(true);
            orcamentoDao.update(orcamento);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

}
