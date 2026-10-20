// CLASSE DENTISTA
package model;

public class Dentista extends Administrador{

    private String cro;

    public  Dentista() { // Inicia as variáveis com null
        super();
    }
    public Dentista(String nome, String cpf, String email, String senha, String cro) {
        super(nome, cpf, email, senha);
        this.cro = cro;
    }

    public String getCro() { return cro; }
    public void setCro(String cro) { this.cro = cro; }
}
