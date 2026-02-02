package com.alura.forumhub.domain.usuario;

public record DadosListagemUsuarioDTO(Long id, String nome, String email) {
    public DadosListagemUsuarioDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
