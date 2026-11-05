package com.dentalcare.compartilhado;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// 1º passo: a classe-base já converte erros de validação (@Valid) em respostas 400
// no formato ProblemDetail (RFC 9457).
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    // 2º passo: traduz exceções do nosso domínio em códigos HTTP.
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ProblemDetail naoEncontrado(RecursoNaoEncontradoException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ProblemDetail conflito(RegraDeNegocioException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }
}