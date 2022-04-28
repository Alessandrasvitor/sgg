package br.com.ajenterprice.sgg_api.service;

import br.com.ajenterprice.sgg_api.entity.Capitulo;
import br.com.ajenterprice.sgg_api.entity.Livro;
import br.com.ajenterprice.sgg_api.entity.dto.CapituloDTO;
import br.com.ajenterprice.sgg_api.exception.ServiceException;
import br.com.ajenterprice.sgg_api.repository.LivroRepository;
import br.com.ajenterprice.sgg_api.repository.UsuarioRepository;
import br.com.ajenterprice.sgg_api.util.GeralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List listar() {
        return livroRepository.findAll();
    }

    public void remover(Long id) {
        Livro livro = buscar(id);
        livroRepository.delete(livro);
    }

    public Livro buscar(Long id) {
        Optional<Livro> livroOp = livroRepository.findById(id);
        if(livroOp.isEmpty()) {
            throw new NotFoundException("Livro não encontrado!");
        }
        return livroOp.get();
    }

    public Livro buscar(String nome) {
        Livro livro = livroRepository.findByNome(nome);
        if(livro == null) {
            throw new NotFoundException("Livro não encontrado!");
        }
        return livro;
    }

    public void salvar(Livro livro) {
        validarLivro(livro);
        livro.setDataCriacao(new Date());
        validarUsuario(livro);
        validarNomeExistente(livro);
        livroRepository.save(livro);
    }

    private void validarUsuario(Livro livro) {
        if(livro.getIdUsuario() == null) {
            livro.setUsuario(usuarioRepository.findByEmail("email@email.com"));
        } else {
            livro.setUsuario(usuarioRepository.getById(livro.getIdUsuario()));
        }
    }

    private void validarLivro(Livro livro) {

        if(GeralUtil.stringNullOrEmpty(livro.getNome()) || livro.getGenero() == null) {
            throw new ServiceException("Livro com informações incompletas!");
        }
    }

    public void alterar(Long id, Livro livro) {
        validarLivro(livro);
        Livro livroNovo = buscar(id);
        livroNovo.setNome(livro.getNome());
        livroNovo.setGenero(livro.getGenero());
        livroNovo.setSubNome(livro.getSubNome());
        validarNomeExistente(livroNovo);

        livroRepository.save(livroNovo);
    }

    private void validarNomeExistente(Livro livro) {

        List<Livro> livros = listar();

        Livro livroExistente = livros.stream().filter(l -> l.getNome().equals(livro.getNome())).findFirst().orElse(null);

        if(livroExistente != null &&
                livro.getId() != livroExistente.getId() &&
                (GeralUtil.stringNullOrEmpty(livro.getSubNome())
                        || livro.getSubNome().equals(livroExistente.getSubNome()))) {
            throw new ServiceException("Livro Já existe na base, tente alterar o subnome para salvar!");
        }
    }

    public Livro adicionarCapitulo(CapituloDTO capitulo) {

        Livro livro = buscar(capitulo.getLivro());
        validaCapitulo(capitulo);

        Capitulo capituloNovo = new Capitulo();
        capituloNovo.setLivro(livro);
        capituloNovo.setOrdem(capitulo.getOrdem());
        capituloNovo.setCapitulo(capitulo.getCapitulo());
        capituloNovo.setTitulo(capitulo.getTitulo());
        capituloNovo.setDataCriacao(new Date());

        livro.getCapitulos().add(capituloNovo);

        livroRepository.save(livro);
        return livro;

    }

    private void validaCapitulo(CapituloDTO capitulo) {

        if(GeralUtil.stringNullOrEmpty(capitulo.getTitulo()) || capitulo.getOrdem() == null
                || GeralUtil.stringNullOrEmpty(capitulo.getCapitulo()) ) {
            throw new ServiceException("Capítulo com informações incompletas!");
        }
    }

}
