package br.com.ajenterprice.sgg_api.controller;

import br.com.ajenterprice.sgg_api.entity.Curso;
import br.com.ajenterprice.sgg_api.service.CursoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/curso")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping()
    public ResponseEntity listar() {
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("{id}")
    public ResponseEntity buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cursoService.buscar(id));
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody Curso curso) {
        try {
            cursoService.salvar(curso);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody Curso curso) {
        try {
            cursoService.alterar(id, curso);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        try {
            cursoService.remover(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

}
