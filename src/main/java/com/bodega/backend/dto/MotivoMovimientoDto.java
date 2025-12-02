package com.bodega.backend.dto;

public record MotivoMovimientoDto(
        Long id,
        String codigo,
        String tipo,
        String descripcion
) {}
