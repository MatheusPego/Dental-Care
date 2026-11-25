package com.dentalcare.prontuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProntuarioRequest(
        @NotNull Long consultaId,
        @NotBlank @Size(max = 500) String descricao) { }
