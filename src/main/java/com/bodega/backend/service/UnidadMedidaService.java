package com.bodega.backend.service;

import com.bodega.backend.dto.UnidadMedidaDto;
import com.bodega.backend.model.UnidadMedida;
import com.bodega.backend.repository.UnidadMedidaRepository;
import com.bodega.backend.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadMedidaService {

    private final UnidadMedidaRepository repo;

    public UnidadMedidaService(UnidadMedidaRepository repo) {
        this.repo = repo;
    }

    public List<UnidadMedidaDto> listar() {
        return repo.findAll()
                .stream()
                .map(u -> new UnidadMedidaDto(
                        u.getId(),
                        u.getCodigo(),
                        u.getDescripcion()
                ))
                .toList();
    }

    public UnidadMedida obtenerEntidad(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Unidad de medida no encontrada"));
    }
}
