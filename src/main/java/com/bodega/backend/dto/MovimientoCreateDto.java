package com.bodega.backend.dto;

import java.math.BigDecimal;

public record MovimientoCreateDto(
        Long productoId,
        Long bodegaId,
        Long usuarioId,
        Long motivoId,
        String tipo,
        BigDecimal cantidad,
        String comentario
) {}
