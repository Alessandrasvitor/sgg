package br.com.ajenterprice.sgg_api.controller;

import br.com.ajenterprice.sgg_api.entity.dto.UsuarioDTO;
import br.com.ajenterprice.sgg_api.util.CriptografiaUtil;
import br.com.ajenterprice.sgg_api.util.DataUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class StatusController {

    @GetMapping("status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Online em " + DataUtil.formataData(DataUtil.obterDataAtual(), "dd/MM/yy hh:mm:ss"));
    }

    @PutMapping("statusS")
    public ResponseEntity<String> senha(@RequestBody String s) {
        return ResponseEntity.ok(CriptografiaUtil.criptografarSHA256(s));
    }

}
