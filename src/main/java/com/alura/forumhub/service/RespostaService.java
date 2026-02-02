package com.alura.forumhub.service;

import com.alura.forumhub.domain.resposta.DadosCadastroResposta;
import com.alura.forumhub.domain.resposta.DadosDetalhamentoResposta;
import com.alura.forumhub.domain.resposta.Resposta;
import com.alura.forumhub.repositories.RespostaRepository;
import com.alura.forumhub.repositories.TopicoRepository;
import com.alura.forumhub.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RespostaService {
    @Autowired
    private RespostaRepository repository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity cadastrar(@Valid DadosCadastroResposta dados, UriComponentsBuilder uriBuilder){
        var topico = topicoRepository.findById(dados.idTopico())
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));
        var autor = usuarioRepository.findById(dados.idAutor())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var resposta = new Resposta(dados, topico, autor);
        repository.save(resposta);

        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }
}
