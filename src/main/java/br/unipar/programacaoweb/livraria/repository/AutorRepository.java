package br.unipar.programacaoweb.livraria.repository;

import br.unipar.programacaoweb.livraria.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Autor a JOIN a.livros l WHERE a.nome LIKE %:nome% GROUP BY a.id HAVING COUNT(l) >= :numeroLivros")
    List<Autor> findAutoresByNomeAndNumeroLivros(@Param("nome") String nome,
                                                 @Param("numeroLivros") long numeroLivros);
}

