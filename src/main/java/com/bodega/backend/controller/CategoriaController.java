package com.bodega.backend.controller;

import com.bodega.backend.dto.CategoriaDto;
import com.bodega.backend.model.Categoria;
import com.bodega.backend.service.CategoriaService;
import com.bodega.backend.util.MapperUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoriaDto> listar() {
        return service.listar(); 
    }

    @GetMapping("/{id}")
    public CategoriaDto obtener(@PathVariable Long id) {
        Categoria c = service.obtenerEntidad(id);
        return MapperUtil.toDto(c);
    }
}
