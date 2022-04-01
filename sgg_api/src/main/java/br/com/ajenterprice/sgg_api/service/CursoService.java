package br.com.ajenterprice.sgg_api.service;

import br.com.ajenterprice.sgg_api.entity.Capitulo;
import br.com.ajenterprice.sgg_api.entity.Curso;
import br.com.ajenterprice.sgg_api.entity.dto.CapituloDTO;
import br.com.ajenterprice.sgg_api.exception.ServiceException;
import br.com.ajenterprice.sgg_api.repository.CursoRepository;
import br.com.ajenterprice.sgg_api.repository.UsuarioRepository;
import br.com.ajenterprice.sgg_api.util.GeralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List listar() {
        return cursoRepository.findAll();
    }

    public void remover(Long id) {
        Curso curso = buscar(id);
        cursoRepository.delete(curso);
    }

    public Curso buscar(Long id) {
        Optional<Curso> cursoOp = cursoRepository.findById(id);
        if(cursoOp.isEmpty()) {
            throw new ServiceException("Curso não encontrado!");
        }
        return cursoOp.get();
    }

    public Curso buscar(String nome) {
        Curso curso = cursoRepository.findByNome(nome);
        if(curso == null) {
            throw new ServiceException("Curso não encontrado!");
        }
        return curso;
    }

    public void salvar(Curso curso) {
        validarCurso(curso);
        curso.setDataCriacao(new Date());
        validarUsuario(curso);
        validarNomeExistente(curso);
        cursoRepository.save(curso);
    }

    private void validarUsuario(Curso curso) {
        if(curso.getUsuario() == null) {
            curso.setUsuario(usuarioRepository.findByEmail("email@email.com"));
        }
    }

    private void validarCurso(Curso curso) {

        if(GeralUtil.stringNullOrEmpty(curso.getNome())) {
            throw new ServiceException("O nome do curso é obrigatório!");
        }

        if(curso.getInstituicaoEnsino() == null) {
            throw new ServiceException("A Instituição de ensino é obrigatória!");
        }
    }

    public void alterar(Long id, Curso curso) {
        validarCurso(curso);
        Curso cursoNovo = buscar(id);
        cursoNovo.setNome(curso.getNome());
        cursoNovo.setDescricao(curso.getDescricao());
        cursoNovo.setInstituicaoEnsino(curso.getInstituicaoEnsino());
        validarNomeExistente(cursoNovo);

        cursoRepository.save(cursoNovo);
    }

    private void validarNomeExistente(Curso curso) {

        List<Curso> cursos = listar();

        Curso cur = cursoRepository.findByNome(curso.getNome());

        Curso cursoExistente = cursos.stream().filter(l -> l.getNome().equals(curso.getNome())).findFirst().orElse(null);

        if(cur != null &&
                curso.getId() != cursoExistente.getId() &&
                (GeralUtil.stringNullOrEmpty(curso.getNome())
                        || curso.getNome().equals(cursoExistente.getNome()))) {
            throw new ServiceException("Curso Já existe na base, tente alterar o subnome para salvar!");
        }
    }

}
