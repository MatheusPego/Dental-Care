package com.dentalcare.consulta;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// 1º passo: a camada HTTP apenas recebe, valida (@Valid) e delega ao Service.
@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse criar(@Valid @RequestBody ConsultaRequest dados) {
        return service.criar(dados);
    }

    // 2º passo: filtros opcionais por query string, ex.: /api/consultas?status=AGENDADA
    @GetMapping
    public List<ConsultaResponse> listar(@RequestParam(required = false) Long pacienteId,
                                         @RequestParam(required = false) Long dentistaId,
                                         @RequestParam(required = false) Consulta.Status status) {
        return service.listar(pacienteId, dentistaId, status);
    }

    @GetMapping("/{id}")
    public ConsultaResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    // 3º passo: mudanças de status são PATCH; consulta não é apagada.
    @PatchMapping("/{id}/realizar")
    public ConsultaResponse realizar(@PathVariable Long id) {
        return service.realizar(id);
    }

    @PatchMapping("/{id}/cancelar")
    public ConsultaResponse cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }
}
