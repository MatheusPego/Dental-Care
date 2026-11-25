package com.dentalcare.prontuario;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// 1º passo: JpaRepository já fornece save, findById, findAll etc.
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

    // 2º passo: usada para garantir um prontuário por consulta.
    boolean existsByConsultaId(Long consultaId);

    // 3º passo: o Spring segue o caminho consulta -> paciente -> id.
    List<Prontuario> findByConsultaPacienteId(Long pacienteId);

    List<Prontuario> findByConsultaDentistaId(Long dentistaId);
}
