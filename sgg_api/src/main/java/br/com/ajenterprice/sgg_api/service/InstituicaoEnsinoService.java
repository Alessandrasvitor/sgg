package br.com.ajenterprice.sgg_api.service;

import br.com.ajenterprice.sgg_api.entity.InstituicaoEnsino;
import br.com.ajenterprice.sgg_api.exception.ServiceException;
import br.com.ajenterprice.sgg_api.repository.InstituicaoEnsinoRepository;
import br.com.ajenterprice.sgg_api.repository.UsuarioRepository;
import br.com.ajenterprice.sgg_api.util.GeralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoEnsinoService {

    @Autowired
    private InstituicaoEnsinoRepository instituicaoEnsinoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List listar() {
        return instituicaoEnsinoRepository.findAll();
    }

    public void remover(Long id) {
        InstituicaoEnsino instituicaoEnsino = buscar(id);
        instituicaoEnsinoRepository.delete(instituicaoEnsino);
    }

    public InstituicaoEnsino buscar(Long id) {
        Optional<InstituicaoEnsino> instituicaoEnsinoOp = instituicaoEnsinoRepository.findById(id);
        if(instituicaoEnsinoOp.isEmpty()) {
            throw new NotFoundException("InstituicaoEnsino não encontrado!");
        }
        return instituicaoEnsinoOp.get();
    }

    public InstituicaoEnsino buscar(String nome) {
        InstituicaoEnsino instituicaoEnsino = instituicaoEnsinoRepository.findByNome(nome);
        if(instituicaoEnsino == null) {
            throw new NotFoundException("InstituicaoEnsino não encontrado!");
        }
        return instituicaoEnsino;
    }

    public void salvar(InstituicaoEnsino instituicaoEnsino) {
        validarInstituicaoEnsino(instituicaoEnsino);
        instituicaoEnsino.setDataCriacao(new Date());
        validarNomeExistente(instituicaoEnsino);
        instituicaoEnsinoRepository.save(instituicaoEnsino);
    }

    private void validarInstituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {

        if(GeralUtil.stringNullOrEmpty(instituicaoEnsino.getNome())) {
            throw new ServiceException("O nome do instituicaoEnsino é obrigatório!");
        }

        if(instituicaoEnsino.getEndereco() == null) {
            throw new ServiceException("Endereço é obrigatório!");
        }
    }

    public void alterar(Long id, InstituicaoEnsino instituicaoEnsino) {
        validarInstituicaoEnsino(instituicaoEnsino);
        InstituicaoEnsino instituicaoEnsinoNovo = buscar(id);
        instituicaoEnsinoNovo.setNome(instituicaoEnsino.getNome());
        instituicaoEnsinoNovo.setEndereco(instituicaoEnsino.getEndereco());
        instituicaoEnsinoNovo.setAvaliacao(instituicaoEnsino.getAvaliacao());
        validarNomeExistente(instituicaoEnsinoNovo);

        instituicaoEnsinoRepository.save(instituicaoEnsinoNovo);
    }

    private void validarNomeExistente(InstituicaoEnsino instituicaoEnsino) {

        List<InstituicaoEnsino> instituicaoEnsinos = listar();

        InstituicaoEnsino cur = instituicaoEnsinoRepository.findByNome(instituicaoEnsino.getNome());

        InstituicaoEnsino instituicaoEnsinoExistente = instituicaoEnsinos.stream().filter(l -> l.getNome().equals(instituicaoEnsino.getNome())).findFirst().orElse(null);

        if(cur != null &&
                instituicaoEnsino.getId() != instituicaoEnsinoExistente.getId() &&
                (GeralUtil.stringNullOrEmpty(instituicaoEnsino.getNome())
                        || instituicaoEnsino.getNome().equals(instituicaoEnsinoExistente.getNome()))) {
            throw new ServiceException("InstituicaoEnsino Já existe na base!");
        }
    }

}
