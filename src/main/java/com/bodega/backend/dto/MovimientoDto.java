package com.bodega.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoDto(
        Long id,
        Long productoId,
        Long bodegaId,
        Long usuarioId,
        Long motivoId,
        String tipo,
        BigDecimal cantidad,
        String comentario,
        LocalDateTime createdAt,
        Long refMovimientoId
) {}
