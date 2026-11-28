package com.dentalcare.prontuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EvolucaoRequest(@NotBlank @Size(max = 450) String descricao) { }
