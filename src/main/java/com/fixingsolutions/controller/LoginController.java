package com.fixingsolutions.controller;

import com.fixingsolutions.bean.FuncionarioDao;
import com.fixingsolutions.domain.AjaxResponseBody;
import com.fixingsolutions.domain.Funcionario;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class LoginController {

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping(value = "/login",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String,Object> params){

        AjaxResponseBody result = new AjaxResponseBody();
        String email = (String) params.get("email");
        String password = (String) params.get("senha");

        FuncionarioDao dao = new FuncionarioDao();

        Funcionario funcionario = dao.findByEmailSenha(email,password);
        if(funcionario.getId() != null){
            funcionario.setLogado(true);
            dao.update(funcionario);
        }

        return ResponseEntity.ok(result);

    }

}
