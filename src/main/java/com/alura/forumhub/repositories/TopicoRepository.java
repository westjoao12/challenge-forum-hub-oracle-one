package com.alura.forumhub.repositories;

import com.alura.forumhub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Verificação se já existe um tópico com o mesmo título e mesma mensagem
    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    // Filtração por nome do curso e ano (JPQL)
    @Query("""
            SELECT t FROM Topico t 
            WHERE t.curso.nome = :nomeCurso 
            AND YEAR(t.dataCriacao) = :ano
            """)
    Page<Topico> findAllByCursoNomeAndAno(String nomeCurso, Integer ano, Pageable paginacao);

    // Filtração apenas por nome do curso (Query Method)
    Page<Topico> findAllByCursoNome(String nomeCurso, Pageable paginacao);
}
