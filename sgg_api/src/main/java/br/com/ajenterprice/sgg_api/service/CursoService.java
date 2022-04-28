package br.com.ajenterprice.sgg_api.service;

import br.com.ajenterprice.sgg_api.constant.StatusEnum;
import br.com.ajenterprice.sgg_api.entity.Curso;
import br.com.ajenterprice.sgg_api.entity.InstituicaoEnsino;
import br.com.ajenterprice.sgg_api.exception.ServiceException;
import br.com.ajenterprice.sgg_api.repository.CursoRepository;
import br.com.ajenterprice.sgg_api.repository.InstituicaoEnsinoRepository;
import br.com.ajenterprice.sgg_api.repository.UsuarioRepository;
import br.com.ajenterprice.sgg_api.util.DataUtil;
import br.com.ajenterprice.sgg_api.util.GeralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InstituicaoEnsinoRepository instituicaoEnsinoRepository;

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
            throw new NotFoundException("Curso não encontrado!");
        }
        return cursoOp.get();
    }

    public Curso buscar(String nome) {
        Curso curso = cursoRepository.findByNome(nome);
        if(curso == null) {
            throw new NotFoundException("Curso não encontrado!");
        }
        return curso;
    }

    public void salvar(Curso curso) {
        validarCurso(curso);
        curso.setDataCriacao(new Date());
        curso.setStatus(StatusEnum.PENDENTE);
        curso.setInstituicaoEnsino(buscarInstituicao(curso.getIdInstituicao()));
        validarNomeExistente(curso);
        cursoRepository.save(curso);
    }

    private void validarCurso(Curso curso) {

        if(GeralUtil.stringNullOrEmpty(curso.getNome())) {
            throw new ServiceException("O nome do curso é obrigatório!");
        }

        if(curso.getIdInstituicao() == null) {
            throw new ServiceException("A Instituição de ensino é obrigatória!");
        }

        if(curso.getIdUsuario() == null) {
            curso.setUsuario(usuarioRepository.findByEmail("email@email.com"));
        } else {
            curso.setUsuario(usuarioRepository.getById(curso.getIdUsuario()));
        }
    }

    public void alterar(Long id, Curso curso) {
        validarCurso(curso);
        Curso cursoNovo = buscar(id);
        cursoNovo.setNome(curso.getNome());
        cursoNovo.setDescricao(curso.getDescricao());
        validarNomeExistente(cursoNovo);
        if(curso.getInstituicaoEnsino().getId() != curso.getIdInstituicao()) {
            curso.setInstituicaoEnsino(buscarInstituicao(curso.getIdInstituicao()));
        }
        cursoNovo.setInstituicaoEnsino(curso.getInstituicaoEnsino());

        cursoRepository.save(cursoNovo);
    }

    private InstituicaoEnsino buscarInstituicao(Long id) {
        return instituicaoEnsinoRepository.findById(id).get();
    }

    private void validarNomeExistente(Curso curso) {

        List<Curso> cursos = listar();

        Curso cur = cursoRepository.findByNome(curso.getNome());

        Curso cursoExistente = cursos.stream().filter(l -> l.getNome().equals(curso.getNome())).findFirst().orElse(null);

        if(cur != null &&
                curso.getId() != cursoExistente.getId() &&
                (GeralUtil.stringNullOrEmpty(curso.getNome())
                        || curso.getNome().equals(cursoExistente.getNome()))) {
            throw new ServiceException("Curso Já existe na base!");
        }
    }

    public void iniciar(Long id) {
        Curso curso = buscar(id);
        if( StatusEnum.PENDENTE.equals(curso.getStatus())){
            curso.setStatus(StatusEnum.INICIADO);
        } else {
            curso.setStatus(StatusEnum.REINICIADO);
        }
        curso.setDataInicio(DataUtil.obterDataAtual());

        cursoRepository.save(curso);
    }

    public void finalizar(Long id, float nota) {
        Curso curso = buscar(id);

        if(nota < 7) {
            curso.setStatus(StatusEnum.REPROVADO);
        } else {
            curso.setStatus(StatusEnum.CONCLUIDO);
        }
        curso.setNota(nota);
        curso.setDataFim(DataUtil.obterDataAtual());
        curso.setFinalizado(Boolean.TRUE);

        cursoRepository.save(curso);
    }
}
