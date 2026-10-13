package org.example;

public class Dentista extends Administrador{

    private String email;
    private String senha;
    private String cro;

    public  Dentista() { // Inicia as variáveis com null
        super();
    }
    public Dentista(String nome, String cpf, String email, String senha, String cro) {
        super(nome, cpf);
        this.email = email;
        this.senha = senha;
        this.cro = cro;
    }

    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getCro() { return cro; }

    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setCro(String cro) { this.cro = cro; }
}
