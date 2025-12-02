package com.bodega.backend.repository;

import com.bodega.backend.model.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BodegaRepository extends JpaRepository<Bodega, Long> {
    Optional<Bodega> findByNombre(String nombre);
}
