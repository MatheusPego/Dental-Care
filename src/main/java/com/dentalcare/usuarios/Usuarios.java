package com.dentalcare.usuarios;

import com.dentalcare.compartilhado.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

// 1º passo: @Entity + @Table ligam a classe à tabela usuarios.
@Entity
@Table(name = "usuarios")
public class Usuarios extends Pessoa {

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false, length = 20)
    private String perfil;

    @Column(nullable = true, length = 20)
    private String cro;

    @Column(nullable = true, length = 20)
    private String turno;

    @Column(nullable = false)
    private boolean ativo = true;

    protected Usuarios() { }

    public Usuarios(String nome, String cpf, String email, String senha, String perfil, String cro, String turno) {
        super(nome, cpf);
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.cro = cro;
        this.turno = turno;
    }

    public void atualizar(String nome, String cpf, String email, String senha, String perfil, String cro, String turno) {
        alterarDados(nome, cpf);
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.cro = cro;
        this.turno = turno;
    }

    public void inativar() { this.ativo = false; }

    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getPerfil() { return perfil; }
    public String getCro() { return cro; }
    public String getTurno() { return turno; }
    public boolean isAtivo() { return ativo; }

    @Override
    public String getIdentificacaoFormatada() {
        return "Usuários: (" + this.perfil + "): " + getNome() + (this.cro != null ? "\nCRO: " + this.cro : "") + (this.turno != null ? "\nTurno: " + this.turno : "");
    }
}