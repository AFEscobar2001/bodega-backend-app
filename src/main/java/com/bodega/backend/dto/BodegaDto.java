package com.bodega.backend.dto;

public record BodegaDto(
        Long id,
        String nombre,
        String ubicacion,
        boolean activo
) {}
