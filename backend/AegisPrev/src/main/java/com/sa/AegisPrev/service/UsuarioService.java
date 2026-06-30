package com.sa.AegisPrev.service;

import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.Usuario;
import com.sa.AegisPrev.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioService {

    public static Usuario obterUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsuario();
        }

        throw new RecursoNaoEncontradoException("Usuário não autenticado");
    }
}
