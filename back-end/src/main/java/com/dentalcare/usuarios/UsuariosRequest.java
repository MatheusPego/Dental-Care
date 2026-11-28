package com.dentalcare.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

// 1º passo: record = classe imutável; as anotações validam a entrada
// antes de qualquer regra de negócio.
public record UsuariosRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @CPF String cpf,
        @NotBlank @Email @Size(max = 150) String email, //adicionei @Email
        @NotBlank @Size(max = 100) String senha, //Mudei a senha para NotBlank
        @NotBlank @Size(max = 20) String perfil,
        @Size(max = 20) String cro,
        @NotBlank @Size(max = 100) String turno) { }