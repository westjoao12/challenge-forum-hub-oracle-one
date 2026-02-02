package com.alura.forumhub.controller;

import com.alura.forumhub.domain.resposta.DadosCadastroRespostaDTO;
import com.alura.forumhub.service.RespostaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/respostas")
@Tag(name = "5. Respostas")
public class RespostaController {

    @Autowired
    private RespostaService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroRespostaDTO dados, UriComponentsBuilder uriBuilder) {
        return service.cadastrar(dados, uriBuilder);
    }

    @PutMapping("/{id}/solucao")
    @Transactional
    public ResponseEntity marcarSolucao(@PathVariable Long id) {
        return service.marcarSolucao(id);
    }
}
