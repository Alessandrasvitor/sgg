package br.com.enterprice.sgg.controller;

import br.com.enterprice.sgg.util.CriptografiaUtil;
import br.com.enterprice.sgg.util.GeradorAleatorio;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class StatusController {

    @GetMapping("status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Online em " + new Date());
    }

    @GetMapping("status/{senha}")
    public ResponseEntity<Object> teste(@PathVariable String senha) {
        return ResponseEntity.ok(CriptografiaUtil.criptografarSHA256(senha));
    }
}
