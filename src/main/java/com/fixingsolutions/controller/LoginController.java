package com.fixingsolutions.controller;

import com.fixingsolutions.bean.FuncionarioDao;
import com.fixingsolutions.domain.AjaxResponseBody;
import com.fixingsolutions.domain.Funcionario;
import com.fixingsolutions.domain.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpSession;

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
            if(email == null || email.isEmpty() || !email.contains("@")){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Email inválido");
            }

            String senha = (String) params.get("senha");
            if(senha == null || senha.isEmpty()){
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
            System.out.println("Erro ao fazer login ||e:"+e);
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

        }

        return ResponseEntity.ok(result);

    }


}
