package com.bodega.backend.dto;

public record RegisterRequest(
        String nombre,
        String email,
        String username,
        String password
) {}
