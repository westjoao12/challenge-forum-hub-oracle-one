package com.alura.forumhub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroTopicoDTO(
        @NotBlank(message = "Título é obrigatório")
        String titulo,

        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem,

        @NotNull(message = "ID do autor é obrigatório")
        Long idAutor,

        @NotNull(message = "ID do curso é obrigatório")
        Long idCurso
) {
}
