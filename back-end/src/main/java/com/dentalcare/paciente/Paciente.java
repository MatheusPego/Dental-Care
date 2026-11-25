package com.dentalcare.paciente;

import com.dentalcare.compartilhado.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

// 1º passo: @Entity + @Table ligam a classe à tabela pacientes.
@Entity
@Table(name = "pacientes")
public class Paciente extends Pessoa {

    @Column(nullable = false, length = 20)
    private String telefone;

    // 2º passo: o nome da coluna é declarado explicitamente (snake_case).
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    // 3º passo: inativação lógica; prontuários não devem ser apagados.
    @Column(nullable = false)
    private boolean ativo = true;

    protected Paciente() { } // exigido pelo JPA

    public Paciente(String nome, String cpf, String telefone, LocalDate dataNascimento) {
        super(nome, cpf);
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    // 4º passo: comportamento de domínio em vez de setters soltos.
    public void atualizar(String nome, String cpf, String telefone, LocalDate dataNascimento) {
        alterarDados(nome, cpf);
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    public void inativar() { this.ativo = false; }

    public String getTelefone() { return telefone; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public boolean isAtivo() { return ativo; }
}