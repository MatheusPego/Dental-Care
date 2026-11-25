
package com.dentalcare.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.hibernate.validator.constraints.br.CPF;

// 1º passo: record = classe imutável; as anotações validam a entrada
// antes de qualquer regra de negócio.
public record PacienteRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @CPF String cpf,
        @NotBlank @Size(max = 20) String telefone,
        @NotNull @Past LocalDate dataNascimento) { }
