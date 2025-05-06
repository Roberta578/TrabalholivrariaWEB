package br.unipar.programacaoweb.livraria.service;

import br.unipar.programacaoweb.livraria.model.Autor;
import br.unipar.programacaoweb.livraria.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    // Listar todos os autores
    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    // Buscar autor por ID
    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    // Salvar novo autor
    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    // Excluir autor
    public void excluir(Long id) {
        autorRepository.deleteById(id);
    }

    public List<Autor> buscarPorTitulo(String titulo) {
        return List.of();
    }
}
