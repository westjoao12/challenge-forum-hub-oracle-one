package com.alura.forumhub.controller;

import com.alura.forumhub.domain.topico.DadosAtualizacaoTopicoDTO;
import com.alura.forumhub.domain.topico.DadosCadastroTopicoDTO;
import com.alura.forumhub.domain.topico.DadosListagemTopicoDTO;
import com.alura.forumhub.service.TopicoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@Tag(name = "4. Tópicos")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopicoDTO dados, UriComponentsBuilder uriBuilder) {
        return service.cadastrar(dados, uriBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopicoDTO>> listar(
            @RequestParam(required = false) String nomeCurso,
            @RequestParam(required = false) Integer ano,
            @PageableDefault(size = 10, sort = "dataCriacao", direction = org.springframework.data.domain.Sort.Direction.ASC) Pageable paginacao)
    {
        return service.listar(nomeCurso, ano, paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        return service.detalhar(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopicoDTO dados) {
        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        return service.excluir(id);
    }
}
