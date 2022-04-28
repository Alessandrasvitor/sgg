package br.com.ajenterprice.sgg_api.controller;

import br.com.ajenterprice.sgg_api.entity.Usuario;
import br.com.ajenterprice.sgg_api.entity.dto.UsuarioDTO;
import br.com.ajenterprice.sgg_api.exception.ServiceException;
import br.com.ajenterprice.sgg_api.service.UsuarioService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.security.auth.message.AuthException;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/usuario")
@OpenAPIDefinition(info = @Info(title = "Sistema de gestão de entreteinimento", version = "1.0", description = ""))
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity listar() {
        return ResponseEntity.ok(usuarioService.listar().stream().map(u ->
                new UsuarioDTO((Usuario) u)).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity buscar(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscar(id);
            return ResponseEntity.ok(new UsuarioDTO(usuario));
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody UsuarioDTO usuario) {
        try {
            usuarioService.salvar(usuario);
            return ResponseEntity.created(null).build();
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody UsuarioDTO u) {
        try {
            Usuario usuario = usuarioService.alterar(id, u);
            return ResponseEntity.ok(new UsuarioDTO(usuario));
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity logar(@RequestBody UsuarioDTO u) {
        try {
            return ResponseEntity.ok().body(usuarioService.logar(u));
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (AuthException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity alterarSenha(@PathVariable Long id, @RequestBody String senha) {
        try {
            usuarioService.alterarSenha(id, senha);
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
            usuarioService.remover(id);
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
