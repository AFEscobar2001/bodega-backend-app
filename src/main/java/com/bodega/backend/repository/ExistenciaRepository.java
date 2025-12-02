package com.bodega.backend.repository;

import com.bodega.backend.model.Existencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExistenciaRepository extends JpaRepository<Existencia, Long> {
    Optional<Existencia> findByProductoIdAndBodegaId(Long productoId, Long bodegaId);
    List<Existencia> findByProductoId(Long productoId);
}
