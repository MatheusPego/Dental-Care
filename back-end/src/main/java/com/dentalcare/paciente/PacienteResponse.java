package com.dentalcare.paciente;

import java.time.LocalDate;

// 2º passo: o contrato de saída é independente da entidade.
public record PacienteResponse(
        Long id,
        String nome,
        String cpf,
        String telefone,
        LocalDate dataNascimento,
        boolean ativo) {

    // 3º passo: mapeamento manual entidade -> DTO.
    public static PacienteResponse de(Paciente p) {
        return new PacienteResponse(p.getId(), p.getNome(), p.getCpf(),
                p.getTelefone(), p.getDataNascimento(), p.isAtivo());
    }
}