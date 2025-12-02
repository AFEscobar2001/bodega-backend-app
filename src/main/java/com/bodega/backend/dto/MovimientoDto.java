package com.bodega.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoDto(
        Long id,
        Long productoId,
        String productoNombre,
        Long bodegaId,
        String bodegaNombre,
        Long usuarioId,
        String usuarioNombre,
        String tipo,          
        BigDecimal cantidad,
        String comentario,
        LocalDateTime createdAt,
        Long refMovimientoId      
) {}
