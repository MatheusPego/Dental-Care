package com.dentalcare.usuarios;

import com.dentalcare.compartilhado.RecursoNaoEncontradoException;
import com.dentalcare.compartilhado.RegraDeNegocioException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuariosService {

    private final UsuariosRepository repository;

    // 1º passo: injeção por construtor (o Spring fornece o repositório).
    public UsuariosService(UsuariosRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UsuariosResponse criar(UsuariosRequest dados) {
        // 2º passo: normaliza o CPF para somente dígitos (o banco guarda 11 caracteres).
        String cpf = somenteDigitos(dados.cpf());

        // 3º passo: regra de negócio - CPF único.
        if (repository.existsByCpf(cpf)) {
            throw new RegraDeNegocioException("Já existe usuarios com este CPF.");
        }

        // 4º passo: cria a entidade e persiste.
        Usuarios usuarios = new Usuarios(dados.nome(), cpf, dados.email(), dados.senha(), dados.perfil(), dados.cro(), dados.turno());
        return UsuariosResponse.de(repository.save(usuarios));
    }

    @Transactional(readOnly = true)
    public List<UsuariosResponse> listarAtivos() {
        return repository.findByAtivoTrue().stream().map(UsuariosResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public UsuariosResponse buscar(Long id) {
        return UsuariosResponse.de(obter(id));
    }

    @Transactional
    public UsuariosResponse atualizar(Long id, UsuariosRequest dados) {
        // 5º passo: carrega a entidade gerenciada.
        Usuarios usuarios = obter(id);
        String cpf = somenteDigitos(dados.cpf());

        // 6º passo: CPF único, ignorando o próprio usuarios.
        if (repository.existsByCpfAndIdNot(cpf, id)) {
            throw new RegraDeNegocioException("Já existe outro usuarios com este CPF.");
        }

        // 7º passo: altera a entidade; o Hibernate grava no commit (dirty checking).
        usuarios.atualizar(dados.nome(), cpf, dados.email(), dados.senha(), dados.perfil(), dados.cro(), dados.turno());
        return UsuariosResponse.de(usuarios);
    }

    @Transactional
    public void inativar(Long id) {
        obter(id).inativar();
    }

    private Usuarios obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuarios não encontrado: " + id));
    }

    private static String somenteDigitos(String valor) {
        return valor.replaceAll("\\D", "");
    }
}