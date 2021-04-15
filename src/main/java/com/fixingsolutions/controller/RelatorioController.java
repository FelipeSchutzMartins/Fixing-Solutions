package com.fixingsolutions.controller;

import com.fixingsolutions.bean.OsDao;
import com.fixingsolutions.domain.AjaxResponseBody;
import com.fixingsolutions.domain.Os;
import com.fixingsolutions.service.InputValidationService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@RestController
public class RelatorioController {

    @PostMapping(value ="/relatorio",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> relatorio(@RequestBody Map<String,Object> params){

        AjaxResponseBody resposta = new AjaxResponseBody();

        try{

            String fileName = "Relatorio.pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("WebContent/static/"+fileName));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            if(!InputValidationService.isValidStringInput((String) params.get("dataFimPeriodo"))){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Data fim do periodo inválida");
            }

            if(!InputValidationService.isValidStringInput((String) params.get("dataIniPeriodo"))){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Data inicio do periodo inválida");
            }

            Date dataFin = formatter.parse((String) params.get("dataFimPeriodo"));
            Date dataIni = formatter.parse((String) params.get("dataIniPeriodo"));

            if(dataFin.before(dataIni)){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Data fim do periodo precisa ser maior do que a data de inicio");
            }

            List<Os> ordemServicos = OsDao.findWhereDateCriacaoBetween(dataIni,dataFin);

            PdfPTable table = new PdfPTable(6);
            table.addCell("Id");
            table.addCell("Status");
            table.addCell("Valor");
            table.addCell("Data de criação");
            table.addCell("Data da ultima atualização");
            table.addCell("Responsável");

            for(Os ordemServico : ordemServicos){

                table.addCell(ordemServico.getId().toString());
                table.addCell(ordemServico.getStatus().toString());
                table.addCell(ordemServico.getOrcamento().getValor().toString());
                table.addCell(ordemServico.getDataCriacao());
                table.addCell(ordemServico.getDataUltimaAtualizacao());
                table.addCell(ordemServico.getOrcamento().getFuncionario().getEmail().toString());

            }

            String pattern = "dd-MM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            document.add(new Paragraph("Relatório de ordem de serviços"));
            document.add(new Paragraph("Data de emissão: "+simpleDateFormat.format(new Date())));
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.close();

            List<Map> retorno = new ArrayList<Map>();
            retorno.add(Collections.singletonMap("fileName",fileName));

            resposta.setResult(retorno);

        }catch (Exception e){

            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um problema, tente novamente mais tarde");

        }

        return ResponseEntity.ok(resposta);

    }

}
