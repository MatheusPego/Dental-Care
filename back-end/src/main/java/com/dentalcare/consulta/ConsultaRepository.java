package com.dentalcare.consulta;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// 1º passo: JpaRepository já fornece save, findById, findAll etc.
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // 2º passo: o Spring deriva o SQL a partir do nome (paciente.id, dentista.id).
    List<Consulta> findByPacienteId(Long pacienteId);

    List<Consulta> findByDentistaId(Long dentistaId);

    List<Consulta> findByStatus(Consulta.Status status);

    // 3º passo: conflito de horário = intervalos que se sobrepõem:
    // (inicio existente < fim novo) E (fim existente > inicio novo)
    boolean existsByDentistaIdAndStatusAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
            Long dentistaId, Consulta.Status status, LocalDateTime fimNovo, LocalDateTime inicioNovo);
}