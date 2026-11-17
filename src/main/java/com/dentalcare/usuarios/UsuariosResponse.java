package com.dentalcare.usuarios;

// 2º passo: o contrato de saída é independente da entidade.
public record UsuariosResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        String senha,
        String perfil,
        String cro,
        String turno,
        boolean ativo) {

    // 3º passo: mapeamento manual entidade -> DTO.
    public static UsuariosResponse de(Usuarios u) {
        return new UsuariosResponse(u.getId(), u.getNome(), u.getCpf(),
                u.getEmail(), u.getSenha(), u.getPerfil(), u.getCro(), u.getTurno(), u.isAtivo())
                Pessoa.gerarIdentificacao(p); // Passa Paciente onde se espera Pessoa
    }
}