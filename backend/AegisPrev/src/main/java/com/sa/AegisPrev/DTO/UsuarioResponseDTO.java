package com.sa.AegisPrev.DTO;

import com.sa.AegisPrev.models.Papel;

public record UsuarioResponseDTO(
        Long idUsuario,
        String email,
        Papel papeis
) {

}
