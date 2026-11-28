package com.dentalcare.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

//DTO de Atualização (senha opcional - sem @NotBlank)
public record UsuariosAtualizacaoRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @CPF String cpf,
        @NotBlank @Email @Size(max = 150) String email,
        @Size(max = 100) String senha, // <-- Opcional
        @NotBlank @Size(max = 20) String perfil,
        @Size(max = 20) String cro,
        @NotBlank @Size(max = 100) String turno) { }
