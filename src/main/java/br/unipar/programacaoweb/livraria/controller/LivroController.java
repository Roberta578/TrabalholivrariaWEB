package br.unipar.programacaoweb.livraria.controller;

import br.unipar.programacaoweb.livraria.model.Livro;
import br.unipar.programacaoweb.livraria.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {

    private LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = livroService.listarTodos();
        if(livros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livros);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long id) {
        Livro livro = livroService.buscarPorId(id);
        if(livro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(livro);
    }

    @GetMapping("/buscar/titulo/{titulo}")
    public ResponseEntity<List<Livro>> buscarLivrosPorTitulo(@PathVariable String titulo) {
        List<Livro> livros = livroService.buscarPorTitulo(titulo);
        if(livros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(livros);
    }

    @PostMapping("/salvar")
    public ResponseEntity<Livro> salvarLivro(@RequestBody Livro livro) {
        Livro livroSalvo = livroService.salvar(livro);

        return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirLivro(@PathVariable Long id) {
        Livro livro = livroService.buscarPorId(id);
        if(livro == null) {
            return ResponseEntity.notFound().build();
        }
        livroService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<Livro> editarLivro(@PathVariable Long id,
                                                @RequestBody Livro livro) {
        Livro livroAtual = livroService.buscarPorId(id);
        if(livroAtual == null) {
            return ResponseEntity.notFound().build();
        }

        livroAtual.setTitulo(livro.getTitulo());
        livroAtual.setNumeroPaginas(livro.getNumeroPaginas());
        livroAtual.setGenero(livro.getGenero());

        return ResponseEntity.ok(livroService.salvar(livroAtual));
    }

}
