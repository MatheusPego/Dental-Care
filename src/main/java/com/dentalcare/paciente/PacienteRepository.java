package com.dentalcare.paciente;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// 1º passo: JpaRepository já fornece save, findById, findAll, deleteById etc.
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // 2º passo: o Spring deriva o SQL a partir do nome do método.
    boolean existsByCpf(String cpf);

    // 3º passo: usada na atualização, para não acusar o próprio paciente como duplicado.
    boolean existsByCpfAndIdNot(String cpf, Long id);

    List<Paciente> findByAtivoTrue();
}