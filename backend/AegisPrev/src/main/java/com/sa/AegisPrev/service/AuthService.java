package com.sa.AegisPrev.service;

import com.sa.AegisPrev.DTO.AuthRequestDTO;
import com.sa.AegisPrev.DTO.AuthResponseDTO;
import com.sa.AegisPrev.exception.RecursoNaoEncontradoException;
import com.sa.AegisPrev.models.Usuario;
import com.sa.AegisPrev.repository.UsuarioRepository;
import com.sa.AegisPrev.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        String token = jwtService.gerarToken(usuario);
        return new AuthResponseDTO(token);
    }
}
