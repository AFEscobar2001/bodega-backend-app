package com.bodega.backend.service;

import com.bodega.backend.dto.CategoriaDto;
import com.bodega.backend.model.Categoria;
import com.bodega.backend.repository.CategoriaRepository;
import com.bodega.backend.util.MapperUtil;
import com.bodega.backend.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repo;

    public CategoriaService(CategoriaRepository repo) {
        this.repo = repo;
    }

    public List<CategoriaDto> listar() {
        return repo.findAll().stream()
                .map(MapperUtil::toDto)
                .toList();
    }

    public Categoria obtenerEntidad(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
    }
}
