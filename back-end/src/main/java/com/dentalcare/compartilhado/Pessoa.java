package com.dentalcare.compartilhado;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// 1º passo: @MappedSuperclass NÃO cria tabela própria; suas colunas são
// herdadas pelas tabelas das subclasses (pacientes agora, usuarios depois).
@MappedSuperclass
public abstract class Pessoa {

    // 2º passo: IDENTITY delega a geração do id ao AUTO_INCREMENT do MySQL.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 3º passo: nullable e length espelham o esquema do V1.
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 11)
    private String cpf;

    // 4º passo: construtor vazio exigido pelo JPA (protegido, para não ser usado à toa).
    protected Pessoa() { }

    protected Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    // 5º passo: alteração controlada dos dados (sem setter de id).
    protected void alterarDados(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
}