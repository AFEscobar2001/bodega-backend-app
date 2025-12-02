package com.bodega.backend.repository;

import com.bodega.backend.model.MotivoMovimiento;
import com.bodega.backend.model.MotivoTipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MotivoMovimientoRepository extends JpaRepository<MotivoMovimiento, Long> {
    Optional<MotivoMovimiento> findByCodigo(String codigo);
    List<MotivoMovimiento> findByTipo(MotivoTipo tipo);
}
