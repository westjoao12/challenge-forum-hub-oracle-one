package com.alura.forumhub.service;

import com.alura.forumhub.domain.usuario.DadosCadastroUsuario;
import com.alura.forumhub.domain.usuario.DadosListagemUsuario;
import com.alura.forumhub.domain.usuario.Usuario;
import com.alura.forumhub.infra.exception.DadosErroDefault;
import com.alura.forumhub.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder, jakarta.servlet.http.HttpServletRequest request){
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
        return ResponseEntity.created(uri).body(new DadosListagemUsuario(usuario));
    }
}
