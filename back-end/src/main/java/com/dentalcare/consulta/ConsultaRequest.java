package com.dentalcare.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ConsultaRequest(
        @NotNull Long pacienteId,
        @NotNull Long dentistaId,
        @NotNull @Future LocalDateTime dataHoraInicio,
        @NotNull @Future LocalDateTime dataHoraFim) { }
