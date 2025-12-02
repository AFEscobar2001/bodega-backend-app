package com.bodega.backend.controller;

import com.bodega.backend.dto.UsuarioDto;
import com.bodega.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioDto> listarActivos() {
        return usuarioService.listarActivos();
    }
}
