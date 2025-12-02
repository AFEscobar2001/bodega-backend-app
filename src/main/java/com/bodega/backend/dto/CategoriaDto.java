package com.bodega.backend.dto;

public record CategoriaDto(
        Long id,
        String nombre,
        String descripcion,
        boolean activo
) {}
