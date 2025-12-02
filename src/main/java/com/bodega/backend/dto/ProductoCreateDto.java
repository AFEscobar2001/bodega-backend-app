package com.bodega.backend.dto;

public record ProductoCreateDto(
        String codigo,
        String nombre,
        Long categoriaId,
        Long unidadMedidaId
) {}
