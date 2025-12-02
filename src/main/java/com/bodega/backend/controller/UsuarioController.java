package com.bodega.backend.controller;

import com.bodega.backend.dto.UsuarioDto;
import com.bodega.backend.dto.UsuarioUpdateRequest;
import com.bodega.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public UsuarioDto obtener(@PathVariable Long id) {
        return service.obtenerDto(id);
    }

    @PutMapping("/{id}")
    public UsuarioDto actualizar(@PathVariable Long id,
                                 @RequestBody UsuarioUpdateRequest request) {
        return service.actualizar(id, request);
    }
}
