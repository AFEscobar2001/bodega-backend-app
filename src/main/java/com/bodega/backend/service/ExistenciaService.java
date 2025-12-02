package com.bodega.backend.service;

import com.bodega.backend.model.Bodega;
import com.bodega.backend.model.Existencia;
import com.bodega.backend.model.Producto;
import com.bodega.backend.repository.ExistenciaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExistenciaService {

    private final ExistenciaRepository repo;

    public ExistenciaService(ExistenciaRepository repo) {
        this.repo = repo;
    }

    public Existencia obtenerOCrear(Producto producto, Bodega bodega) {
        return repo.findByProductoIdAndBodegaId(producto.getId(), bodega.getId())
                .orElseGet(() -> {
                    Existencia e = new Existencia();
                    e.setProducto(producto);
                    e.setBodega(bodega);
                    e.setCantidad(BigDecimal.ZERO);
                    return repo.save(e);
                });
    }

    public void guardar(Existencia e) {
        repo.save(e);
    }
}
