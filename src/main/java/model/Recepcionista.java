// CLASSE RECEPCIONISTA
package model;

public class Recepcionista extends Administrador {

    private String email;
    private String senha;
    private String turno;

    public Recepcionista() { // Inicia as variáveis com null
        super();
    }
    public Recepcionista(String nome, String cpf, String email, String senha, String turno) {
        super(nome, cpf, email, senha);
        this.turno = turno;
    }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
}
