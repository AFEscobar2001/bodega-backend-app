package com.bodega.backend.dto;

public record UsuarioUpdateRequest(
        String nombre,
        String email,
        String username
) {}
