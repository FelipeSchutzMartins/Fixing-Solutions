package com.fixingsolutions.controller;

import com.fixingsolutions.bean.OsDao;
import com.fixingsolutions.domain.AjaxResponseBody;
import com.fixingsolutions.domain.Os;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class RelatorioController {

    @PostMapping(value ="/relatorio",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> relatorio(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try{

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("WebContent/static/Relatorio.pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Hello World", font);

            document.add(chunk);
            document.close();

            resposta.setResult(Collections.singletonList(""));

        }catch (Exception e){

            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");

        }

        return ResponseEntity.ok(resposta);

    }

}
