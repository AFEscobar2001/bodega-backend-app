package com.bodega.backend.controller;

import com.bodega.backend.dto.AuthResponse;
import com.bodega.backend.dto.LoginRequest;
import com.bodega.backend.dto.RegisterRequest;
import com.bodega.backend.dto.UsuarioDto;
import com.bodega.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public UsuarioDto register(@RequestBody RegisterRequest request) {
        return usuarioService.registrar(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return usuarioService.login(request);
    }
}
