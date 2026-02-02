package com.alura.forumhub.service;

import com.alura.forumhub.domain.topico.DadosCadastroTopico;
import com.alura.forumhub.domain.topico.DadosDetalhamentoTopico;
import com.alura.forumhub.domain.topico.StatusTopico;
import com.alura.forumhub.domain.topico.Topico;
import com.alura.forumhub.repositories.CursoRepository;
import com.alura.forumhub.repositories.TopicoRepository;
import com.alura.forumhub.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public ResponseEntity cadastrar(@Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder){
        // 1. Validação de Regra de Negócio: Duplicidade
        if(topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem()))
            return ResponseEntity.badRequest().body("Já Existe um tópico com este título e mensagem");

        // 2. Busca das entidades relacionadas (Autor e Curso)
        var autor = usuarioRepository.findById(dados.idAutor())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com o ID: " + dados.idAutor()));

        var curso = cursoRepository.findById(dados.idCurso())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com o ID: " + dados.idCurso()));

        // 3. Criação da entidade Topico
        var topico = new Topico(null, dados.titulo(), dados.mensagem(), LocalDateTime.now(), StatusTopico.NAO_RESPONDIDO, autor, curso);

        topicoRepository.save(topico);

        // 4. Retorna 201 Created e o DTO de detalhamento
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }
}
