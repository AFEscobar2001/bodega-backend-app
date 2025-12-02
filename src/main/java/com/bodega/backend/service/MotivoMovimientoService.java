package com.bodega.backend.service;

import com.bodega.backend.dto.MotivoMovimientoDto;
import com.bodega.backend.model.MotivoMovimiento;
import com.bodega.backend.repository.MotivoMovimientoRepository;
import com.bodega.backend.util.MapperUtil;
import com.bodega.backend.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotivoMovimientoService {

    private final MotivoMovimientoRepository repo;

    public MotivoMovimientoService(MotivoMovimientoRepository repo) {
        this.repo = repo;
    }

    public List<MotivoMovimientoDto> listar() {
        return repo.findAll().stream()
                .map(MapperUtil::toDto)
                .toList();
    }

    public MotivoMovimiento obtenerEntidad(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Motivo de movimiento no encontrado"));
    }
}
