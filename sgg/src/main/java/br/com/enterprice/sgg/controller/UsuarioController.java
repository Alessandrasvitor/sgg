package br.com.enterprice.sgg.controller;

import br.com.enterprice.sgg.entity.Usuario;
import br.com.enterprice.sgg.entity.dto.UsuarioDTO;
import br.com.enterprice.sgg.services.UsuarioService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
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
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody UsuarioDTO usuario) {
        try {
            usuarioService.salvar(usuario);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody UsuarioDTO u) {
        try {
            Usuario usuario = usuarioService.alterar(id, u);
            return ResponseEntity.ok(new UsuarioDTO(usuario));
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity logar(@RequestBody UsuarioDTO u) {
        try {
            usuarioService.logar(u);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity alterarSenha(@PathVariable Long id, @RequestBody String senha) {
        try {
            usuarioService.alterarSenha(id, senha);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        try {
            usuarioService.remover(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

}
