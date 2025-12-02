package com.bodega.backend.dto;

import java.math.BigDecimal;

public record ProductoCreateRequest(
        String codigo,
        String nombre,
        Long categoriaId,
        Long unidadMedidaId,
        BigDecimal cantidadInicial,
        Long bodegaId 
) {}
