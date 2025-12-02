package com.bodega.backend.dto;

import java.math.BigDecimal;

public record MovimientoCreateDto(
        Long productoId,
        Long bodegaOrigenId,
        Long bodegaDestinoId,     
        Long usuarioId,
        String tipo,          
        BigDecimal cantidad,
        String comentario
) {}
