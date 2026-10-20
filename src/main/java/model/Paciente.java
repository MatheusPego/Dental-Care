// CLASSE PACIENTE
package model;

import java.time.LocalDate;

public class Paciente extends Pessoa{

    private String telefone;
    private LocalDate dataNascimento;

    public Paciente() { // Inicia com valores nulos
        super();
    }
    public Paciente(String nome, String cpf, String telefone, LocalDate dataNascimento) {
        super(nome, cpf);
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() { return telefone; }
    public LocalDate getDataNascimento() { return dataNascimento; }

    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
}
