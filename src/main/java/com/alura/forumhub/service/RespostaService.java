package com.alura.forumhub.service;

import com.alura.forumhub.repositories.RespostaRepository;
import com.alura.forumhub.repositories.TopicoRepository;
import com.alura.forumhub.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespostaService {
    @Autowired
    private RespostaRepository repository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
}
