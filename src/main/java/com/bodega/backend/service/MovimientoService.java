package com.bodega.backend.service;

import com.bodega.backend.dto.MovimientoCreateDto;
import com.bodega.backend.dto.MovimientoDto;
import com.bodega.backend.model.*;
import com.bodega.backend.repository.MovimientoRepository;
import com.bodega.backend.util.BusinessException;
import com.bodega.backend.util.NotFoundException;
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
    private final MotivoMovimientoService motivoService;
    private final ExistenciaService existenciaService;

    public MovimientoService(MovimientoRepository repo,
                             ProductoService productoService,
                             BodegaService bodegaService,
                             UsuarioService usuarioService,
                             MotivoMovimientoService motivoService,
                             ExistenciaService existenciaService) {
        this.repo = repo;
        this.productoService = productoService;
        this.bodegaService = bodegaService;
        this.usuarioService = usuarioService;
        this.motivoService = motivoService;
        this.existenciaService = existenciaService;
    }

    @Transactional
    public MovimientoDto registrar(MovimientoCreateDto dto) {
        Producto producto = productoService.obtenerEntidad(dto.productoId());
        Bodega bodega = bodegaService.obtenerEntidad(dto.bodegaId());
        Usuario usuario = usuarioService.obtenerEntidad(dto.usuarioId());
        MotivoMovimiento motivo = motivoService.obtenerEntidad(dto.motivoId());

        MovimientoTipo tipo;
        try {
            tipo = MovimientoTipo.valueOf(dto.tipo());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Tipo de movimiento inválido");
        }

        if (dto.cantidad() == null || dto.cantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Cantidad debe ser mayor a cero");
        }

        Existencia existencia = existenciaService.obtenerOCrear(producto, bodega);

        BigDecimal nuevaCantidad;
        switch (tipo) {
            case IN -> nuevaCantidad = existencia.getCantidad().add(dto.cantidad());
            case OUT -> {
                if (existencia.getCantidad().compareTo(dto.cantidad()) < 0) {
                    throw new BusinessException("Stock insuficiente para salida");
                }
                nuevaCantidad = existencia.getCantidad().subtract(dto.cantidad());
            }
            case ADJUST -> nuevaCantidad = existencia.getCantidad().add(dto.cantidad());
            default -> throw new BusinessException("Tipo no permitido en registrar()");
        }

        existencia.setCantidad(nuevaCantidad);
        existenciaService.guardar(existencia);

        Movimiento mov = new Movimiento();
        mov.setProducto(producto);
        mov.setBodega(bodega);
        mov.setUsuario(usuario);
        mov.setMotivo(motivo);
        mov.setTipo(tipo);
        mov.setCantidad(dto.cantidad());
        mov.setComentario(dto.comentario());

        Movimiento guardado = repo.save(mov);

        return new MovimientoDto(
                guardado.getId(),
                producto.getId(),
                bodega.getId(),
                usuario.getId(),
                motivo.getId(),
                guardado.getTipo().name(),
                guardado.getCantidad(),
                guardado.getComentario(),
                guardado.getCreatedAt(),
                guardado.getRefMovimiento() != null ? guardado.getRefMovimiento().getId() : null
        );
    }

    @Transactional
    public MovimientoDto deshacer(Long movimientoId, Long usuarioId) {
        Movimiento original = repo.findById(movimientoId)
                .orElseThrow(() -> new NotFoundException("Movimiento no encontrado"));

        if (original.getRefMovimiento() != null) {
            throw new BusinessException("El movimiento ya fue deshecho");
        }
        if (original.getTipo() == MovimientoTipo.UNDO) {
            throw new BusinessException("No se puede deshacer un UNDO");
        }

        Usuario usuario = usuarioService.obtenerEntidad(usuarioId);
        Producto producto = original.getProducto();
        Bodega bodega = original.getBodega();

        Existencia existencia = existenciaService.obtenerOCrear(producto, bodega);

        BigDecimal delta;
        if (original.getTipo() == MovimientoTipo.IN || original.getTipo() == MovimientoTipo.ADJUST) {
            // revertir suma → restar
            delta = original.getCantidad().negate();
        } else if (original.getTipo() == MovimientoTipo.OUT) {
            // revertir resta → sumar
            delta = original.getCantidad();
        } else {
            throw new BusinessException("Tipo de movimiento no soportado para UNDO");
        }

        BigDecimal nuevaCantidad = existencia.getCantidad().add(delta);
        if (nuevaCantidad.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("No se puede deshacer, dejaría stock negativo");
        }
        existencia.setCantidad(nuevaCantidad);
        existenciaService.guardar(existencia);

        Movimiento undo = new Movimiento();
        undo.setProducto(producto);
        undo.setBodega(bodega);
        undo.setUsuario(usuario);
        undo.setMotivo(original.getMotivo()); // o un motivo especial "UNDO"
        undo.setTipo(MovimientoTipo.UNDO);
        undo.setCantidad(original.getCantidad());
        undo.setComentario("Deshacer de movimiento " + original.getId());
        undo.setRefMovimiento(original);

        Movimiento guardado = repo.save(undo);

        return new MovimientoDto(
                guardado.getId(),
                producto.getId(),
                bodega.getId(),
                usuario.getId(),
                guardado.getMotivo().getId(),
                guardado.getTipo().name(),
                guardado.getCantidad(),
                guardado.getComentario(),
                guardado.getCreatedAt(),
                original.getId()
        );
    }

    public List<MovimientoDto> listarPorProducto(Long productoId) {
        return repo.findByProductoIdOrderByCreatedAtDesc(productoId).stream()
                .map(m -> new MovimientoDto(
                        m.getId(),
                        m.getProducto().getId(),
                        m.getBodega().getId(),
                        m.getUsuario().getId(),
                        m.getMotivo().getId(),
                        m.getTipo().name(),
                        m.getCantidad(),
                        m.getComentario(),
                        m.getCreatedAt(),
                        m.getRefMovimiento() != null ? m.getRefMovimiento().getId() : null
                ))
                .toList();
    }
}
