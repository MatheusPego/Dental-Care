package com.dentalcare.consulta;

import com.dentalcare.compartilhado.RegraDeNegocioException;
import com.dentalcare.paciente.Paciente;
import com.dentalcare.usuarios.Usuarios;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta {

    public enum Status { AGENDADA, REALIZADA, CANCELADA }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // O dentista agora é um Usuarios (perfil DENTISTA): dentista_id -> usuarios.id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dentista_id", nullable = false)
    private Usuarios dentista;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // Nomes das colunas iguais aos do SQL
    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim", nullable = false)
    private LocalDateTime dataHoraFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.AGENDADA;

    protected Consulta() { }

    public Consulta(Usuarios dentista, Paciente paciente,
                    LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        // Mesma regra do CHECK do banco: início < fim
        if (!dataHoraInicio.isBefore(dataHoraFim)) {
            throw new RegraDeNegocioException("O horário de início deve ser anterior ao de fim.");
        }
        this.dentista = dentista;
        this.paciente = paciente;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
    }

    public void realizar() {
        exigirAgendada("realizada");
        this.status = Status.REALIZADA;
    }

    public void cancelar() {
        exigirAgendada("cancelada");
        this.status = Status.CANCELADA;
    }

    private void exigirAgendada(String acao) {
        if (status != Status.AGENDADA) {
            throw new RegraDeNegocioException(
                    "Só consultas agendadas podem ser " + acao + ". Status atual: " + status);
        }
    }

    public Long getId() { return id; }
    public Usuarios getDentista() { return dentista; }
    public Paciente getPaciente() { return paciente; }
    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public Status getStatus() { return status; }
}
