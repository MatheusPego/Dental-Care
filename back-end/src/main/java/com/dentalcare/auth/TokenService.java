package com.dentalcare.auth;

import com.dentalcare.usuarios.Usuarios;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {

    private final SecretKey chave;
    private final long expiracao;

    public TokenService(
            @Value("${jwt.secret}") String segredo,
            @Value("${jwt.expiration-ms}") long expiracao) {

        this.chave = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(segredo)
        );

        this.expiracao = expiracao;
    }

    public String gerarToken(Usuarios usuario) {

        Instant agora = Instant.now(); //Obtém a data e hora atuais.

        return Jwts.builder() //Inicia a construção do JWT.
                .subject(usuario.getId().toString()) //Define o ID do usuário como identificador principal (subject) do token.
                .claim("perfil", usuario.getPerfil()) //Armazena o perfil do usuário no token, por exemplo, ADMIN ou DENTISTA.
                .issuedAt(Date.from(agora))
                .expiration(Date.from(agora.plusMillis(expiracao))) //Define quando o token deixará de ser válido.
                .signWith(chave, Jwts.SIG.HS256) //Assina o token utilizando o algoritmo HMAC SHA-256 e a chave secreta.
                .compact(); //Monta o JWT e retorna uma String.
    }

    public String validarTokenEObterId(String token) {
        return Jwts.parser() //Inicia o analisador do JWT.
                .verifyWith(chave) //	Informa a chave usada para verificar a assinatura.
                .build() // Constrói o analisador.
                .parseSignedClaims(token) //Analisa o token e verifica sua assinatura e validade.
                .getPayload() //	Obtém os dados armazenados no token.
                .getSubject(); //	Recupera o ID do usuário.
    }
}
