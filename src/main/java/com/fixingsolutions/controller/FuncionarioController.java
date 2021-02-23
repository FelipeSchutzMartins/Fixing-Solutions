package com.fixingsolutions.controller;

import com.fixingsolutions.bean.FuncionarioDao;
import com.fixingsolutions.domain.AjaxResponseBody;
import com.fixingsolutions.domain.Cargo;
import com.fixingsolutions.domain.Funcionario;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.fixingsolutions.bean.CargoDao;

@RestController
public class FuncionarioController {

    @GetMapping("/buscarCargos")
    public ResponseEntity<?> buscarCargos(){

        AjaxResponseBody resposta = new AjaxResponseBody();
        CargoDao dao = new CargoDao();
        try {
            List<Cargo> cargos = dao.getAll();
            if (cargos.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Cargos não encontrados");
            }

            resposta.setResult(cargos);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @PostMapping(value = "/criarConta",consumes = {MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> criarConta(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();
        Funcionario funcionario = new Funcionario();
        try {

            String email = (String) params.get("email");
            if(email == null || email.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Email inválido");
            }

            String senha = (String) params.get("senha");

            if(senha == null || senha.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Senha inválido");
            }
            String nome = (String) params.get("nome");
            if(nome == null || nome.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Nome inválido");
            }
            Integer idCargo = (Integer) params.get("cargo");
            if(idCargo == null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Cargo inválido");
            }

            CargoDao cargoDao = new CargoDao();
            Cargo cargo = cargoDao.get(idCargo);

            funcionario.setEmail(email);
            funcionario.setSenha(senha);
            funcionario.setNome(nome);
            funcionario.setCargo(cargo);

            FuncionarioDao dao = new FuncionarioDao();
            dao.save(funcionario);

        }catch (Exception e){
            System.out.println("Houve um problema a criar conta || e: "+e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @GetMapping("/buscarFuncionario")
    public ResponseEntity<?> buscarFuncionario(){

        AjaxResponseBody resposta = new AjaxResponseBody();
        FuncionarioDao dao = new FuncionarioDao();
        try {
            List<Funcionario> funcionarios = dao.getAll();
            if (funcionarios.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Cargos não encontrados");
            }
            resposta.setResult(funcionarios);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @DeleteMapping(value = "/deletarFuncionario",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deletarFuncionario(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try {

            Integer id = (Integer) params.get("id");


            FuncionarioDao dao = new  FuncionarioDao();
            dao.delete(id);


        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

    @PutMapping(value = "/editarFuncionario",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editarCliente(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();
        Funcionario funcionario = new Funcionario();

        try {

            Integer id = (Integer) params.get("id");

            String email = (String) params.get("email");
            if(email == null || email.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Email inválido");
            }

            String senha = (String) params.get("senha");

            if(senha == null || senha.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Senha inválido");
            }
            String nome = (String) params.get("nome");
            if(nome == null || nome.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Nome inválido");
            }
            Integer idCargo = (Integer) params.get("cargo");
            if(idCargo == null){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Cargo inválido");
            }

            CargoDao cargoDao = new CargoDao();
            Cargo cargo = cargoDao.get(idCargo);

            funcionario.setEmail(email);
            funcionario.setSenha(senha);
            funcionario.setNome(nome);
            funcionario.setCargo(cargo);
            funcionario.setId(id);

            FuncionarioDao dao = new FuncionarioDao();
            dao.update(funcionario);


        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");
        }
        return ResponseEntity.ok(resposta);

    }

}
