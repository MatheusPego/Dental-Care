package com.dentalcare.consulta;

import java.time.LocalDateTime;

public record ConsultaResponse(
        Long id,
        Long pacienteId,
        String pacienteNome,
        Long dentistaId,
        String dentistaNome,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        Consulta.Status status) {

    public static ConsultaResponse de(Consulta c) {
        return new ConsultaResponse(
                c.getId(),
                c.getPaciente().getId(),
                c.getPaciente().getNome(),
                c.getDentista().getId(),
                c.getDentista().getNome(),
                c.getDataHoraInicio(),
                c.getDataHoraFim(),
                c.getStatus());
    }
}