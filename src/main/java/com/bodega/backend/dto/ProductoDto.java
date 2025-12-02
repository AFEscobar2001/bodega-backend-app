package com.bodega.backend.dto;

import java.time.LocalDateTime;

public record ProductoDto(
        Long id,
        String codigo,
        String nombre,
        Long categoriaId,
        String categoriaNombre,
        Long unidadMedidaId,
        String unidadMedidaCodigo,
        boolean activo,
        LocalDateTime createdAt
) {}
