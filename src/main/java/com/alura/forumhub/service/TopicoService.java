package com.alura.forumhub.service;

import com.alura.forumhub.domain.topico.*;
import com.alura.forumhub.repositories.CursoRepository;
import com.alura.forumhub.repositories.TopicoRepository;
import com.alura.forumhub.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
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

    public ResponseEntity<Page<DadosListagemTopico>> listar(
            String nomeCurso,
            Integer ano,
            @PageableDefault(size = 10, sort = "dataCriacao", direction = org.springframework.data.domain.Sort.Direction.ASC) Pageable paginacao
    ){
        Page<Topico> pagina;

        if (nomeCurso != null && ano != null) {
            pagina = topicoRepository.findAllByCursoNomeAndAno(nomeCurso, ano, paginacao);
        } else if (nomeCurso != null) {
            pagina = topicoRepository.findAllByCursoNome(nomeCurso, paginacao);
        } else {
            pagina = topicoRepository.findAll(paginacao);
        }

        return ResponseEntity.ok(pagina.map(DadosListagemTopico::new));
    }

    public ResponseEntity detalhar(Long id){
        var topico = topicoRepository.findById(id);

        if (topico.isPresent())
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico.get()));
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity atualizar(Long id, @RequestBody @Valid DadosAtualizacaoTopico dados){
        var topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isEmpty())
            return ResponseEntity.notFound().build();

        var topico = topicoOptional.get();

        // Regra de Negócio: Não permitir atualizar para um título/mensagem que já existe (duplicidade)
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem()))
            return ResponseEntity.badRequest().body("Já existe um tópico com este título e mensagem.");

        topico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    public ResponseEntity excluir(Long id){
        var topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
