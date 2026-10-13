package org.example;

public class Prontuario {

    private Long id;
    private String descricao;
    private Consulta consultas; // Usada para simular a fk

    public Prontuario(String descricao, Consulta consultas) {
        if(consultas.getStatusAtual() != Consulta.Status.REALIZADA) {
            throw new IllegalArgumentException("A consulta precisa ser realizada!");
        }
        this.descricao = descricao;
        this.consultas = consultas;
    }

    public Long getId() { return id; }
    public String getDescricao() { return descricao; }
    public Consulta getConsultas() { return consultas; }

    public void setid(Long id) { this.id = id; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void adicionarEvolucao(String novainformacao) {
        this.descricao += "\nEvolução clínica: " + novainformacao;
    }
}