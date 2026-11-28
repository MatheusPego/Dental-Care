package com.dentalcare.auth;

import com.dentalcare.usuarios.Usuarios;

public record LoginResponse(
        Long id,
        String nome,
        String email,
        String perfil,
        String token) {

    public static LoginResponse de(Usuarios u, String token) {
       return new LoginResponse(
               u.getId(),
               u.getNome(),
               u.getEmail(),
               u.getPerfil(),
               token
        );
    }
}
