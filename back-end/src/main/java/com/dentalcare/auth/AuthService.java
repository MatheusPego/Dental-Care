package com.dentalcare.auth;

import com.dentalcare.compartilhado.CredenciaisInvalidasException;
import com.dentalcare.usuarios.Usuarios;
import com.dentalcare.usuarios.UsuariosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

@Service
public class AuthService {

    private final UsuariosRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UsuariosRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional
    public LoginResponse login(LoginRequest login) {
        //Normaliza o email
        String email = login.email().trim().toLowerCase(Locale.ROOT);

        //Retorna o objeto Usuarios, caso exista. Se não existir, lança a exceção
        Usuarios usuario = repository.findByEmail(email)
                .orElseThrow(() -> new CredenciaisInvalidasException("E-mail ou senha invalidos."));

        //Verifica se esta ativo e se a senha corresponde
        if (!usuario.isAtivo() || !passwordEncoder.matches(login.senha(),  usuario.getSenha())) {
            throw new CredenciaisInvalidasException("E-mail ou senha invalidos.");
        }

        //Gera o token
        String token = tokenService.gerarToken(usuario);

        return LoginResponse.de(usuario, token);
    }
}
