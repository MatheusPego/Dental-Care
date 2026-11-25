package com.dentalcare.prontuario;

// 2º passo: o contrato de saída é independente da entidade.
public record ProntuarioResponse(
        Long id,
        Long consultaId,
        Long pacienteId,
        String pacienteNome,
        Long dentistaId,
        String dentistaNome,
        String descricao) {

    // 3º passo: mapeamento manual entidade -> DTO.
    public static ProntuarioResponse de(Prontuario p) {
        return new ProntuarioResponse(p.getId(), p.getConsulta().getId(),
                p.getConsulta().getPaciente().getId(), p.getConsulta().getPaciente().getNome(),
                p.getConsulta().getDentista().getId(), p.getConsulta().getDentista().getNome(),
                p.getDescricao());
    }
}
