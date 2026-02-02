package com.alura.forumhub.repositories;

import com.alura.forumhub.domain.resposta.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
}
