package com.dentalcare.paciente;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// 1º passo: a camada HTTP apenas recebe, valida (@Valid) e delega ao Service.
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteResponse criar(@Valid @RequestBody PacienteRequest dados) {
        return service.criar(dados);
    }

    @GetMapping
    public List<PacienteResponse> listar() {
        return service.listarAtivos();
    }

    @GetMapping("/{id}")
    public PacienteResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public PacienteResponse atualizar(@PathVariable Long id, @Valid @RequestBody PacienteRequest dados) {
        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        service.inativar(id);
    }
}