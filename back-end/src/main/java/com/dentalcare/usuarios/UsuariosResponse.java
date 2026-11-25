package com.dentalcare.usuarios;

public record UsuariosResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        String perfil,
        String cro,
        String turno,
        boolean ativo) {

    public static UsuariosResponse de(Usuarios u) {
        return new UsuariosResponse(
                u.getId(),
                u.getNome(),
                u.getCpf(),
                u.getEmail(),
                u.getPerfil(),
                u.getCro(),
                u.getTurno(),
                u.isAtivo());
    }
}