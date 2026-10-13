package org.example;

package model;

public abstract class Administrador extends Pessoa implements Autenticar{

    private String email;
    private String senha;

    public Administrador() { super(); }
    public Administrador(String nome, String cpf, String email, String senha) {
        super(nome, cpf);
        this.email = email;
        this.senha = senha;
    }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }

    @Override
    public boolean login(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }
}
