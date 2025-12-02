package com.bodega.backend.dto;

import java.math.BigDecimal;

public record ExistenciaDto(
        Long id,
        Long productoId,
        Long bodegaId,
        BigDecimal cantidad
) {}
