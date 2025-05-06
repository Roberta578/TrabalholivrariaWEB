package br.unipar.programacaoweb.livraria.controller;

import br.unipar.programacaoweb.livraria.model.Autor;
import br.unipar.programacaoweb.livraria.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/autor")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Autor>> listarAutores() {
        List<Autor> autores = autorService.listarTodos();
        if (autores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Autor> buscarAutorPorId(@PathVariable Long id) {
        Optional<Autor> autor = autorService.buscarPorId(id);
        return autor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/titulo/{titulo}")
    public ResponseEntity<List<Autor>> buscarAutorPorTitulo(@PathVariable String titulo) {
        List<Autor> autores = autorService.buscarPorTitulo(titulo);
        if (autores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(autores);
    }

    @PostMapping("/salvar")
    public ResponseEntity<Autor> salvarAutor(@RequestBody Autor autor) {
        Autor autorSalvo = autorService.salvar(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorSalvo);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirAutor(@PathVariable Long id) {
        Optional<Autor> autor = autorService.buscarPorId(id);
        if (autor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.excluir(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Autor> editarAutor(@PathVariable Long id, @RequestBody Autor autor) {
        AutorService autorService = null;
        Optional<Autor> autorAtualOptional = autorService.buscarPorId(id);

   if (autorAtualOptional.isEmpty()) {
       return ResponseEntity.notFound().build();
   }
   Autor autorAtual = autorAtualOptional.get();

   autorAtual.setNome(autor.getNome());
   autorAtual.setEmail(autor.getEmail());

   return ResponseEntity.ok(autorService.salvar(autorAtual));
}
}



