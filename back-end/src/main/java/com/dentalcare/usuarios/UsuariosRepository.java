package com.dentalcare.usuarios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// 1º passo: JpaRepository já fornece save, findById, findAll, deleteById etc.
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    // 2º passo: o Spring deriva o SQL a partir do nome do método.
    boolean existsByCpf(String cpf);

    // 3º passo: usada na atualização, para não acusar o próprio paciente como duplicado.
    boolean existsByCpfAndIdNot(String cpf, Long id);

    //mesma finalidade dos metodos de cpf
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByCro(String cro);
    boolean existsByCroAndIdNot(String cro, Long id);

    List<Usuarios> findByAtivoTrue();
    List<Usuarios> findByAtivoFalse();

    Optional<Usuarios> findByEmail(String email);//Sera utilizado em AuthService para autenticação
}