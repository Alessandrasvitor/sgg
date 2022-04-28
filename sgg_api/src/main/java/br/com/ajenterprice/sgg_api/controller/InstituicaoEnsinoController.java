package br.com.ajenterprice.sgg_api.controller;

import br.com.ajenterprice.sgg_api.entity.InstituicaoEnsino;
import br.com.ajenterprice.sgg_api.exception.ServiceException;
import br.com.ajenterprice.sgg_api.service.InstituicaoEnsinoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@CrossOrigin
@RestController
@RequestMapping("/instituicaoEnsino")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class InstituicaoEnsinoController {

    @Autowired
    private InstituicaoEnsinoService instituicaoEnsinoService;

    @GetMapping()
    public ResponseEntity listar() {
        return ResponseEntity.ok(instituicaoEnsinoService.listar());
    }

    @GetMapping("{id}")
    public ResponseEntity buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(instituicaoEnsinoService.buscar(id));
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody InstituicaoEnsino instituicaoEnsino) {
        try {
            instituicaoEnsinoService.salvar(instituicaoEnsino);
            return ResponseEntity.created(null).build();
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody InstituicaoEnsino instituicaoEnsino) {
        try {
            instituicaoEnsinoService.alterar(id, instituicaoEnsino);
            return ResponseEntity.ok().build();
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        try {
            instituicaoEnsinoService.remover(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

}
