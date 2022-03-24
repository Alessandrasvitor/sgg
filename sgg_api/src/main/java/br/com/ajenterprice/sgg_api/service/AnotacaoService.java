package br.com.ajenterprice.sgg_api.service;

import br.com.ajenterprice.sgg_api.entity.Anotacao;
import br.com.ajenterprice.sgg_api.entity.dto.AnotacaoDTO;
import br.com.ajenterprice.sgg_api.exception.ServiceException;
import br.com.ajenterprice.sgg_api.repository.AnotacaoRepository;
import br.com.ajenterprice.sgg_api.repository.UsuarioRepository;
import br.com.ajenterprice.sgg_api.util.GeralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnotacaoService {

    @Autowired
    private AnotacaoRepository anotacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void salvar(AnotacaoDTO anotacao) {

        validarCampos(anotacao);

        Anotacao anotacaoNova = new Anotacao();
        anotacaoNova.setTitulo(anotacao.getTitulo());
        anotacaoNova.setDescricao(anotacao.getDescricao());
        anotacaoNova.setDataVencimento(anotacao.getDataVencimento());
        anotacaoNova.setDataCriacao(new Date());
        anotacaoNova.setTipoAnotacao(anotacao.getTipoAnotacao());

        validarUsuario(anotacaoNova);

        anotacaoRepository.save(anotacaoNova);
    }

    private void validarUsuario(Anotacao anotacao) {
        if(anotacao.getUsuario() == null || anotacao.getUsuario().getId() == null) {
            anotacao.setUsuario(usuarioRepository.findByEmail("email@email.com"));
        }
    }

    public List listar() {
        return anotacaoRepository.findAll();
    }

    public Anotacao buscar(Long id) {
        Optional<Anotacao> AnotacaoOp = anotacaoRepository.findById(id);
        if(AnotacaoOp.isEmpty()) {
            throw new ServiceException("Anotação não encontrada!");
        }
        return AnotacaoOp.get();
    }

    public Anotacao alterar(Long id, AnotacaoDTO anotacao) {

        validarCampos(anotacao);

        Anotacao anotacaoNova = buscar(id);
        anotacaoNova.setTitulo(anotacao.getTitulo());
        anotacaoNova.setDescricao(anotacao.getDescricao());
        anotacaoNova.setDataVencimento(anotacao.getDataVencimento());
        anotacaoNova.setTipoAnotacao(anotacao.getTipoAnotacao());
        anotacaoRepository.save(anotacaoNova);

        return anotacaoNova;
    }

    public void remover(Long id) {
        Anotacao anotacao = buscar(id);
        anotacaoRepository.delete(anotacao);
    }

    private void validarCampos(AnotacaoDTO anotacao) {

        if(GeralUtil.stringNullOrEmpty(anotacao.getTitulo())) {
            throw new ServiceException("Títilo é obrigatório!");
        }

        if(anotacao.getTipoAnotacao() == null) {
            throw new ServiceException("Tipo de anotação é obrigatório!");
        }

    }

}
