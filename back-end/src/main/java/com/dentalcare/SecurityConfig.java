package com.dentalcare;

import com.dentalcare.auth.AuthFilter;
import com.dentalcare.auth.TokenService;

import com.dentalcare.usuarios.UsuariosRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            TokenService tokenService,
            UsuariosRepository usuariosRepository
    ) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new AuthFilter(tokenService, usuariosRepository),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}


/*permitAll(): permite acessar o login sem token.
authenticated(): exige autenticação nas demais rotas.
STATELESS: não mantém sessões no servidor; cada requisição precisa apresentar seu token.
addFilterBefore(): executa nosso filtro antes do filtro de autenticação por usuário e senha.
csrf.disable(): desabilita a proteção CSRF, adequada para esta API stateless que utiliza tokens no cabeçalho,
sem autenticação por cookies.*/