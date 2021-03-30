package com.fixingsolutions.controller;

import com.fixingsolutions.bean.FuncionarioDao;
import com.fixingsolutions.domain.AjaxResponseBody;
import com.fixingsolutions.domain.Funcionario;
import com.fixingsolutions.domain.Token;
import com.fixingsolutions.service.InputValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

public class LoginController {

    @PostMapping(value = "/login",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String,Object> params){

        AjaxResponseBody result = new AjaxResponseBody();


        FuncionarioDao dao = new FuncionarioDao();
        try {

            String email = (String) params.get("email");
            if(!InputValidationService.isValidStringInput(email) || !email.contains("@") || !email.contains("mail")){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Email inválido");
            }

            String senha = (String) params.get("senha");
            if(!InputValidationService.isValidStringInput(senha)){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Senha inválida");
            }

            Funcionario funcionario = dao.findByEmailSenha(email,senha);
            if (funcionario.getId() == null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Usuário não encontrado");
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente nvoamente mais tarde");
        }

        return ResponseEntity.ok(result);

    }

    @GetMapping(value = "/verificarlogin")
    public ResponseEntity<?> verificarLogin(){

        AjaxResponseBody result = new AjaxResponseBody();

        try{

            Token token = FuncionarioDao.findToken();

            if(token.getId()==null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Login inválido");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(result);

    }


}
