package com.bodega.backend.repository;

import com.bodega.backend.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByProductoIdOrderByCreatedAtDesc(Long productoId);
    List<Movimiento> findByUsuarioIdOrderByCreatedAtDesc(Long usuarioId);
}
