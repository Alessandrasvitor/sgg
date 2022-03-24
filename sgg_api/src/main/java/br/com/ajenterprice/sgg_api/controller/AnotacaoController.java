package br.com.ajenterprice.sgg_api.controller;

import br.com.ajenterprice.sgg_api.entity.dto.AnotacaoDTO;
import br.com.ajenterprice.sgg_api.service.AnotacaoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anotacao")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class AnotacaoController {

    @Autowired
    private AnotacaoService anotacaoService;

    @GetMapping()
    public ResponseEntity listar() {
        return ResponseEntity.ok(anotacaoService.listar());
    }

    @GetMapping("{id}")
    public ResponseEntity buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(anotacaoService.buscar(id));
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody AnotacaoDTO anotacao) {
        try {
            anotacaoService.salvar(anotacao);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody AnotacaoDTO anotacao) {
        try {
            return ResponseEntity.ok(anotacaoService.alterar(id, anotacao));
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        try {
            anotacaoService.remover(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

}
