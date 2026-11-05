package com.dentalcare.paciente;

import com.dentalcare.compartilhado.RecursoNaoEncontradoException;
import com.dentalcare.compartilhado.RegraDeNegocioException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteService {

    private final PacienteRepository repository;

    // 1º passo: injeção por construtor (o Spring fornece o repositório).
    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PacienteResponse criar(PacienteRequest dados) {
        // 2º passo: normaliza o CPF para somente dígitos (o banco guarda 11 caracteres).
        String cpf = somenteDigitos(dados.cpf());

        // 3º passo: regra de negócio - CPF único.
        if (repository.existsByCpf(cpf)) {
            throw new RegraDeNegocioException("Já existe paciente com este CPF.");
        }

        // 4º passo: cria a entidade e persiste.
        Paciente paciente = new Paciente(dados.nome(), cpf, dados.telefone(), dados.dataNascimento());
        return PacienteResponse.de(repository.save(paciente));
    }

    @Transactional(readOnly = true)
    public List<PacienteResponse> listarAtivos() {
        return repository.findByAtivoTrue().stream().map(PacienteResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public PacienteResponse buscar(Long id) {
        return PacienteResponse.de(obter(id));
    }

    @Transactional
    public PacienteResponse atualizar(Long id, PacienteRequest dados) {
        // 5º passo: carrega a entidade gerenciada.
        Paciente paciente = obter(id);
        String cpf = somenteDigitos(dados.cpf());

        // 6º passo: CPF único, ignorando o próprio paciente.
        if (repository.existsByCpfAndIdNot(cpf, id)) {
            throw new RegraDeNegocioException("Já existe outro paciente com este CPF.");
        }

        // 7º passo: altera a entidade; o Hibernate grava no commit (dirty checking).
        paciente.atualizar(dados.nome(), cpf, dados.telefone(), dados.dataNascimento());
        return PacienteResponse.de(paciente);
    }

    @Transactional
    public void inativar(Long id) {
        obter(id).inativar();
    }

    private Paciente obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado: " + id));
    }

    private static String somenteDigitos(String valor) {
        return valor.replaceAll("\\D", "");
    }
}