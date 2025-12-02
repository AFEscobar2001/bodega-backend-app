package com.bodega.backend.dto;

public record UsuarioDto(
        Long id,
        String nombre,
        String email,
        String username,
        boolean activo
) {}
