package com.bodega.backend.dto;

public record ProductoDto(
        Long id,
        String codigo,
        String nombre,
        Long categoriaId,
        Long unidadMedidaId,
        boolean activo
) {}
