package com.fixingsolutions.controller;

import com.fixingsolutions.bean.FuncionarioDao;
import com.fixingsolutions.domain.AjaxResponseBody;
import com.fixingsolutions.domain.Cargo;
import com.fixingsolutions.domain.Funcionario;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fixingsolutions.bean.CargoDao;

@RestController
public class FuncionarioController {

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/buscarCargos")
    public ResponseEntity buscarCargos(){

        AjaxResponseBody resposta = new AjaxResponseBody();
        CargoDao dao = new CargoDao();
        List<Cargo> cargos = dao.getAll();
        if(cargos.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Cargo não encontrado");
        }

        resposta.setResult(cargos);

        return ResponseEntity.ok(resposta);

    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping(value = "/criarConta", consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity criarConta(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail((String) params.get("email"));
        funcionario.setPassword((String) params.get("senha"));
        funcionario.setNome((String) params.get("nome"));
        funcionario.setIdCargo((Integer) params.get("cargo"));

        FuncionarioDao dao = new FuncionarioDao();
        dao.save(funcionario);

        return ResponseEntity.ok(resposta);

    }

}
