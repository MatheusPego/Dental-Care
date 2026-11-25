package com.dentalcare.consulta;

import com.dentalcare.compartilhado.RecursoNaoEncontradoException;
import com.dentalcare.compartilhado.RegraDeNegocioException;
import com.dentalcare.paciente.Paciente;
import com.dentalcare.paciente.PacienteRepository;
import com.dentalcare.usuarios.Usuarios;
import com.dentalcare.usuarios.UsuariosRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;
    private final PacienteRepository pacienteRepository;
    private final UsuariosRepository usuariosRepository;

    public ConsultaService(ConsultaRepository repository,
                           PacienteRepository pacienteRepository,
                           UsuariosRepository usuariosRepository) {
        this.repository = repository;
        this.pacienteRepository = pacienteRepository;
        this.usuariosRepository = usuariosRepository;
    }

    @Transactional
    public ConsultaResponse criar(ConsultaRequest dados) {
        if (!dados.dataHoraInicio().isBefore(dados.dataHoraFim())) {
            throw new RegraDeNegocioException("O horário de início deve ser anterior ao de fim.");
        }

        Paciente paciente = pacienteRepository.findById(dados.pacienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado: " + dados.pacienteId()));
        if (!paciente.isAtivo()) {
            throw new RegraDeNegocioException("Paciente inativo não pode agendar consulta.");
        }

        // O dentista é um usuário com perfil DENTISTA e ativo
        Usuarios dentista = usuariosRepository.findById(dados.dentistaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Dentista não encontrado: " + dados.dentistaId()));
        if (!"DENTISTA".equalsIgnoreCase(dentista.getPerfil())) {
            throw new RegraDeNegocioException("O usuário informado não tem perfil de dentista.");
        }
        if (!dentista.isAtivo()) {
            throw new RegraDeNegocioException("Dentista inativo não pode receber consultas.");
        }

        // Conflito de horário (sobreposição de intervalos)
        if (repository.existsByDentistaIdAndStatusAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
                dados.dentistaId(), Consulta.Status.AGENDADA,
                dados.dataHoraFim(), dados.dataHoraInicio())) {
            throw new RegraDeNegocioException("O dentista já tem consulta agendada neste horário.");
        }

        Consulta consulta = new Consulta(dentista, paciente, dados.dataHoraInicio(), dados.dataHoraFim());
        return ConsultaResponse.de(repository.save(consulta));
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> listar(Long pacienteId, Long dentistaId, Consulta.Status status) {
        List<Consulta> consultas;
        if (pacienteId != null) {
            consultas = repository.findByPacienteId(pacienteId);
        } else if (dentistaId != null) {
            consultas = repository.findByDentistaId(dentistaId);
        } else if (status != null) {
            consultas = repository.findByStatus(status);
        } else {
            consultas = repository.findAll();
        }
        return consultas.stream().map(ConsultaResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public ConsultaResponse buscar(Long id) {
        return ConsultaResponse.de(obter(id));
    }

    @Transactional
    public ConsultaResponse realizar(Long id) {
        Consulta consulta = obter(id);
        consulta.realizar();
        return ConsultaResponse.de(consulta);
    }

    @Transactional
    public ConsultaResponse cancelar(Long id) {
        Consulta consulta = obter(id);
        consulta.cancelar();
        return ConsultaResponse.de(consulta);
    }

    private Consulta obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada: " + id));
    }
}
