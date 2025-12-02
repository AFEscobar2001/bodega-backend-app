package com.bodega.backend.util;

import com.bodega.backend.dto.*;
import com.bodega.backend.model.*;

public final class MapperUtil {

    private MapperUtil() {}

    public static UsuarioDto toDto(Usuario u) {
        return new UsuarioDto(
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                u.getUsername(),
                u.isActivo()
        );
    }

    public static UnidadMedidaDto toDto(UnidadMedida u) {
        return new UnidadMedidaDto(
                u.getId(),
                u.getCodigo(),
                u.getDescripcion()
        );
    }

    public static CategoriaDto toDto(Categoria c) {
        return new CategoriaDto(
                c.getId(),
                c.getNombre(),
                c.getDescripcion(),
                c.isActivo()
        );
    }

    public static ProductoDto toDto(Producto p) {
        return new ProductoDto(
                p.getId(),
                p.getCodigo(),
                p.getNombre(),
                p.getCategoria().getId(),
                p.getUnidadMedida().getId(),
                p.isActivo()
        );
    }

    public static MotivoMovimientoDto toDto(MotivoMovimiento m) {
        return new MotivoMovimientoDto(
                m.getId(),
                m.getCodigo(),
                m.getTipo().name(),
                m.getDescripcion()
        );
    }
}
