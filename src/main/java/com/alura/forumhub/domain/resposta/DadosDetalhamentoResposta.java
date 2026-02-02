package com.alura.forumhub.domain.resposta;

public record DadosDetalhamentoResposta(Long id, String mensagem, Long idTopico, java.time.LocalDateTime dataCriacao, String autor, Boolean solucao) {
    public DadosDetalhamentoResposta(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getTopico().getId(), resposta.getDataCriacao(), resposta.getAutor().getNome(), resposta.getSolucao());
    }
}
