package com.alura.forumhub.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoTopicoDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem
) {
}
