package com.bodega.backend.service;

import com.bodega.backend.dto.MovimientoCreateDto;
import com.bodega.backend.dto.MovimientoDto;
import com.bodega.backend.model.*;
import com.bodega.backend.repository.MovimientoRepository;
import com.bodega.backend.util.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovimientoService {

    private final MovimientoRepository repo;
    private final ProductoService productoService;
    private final BodegaService bodegaService;
    private final UsuarioService usuarioService;
    private final ExistenciaService existenciaService;

    public MovimientoService(MovimientoRepository repo,
                             ProductoService productoService,
                             BodegaService bodegaService,
                             UsuarioService usuarioService,
                             ExistenciaService existenciaService) {
        this.repo = repo;
        this.productoService = productoService;
        this.bodegaService = bodegaService;
        this.usuarioService = usuarioService;
        this.existenciaService = existenciaService;
    }

    @Transactional
    public MovimientoDto registrar(MovimientoCreateDto dto) {

        if (dto.cantidad() == null || dto.cantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Cantidad debe ser mayor a cero");
        }

        Producto producto = productoService.obtenerEntidad(dto.productoId());
        Usuario usuario = usuarioService.obtenerEntidad(dto.usuarioId());

        MovimientoTipo tipo;
        try {
            tipo = MovimientoTipo.valueOf(dto.tipo());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Tipo de movimiento inválido");
        }

        Bodega bodegaOrigen = bodegaService.obtenerEntidad(dto.bodegaOrigenId());
        Bodega bodegaDestino = null;
        if (tipo == MovimientoTipo.TRASLADO) {
            if (dto.bodegaDestinoId() == null) {
                throw new BusinessException("Debe indicar bodega destino para traslado");
            }
            if (dto.bodegaDestinoId().equals(dto.bodegaOrigenId())) {
                throw new BusinessException("Origen y destino no pueden ser la misma bodega");
            }
            bodegaDestino = bodegaService.obtenerEntidad(dto.bodegaDestinoId());
        }

        Existencia exOrigen = existenciaService.obtenerOCrear(producto, bodegaOrigen);

        if (exOrigen.getCantidad().compareTo(dto.cantidad()) < 0) {
            throw new BusinessException("Stock insuficiente en bodega origen");
        }

        exOrigen.setCantidad(exOrigen.getCantidad().subtract(dto.cantidad()));
        existenciaService.guardar(exOrigen);

        Bodega bodegaMovimiento = bodegaOrigen;

        if (tipo == MovimientoTipo.TRASLADO) {
            Existencia exDestino = existenciaService.obtenerOCrear(producto, bodegaDestino);
            exDestino.setCantidad(exDestino.getCantidad().add(dto.cantidad()));
            existenciaService.guardar(exDestino);
            bodegaMovimiento = bodegaDestino;
        }

        Movimiento mov = new Movimiento();
        mov.setProducto(producto);
        mov.setBodega(bodegaMovimiento);
        mov.setUsuario(usuario);
        mov.setTipo(tipo);
        mov.setCantidad(dto.cantidad());
        mov.setComentario(dto.comentario());

        Movimiento guardado = repo.save(mov);

        return toDto(guardado);
    }

    public List<MovimientoDto> listarPorProducto(Long productoId) {
        return repo.findByProductoIdOrderByCreatedAtDesc(productoId).stream()
                .map(this::toDto)
                .toList();
    }

    private MovimientoDto toDto(Movimiento m) {
        return new MovimientoDto(
                m.getId(),
                m.getProducto().getId(),
                m.getProducto().getNombre(),
                m.getBodega().getId(),
                m.getBodega().getNombre(),
                m.getUsuario().getId(),
                m.getUsuario().getNombre(),
                m.getTipo().name(),
                m.getCantidad(),
                m.getComentario(),
                m.getCreatedAt(),
                m.getRefMovimiento() != null ? m.getRefMovimiento().getId() : null
        );
    }
}
