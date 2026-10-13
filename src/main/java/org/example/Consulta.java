package org.example;

package model;

import java.time.LocalDateTime;

public class Consulta { // Consulta tem atributo das classes Dentista e Paciente para referenciar a fk

    private Long id;
    private LocalDateTime dataHora;
    private Dentista dentista;
    private Paciente paciente;
    private Status statusAtual;

    public enum Status{
        AGENDADA, REALIZADA, CANCELADA
    }

    public Consulta() {
        this.statusAtual = Status.AGENDADA;
        this.dataHora = LocalDateTime.now();
    }
    public Consulta(Dentista dentista, Paciente paciente) {
        this.statusAtual = Status.AGENDADA;
        this.dataHora = LocalDateTime.now();
        this.dentista = dentista;
        this.paciente = paciente;
    }

    public Long getI() { return id; }
    public Dentista getDentista() { return  dentista; }
    public Paciente getPaciente() { return  paciente; }
    public Status getStatusAtual() { return  statusAtual; }

    public void setId(Long id) { this.id = id; }
    public void setDentista(Dentista dentista) { this.dentista = dentista; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void realizaConsulta() { this.statusAtual = Status.REALIZADA; }
    public void cancelarConsulta() { this.statusAtual = Status.CANCELADA; }
}
