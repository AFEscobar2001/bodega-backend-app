package com.bodega.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExistenciaDto(
        Long productoId,
        String productoNombre,
        Long bodegaId,
        String bodegaNombre,
        BigDecimal cantidad,
        LocalDateTime updatedAt
) {}
