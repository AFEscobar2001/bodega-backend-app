package com.bodega.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bodega.backend.model.Bodega;
import com.bodega.backend.model.Existencia;
import com.bodega.backend.model.Producto;

public interface ExistenciaRepository extends JpaRepository<Existencia, Long> {

    Optional<Existencia> findByProductoAndBodega(Producto producto, Bodega bodega);

    List<Existencia> findByBodega(Bodega bodega);

    // para listar stock por producto
    List<Existencia> findByProductoId(Long productoId);
}