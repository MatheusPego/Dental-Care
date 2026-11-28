package com.dentalcare.prontuario;

import com.dentalcare.compartilhado.RegraDeNegocioException;
import com.dentalcare.consulta.Consulta;
import jakarta.persistence.*;

@Entity
@Table(name = "prontuarios")
public class Prontuario {

    // A coluna do SQL é VARCHAR(500)
    public static final int LIMITE_DESCRICAO = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "consulta_id", nullable = false, unique = true)
    private Consulta consulta;

    @Column(nullable = false, length = LIMITE_DESCRICAO)
    private String descricao;

    protected Prontuario() { }

    public Prontuario(String descricao, Consulta consulta) {
        if (consulta.getStatus() != Consulta.Status.REALIZADA) {
            throw new RegraDeNegocioException("A consulta precisa ser realizada!");
        }
        validarTamanho(descricao);
        this.descricao = descricao;
        this.consulta = consulta;
    }

    public void adicionarEvolucao(String novaInformacao) {
        String novaDescricao = this.descricao + "\nEvolução clínica: " + novaInformacao;
        validarTamanho(novaDescricao);
        this.descricao = novaDescricao;
    }

    private static void validarTamanho(String texto) {
        if (texto.length() > LIMITE_DESCRICAO) {
            throw new RegraDeNegocioException(
                    "A descrição do prontuário excede " + LIMITE_DESCRICAO + " caracteres.");
        }
    }

    public Long getId() { return id; }
    public Consulta getConsulta() { return consulta; }
    public String getDescricao() { return descricao; }
}
