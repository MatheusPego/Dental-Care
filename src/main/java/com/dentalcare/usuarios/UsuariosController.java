package com.dentalcare.usuarios;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController{

    private final UsuariosService service;

    public UsuariosController(UsuariosService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuariosResponse criar(@Valid @RequestBody UsuariosRequest dados) {
        return service.criar(dados);
    }

    @GetMapping
    public List<UsuariosResponse> listar() {
        return service.listarAtivos();
    }

    @GetMapping("/{id}")
    public UsuariosResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public UsuariosResponse atualizar(@PathVariable Long id, @Valid @RequestBody UsuariosRequest dados) {
        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        service.inativar(id);
    }
}