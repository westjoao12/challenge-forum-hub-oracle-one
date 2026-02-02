package com.alura.forumhub.controller;

import com.alura.forumhub.domain.usuario.DadosCadastroUsuario;
import com.alura.forumhub.domain.usuario.DadosListagemUsuario;
import com.alura.forumhub.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "3. Usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder, jakarta.servlet.http.HttpServletRequest request) {
        return service.cadastrar(dados, uriBuilder, request);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuario>> listar(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        return service.listar(paginacao);
    }
}
