package com.bodega.backend.service;

import com.bodega.backend.dto.ExistenciaDto;
import com.bodega.backend.model.Bodega;
import com.bodega.backend.model.Existencia;
import com.bodega.backend.model.Producto;
import com.bodega.backend.repository.ExistenciaRepository;
import com.bodega.backend.util.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExistenciaService {

    private final ExistenciaRepository repo;

    public ExistenciaService(ExistenciaRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Existencia obtenerOCrear(Producto producto, Bodega bodega) {
        return repo.findByProductoAndBodega(producto, bodega)
                .orElseGet(() -> {
                    Existencia e = new Existencia();
                    e.setProducto(producto);
                    e.setBodega(bodega);
                    return repo.save(e);
                });
    }

    @Transactional
    public Existencia guardar(Existencia existencia) {
        return repo.save(existencia);
    }

    public List<ExistenciaDto> listarPorBodega(Bodega bodega) {
        return repo.findByBodega(bodega).stream()
                .map(e -> new ExistenciaDto(
                        e.getProducto().getId(),
                        e.getProducto().getNombre(),
                        e.getBodega().getId(),
                        e.getBodega().getNombre(),
                        e.getCantidad(),
                        e.getUpdatedAt()
                ))
                .toList();
    }

    // >>> ESTE ES EL QUE TE FALTABA <<<
    public List<ExistenciaDto> listarPorProducto(Long productoId) {
        return repo.findByProductoId(productoId).stream()
                .map(e -> new ExistenciaDto(
                        e.getProducto().getId(),
                        e.getProducto().getNombre(),
                        e.getBodega().getId(),
                        e.getBodega().getNombre(),
                        e.getCantidad(),
                        e.getUpdatedAt()
                ))
                .toList();
    }

    public Existencia obtenerEntidad(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Existencia no encontrada"));
    }
}
