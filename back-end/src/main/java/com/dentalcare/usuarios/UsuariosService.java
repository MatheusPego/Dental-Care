package com.dentalcare.usuarios;

import com.dentalcare.compartilhado.RecursoNaoEncontradoException;
import com.dentalcare.compartilhado.RegraDeNegocioException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuariosService {

    private final UsuariosRepository repository;
    private final PasswordEncoder passwordEncoder;

    // 1º passo: injeção por construtor (o Spring fornece o repositório).
    public UsuariosService(UsuariosRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    private void validarPerfilECro(String perfil, String cro) {

        if (!"ADMIN".equalsIgnoreCase(perfil)
                && !"DENTISTA".equalsIgnoreCase(perfil)) {
            throw new RegraDeNegocioException(
                    "Perfil inválido. Utilize ADMIN ou DENTISTA."
            );
        }

        if ("DENTISTA".equalsIgnoreCase(perfil)
                && (cro == null || cro.isBlank())) {

            throw new RegraDeNegocioException(
                    "O CRO é obrigatório para usuários dentistas."
            );
        }

        if ("ADMIN".equalsIgnoreCase(perfil)
                && cro != null && !cro.isBlank()) {
            throw new RegraDeNegocioException(
                    "Somente dentistas podem possuir CRO."
            );
        }
    }

    @Transactional
    public UsuariosResponse criar(UsuariosRequest dados) {
        // 2º passo: normaliza o CPF para somente dígitos (o banco guarda 11 caracteres).
        String cpf = somenteDigitos(dados.cpf());

        // 3º passo: regra de negócio - CPF único.
        if (repository.existsByCpf(cpf)) {
            throw new RegraDeNegocioException("Já existe um usuario com este CPF.");
        }

        //verificar email para impedir duplicação
        String email = dados.email().trim().toLowerCase(Locale.ROOT);//formatação
        if (repository.existsByEmail(email)) {
            throw new RegraDeNegocioException(
                    "Já existe um usuario com este e-mail.");
        }

        //Normaliza perfil e cro antes de validar
        String perfil = dados.perfil().trim().toUpperCase(Locale.ROOT);
        String cro = (dados.cro() == null || dados.cro().isBlank()) ? null : dados.cro().trim().toUpperCase(Locale.ROOT);
        validarPerfilECro(perfil, dados.cro());

        if (cro != null && repository.existsByCro(cro)) {
            throw new RegraDeNegocioException(
                    "Já existe um dentista com este CRO."
            );
        }

        // 4º passo: cria a entidade e persiste.
        Usuarios usuarios = new Usuarios(dados.nome(), cpf, email, passwordEncoder.encode(dados.senha()), perfil, cro, dados.turno());
        return UsuariosResponse.de(repository.save(usuarios));
    }

    @Transactional(readOnly = true)
    public List<UsuariosResponse> listarAtivos() {
        return repository.findByAtivoTrue().stream().map(UsuariosResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public List<UsuariosResponse> listarDesativos() {
        return repository.findByAtivoFalse().stream().map(UsuariosResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public UsuariosResponse buscar(Long id) {
        return UsuariosResponse.de(obter(id));
    }

    @Transactional
    public UsuariosResponse atualizar(Long id, UsuariosAtualizacaoRequest dados) {
        // 5º passo: carrega a entidade gerenciada.
        Usuarios usuarios = obter(id);
        String cpf = somenteDigitos(dados.cpf());

        // 6º passo: CPF único, ignorando o próprio usuario.
        if (repository.existsByCpfAndIdNot(cpf, id)) {
            throw new RegraDeNegocioException("Já existe outro usuario com este CPF.");
        }

        //Email único, ignorando o próprio usuario.
        String email = dados.email().trim().toLowerCase(Locale.ROOT);//formatação
        if (repository.existsByEmailAndIdNot(email, id)) {
            throw new RegraDeNegocioException(
                    "Já existe outro usuario com este e-mail."
            );
        }

        //Normaliza perfil e cro antes de validar
        String perfil = dados.perfil().trim().toUpperCase(Locale.ROOT);
        String cro = (dados.cro() == null || dados.cro().isBlank()) ? null : dados.cro().trim().toUpperCase(Locale.ROOT);
        validarPerfilECro(perfil, cro);

        if (cro != null && repository.existsByCroAndIdNot(cro, id)) {
            throw new RegraDeNegocioException(
                    "Já existe um dentista com este CRO."
            );
        }

        // Se enviou uma nova senha, criptografa. Se veio vazia ou nula, passa null.
        String senha = (dados.senha() != null && !dados.senha().isBlank())
                ? passwordEncoder.encode(dados.senha())
                : null;

        // 7º passo: altera a entidade; o Hibernate grava no commit (dirty checking).
        usuarios.atualizar(dados.nome(), cpf, email, senha, perfil, cro, dados.turno());
        return UsuariosResponse.de(usuarios);
    }

    @Transactional
    public void inativar(Long id) {
        obter(id).inativar();
    }

    @Transactional
    public void reativar(Long id) {
        obter(id).reativar();
    }

    private Usuarios obter(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuarios não encontrado: " + id));
    }

    private static String somenteDigitos(String valor) {
        return valor.replaceAll("\\D", "");
    }
}