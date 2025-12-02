package com.bodega.backend.dto;

public record AuthResponse(
        Long id,
        String nombre,
        String username
) {}
