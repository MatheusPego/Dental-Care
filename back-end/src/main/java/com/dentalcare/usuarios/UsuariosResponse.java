package com.dentalcare.usuarios;

import com.dentalcare.compartilhado.Pessoa;

// 2º passo: o contrato de saída é independente da entidade.
public record UsuariosResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        String perfil,
        String cro,
        String turno,
        boolean ativo,
        String identificacao) {

    // 3º passo: mapeamento manual entidade -> DTO.
    public static UsuariosResponse de(Usuarios u) {
        return new UsuariosResponse(u.getId(),
                u.getNome(),
                u.getCpf(),
                u.getEmail(),
                u.getPerfil(),
                u.getCro(),
                u.getTurno(),
                u.isAtivo(),
                Pessoa.gerarIdentificacao(u) // Passa Usuarios onde se espera Pessoa
        );
    }
}