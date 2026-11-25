package com.dentalcare.prontuario;

import com.dentalcare.compartilhado.RecursoNaoEncontradoException;
import com.dentalcare.compartilhado.RegraDeNegocioException;
import com.dentalcare.consulta.Consulta;
import com.dentalcare.consulta.ConsultaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProntuarioService {

    private final ProntuarioRepository repository;
    private final ConsultaRepository consultaRepository;

    // 1º passo: injeção por construtor.
    public ProntuarioService(ProntuarioRepository repository, ConsultaRepository consultaRepository) {
        this.repository = repository;
        this.consultaRepository = consultaRepository;
    }

    @Transactional
    public ProntuarioResponse criar(ProntuarioRequest dados) {
        // 2º passo: a consulta precisa existir.
        Consulta consulta = consultaRepository.findById(dados.consultaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada: " + dados.consultaId()));

        // 3º passo: regra de negócio - um prontuário por consulta.
        if (repository.existsByConsultaId(dados.consultaId())) {
            throw new RegraDeNegocioException("Esta consulta já possui prontuário.");
        }

        // 4º passo: a entidade valida se a consulta está REALIZADA; depois persiste.
        Prontuario prontuario = new Prontuario(dados.descricao(), consulta);
        return ProntuarioResponse.de(repository.save(prontuario));
    }

    @Transactional(readOnly = true)
    public List<ProntuarioResponse> listar(Long pacienteId, Long dentistaId) {
        List<Prontuario> prontuarios;
        if (pacienteId != null) {
            prontuarios = repository.findByConsultaPacienteId(pacienteId);
        } else if (dentistaId != null) {
            prontuarios = repository.findByConsultaDentistaId(dentistaId);
        } else {
            prontuarios = repository.findAll();
        }
        return prontuarios.stream().map(ProntuarioResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public ProntuarioResponse buscar(Long id) {
        return ProntuarioResponse.de(obter(id));
    }

    // 5º passo: altera a entidade; o Hibernate grava no commit (dirty checking).
    @Transactional
    public ProntuarioResponse adicionarEvolucao(Long id, EvolucaoRequest dados) {
        Prontuario prontuario = obter(id);
        prontuario.adicionarEvolucao(dados.texto());
        return ProntuarioResponse.de(prontuario);
    }

    private Prontuario obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Prontuário não encontrado: " + id));
    }
}
