package com.alura.forumhub.service;

import com.alura.forumhub.domain.usuario.DadosCadastroUsuarioDTO;
import com.alura.forumhub.domain.usuario.DadosListagemUsuarioDTO;
import com.alura.forumhub.domain.usuario.Usuario;
import com.alura.forumhub.infra.exception.DadosErroDefault;
import com.alura.forumhub.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity cadastrar(@Valid DadosCadastroUsuarioDTO dados, UriComponentsBuilder uriBuilder, jakarta.servlet.http.HttpServletRequest request){
        // Validação: E-mail duplicado
        if(repository.existsByEmail(dados.email())){
            var erro = new DadosErroDefault(
                    400,
                    "Bad Request",
                    "Já existe um usuário cadastrado com este e-mail",
                    request.getRequestURI()

            );
            return ResponseEntity.badRequest().body(erro);
        }

        var senhaCriptografada = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(null, dados.nome(), dados.email(), senhaCriptografada);
        repository.save(usuario);

        var uri = uriBuilder.path("usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemUsuarioDTO(usuario));
    }

    public ResponseEntity<Page<DadosListagemUsuarioDTO>> listar(@PageableDefault(size = 10, sort = "nome") Pageable paginacao){
        var pagina = repository.findAll(paginacao).map(DadosListagemUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }
}
