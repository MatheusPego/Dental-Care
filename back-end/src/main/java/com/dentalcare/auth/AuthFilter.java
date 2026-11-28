package com.dentalcare.auth;


import com.dentalcare.usuarios.Usuarios;
import com.dentalcare.usuarios.UsuariosRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuariosRepository usuariosRepository;

    public AuthFilter(TokenService tokenService,  UsuariosRepository usuariosRepository) {
        this.tokenService = tokenService;
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String autorizacao = request.getHeader("Authorization");

        if (autorizacao != null && autorizacao.startsWith("Bearer ")) {

            String token = autorizacao.substring(7);

            try {
                String id = tokenService.validarTokenEObterId(token);

                Usuarios usuario = usuariosRepository.findById(Long.parseLong(id)).orElse(null);

                if (usuario == null || !usuario.isAtivo()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario nao encontrado ou inativo.");
                    return;
                }

                var autenticacao = new UsernamePasswordAuthenticationToken(
                        id,
                        null,
                        java.util.Collections.emptyList()
                );

                SecurityContextHolder.getContext().setAuthentication(autenticacao);

            } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}

/*Entenda o que foi implementado
- OncePerRequestFilter: garante que o filtro seja executado uma vez por requisição, dentro da cadeia de filtros.
- getHeader("Authorization"): lê o cabeçalho de autenticação.
- Bearer: identifica o esquema usado para enviar o JWT.
- substring(7): retira o prefixo Bearer  e deixa somente o token.
- validarTokenEObterId(): verifica o JWT e recupera o ID.
- SecurityContextHolder: guarda a autenticação para que o Spring Security reconheça o usuário na requisição.
- sendError(401, ...): rejeita tokens inválidos ou expirados.*/
