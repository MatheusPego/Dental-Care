// CLASSE PESSOA
package model;

public abstract class Pessoa {

    private Long id;
    private String nome;
    private String cpf;

    public Pessoa() {} // Inicia variáveis com null
    public Pessoa(String nome, String cpf) { //Id vai ser gerado na classe que representa o banco
        this.nome = nome;
        this.cpf = cpf;
    }
    public Long getId(){ return id; }
    public String getNome(){ return nome; }
    public String getCpf(){ return cpf; }

    public void setId(Long id){ this.id = id; }
    public void setNome(String nome){ this.nome = nome; }
    public void setCpf(String cpf){ this.cpf = cpf; }


}
