package com.bodega.backend.service;

import com.bodega.backend.dto.BodegaDto;
import com.bodega.backend.model.Bodega;
import com.bodega.backend.repository.BodegaRepository;
import com.bodega.backend.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodegaService {

    private final BodegaRepository repo;

    public BodegaService(BodegaRepository repo) {
        this.repo = repo;
    }

    public List<BodegaDto> listar() {
        return repo.findAll()
                .stream()
                .map(b -> new BodegaDto(
                        b.getId(),
                        b.getNombre(),
                        b.getUbicacion(),
                        b.isActivo()
                ))
                .toList();
    }

    public Bodega obtenerEntidad(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Bodega no encontrada"));
    }
}
