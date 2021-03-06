package br.com.ajenterprice.sgg_api.controller;

import br.com.ajenterprice.sgg_api.entity.Livro;
import br.com.ajenterprice.sgg_api.entity.dto.CapituloDTO;
import br.com.ajenterprice.sgg_api.exception.ServiceException;
import br.com.ajenterprice.sgg_api.service.LivroService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@CrossOrigin
@RestController
@RequestMapping("/livro")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping()
    public ResponseEntity listar() {
        return ResponseEntity.ok(livroService.listar());
    }

    @GetMapping("{id}")
    public ResponseEntity buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(livroService.buscar(id));
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody Livro livro) {
        try {
            livroService.salvar(livro);
            return ResponseEntity.created(null).build();
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody Livro livro) {
        try {
            livroService.alterar(id, livro);
            return ResponseEntity.ok().build();
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PatchMapping()
    public ResponseEntity adicionarCapitulo(@RequestBody CapituloDTO capitulo) {
        try {
            livroService.adicionarCapitulo(capitulo);
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
            livroService.remover(id);
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
