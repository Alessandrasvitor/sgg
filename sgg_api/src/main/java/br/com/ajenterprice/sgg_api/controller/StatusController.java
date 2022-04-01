package br.com.ajenterprice.sgg_api.controller;

import br.com.ajenterprice.sgg_api.util.DataUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class StatusController {

    @GetMapping("status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Online em " + DataUtil.obterDataAtual());
    }

}
