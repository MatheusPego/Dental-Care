package com.dentalcare.prontuario;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// 1º passo: a camada HTTP apenas recebe, valida (@Valid) e delega ao Service.
// Não há DELETE: prontuários não devem ser apagados.
@RestController
@RequestMapping("/api/prontuarios")
public class ProntuarioController {

    private final ProntuarioService service;

    public ProntuarioController(ProntuarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProntuarioResponse criar(@Valid @RequestBody ProntuarioRequest dados) {
        return service.criar(dados);
    }

    @GetMapping
    public List<ProntuarioResponse> listar(@RequestParam(required = false) Long pacienteId,
                                           @RequestParam(required = false) Long dentistaId) {
        return service.listar(pacienteId, dentistaId);
    }

    @GetMapping("/{id}")
    public ProntuarioResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping("/{id}/evolucoes")
    public ProntuarioResponse adicionarEvolucao(@PathVariable Long id,
                                                @Valid @RequestBody EvolucaoRequest dados) {
        return service.adicionarEvolucao(id, dados);
    }
}
