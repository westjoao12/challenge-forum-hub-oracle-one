package com.alura.forumhub.domain.topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopicoDTO(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, StatusTopico status, String autor, String curso) {
    public DadosDetalhamentoTopicoDTO(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(), topico.getAutor().getNome(), topico.getCurso().getNome());
    }
}
