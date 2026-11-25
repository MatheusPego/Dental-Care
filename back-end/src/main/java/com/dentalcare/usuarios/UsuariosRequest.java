package com.dentalcare.usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

// 1º passo: record = classe imutável; as anotações validam a entrada
// antes de qualquer regra de negócio.
public record UsuariosRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @CPF String cpf,
        @NotBlank @Size(max = 150) String email,
        @NotNull @Size(max = 100) String senha,
        @NotNull @Size(max = 20) String perfil,
        @NotNull @Size(max = 20) String cro,
        @NotNull @Size(max = 100) String turno) { }