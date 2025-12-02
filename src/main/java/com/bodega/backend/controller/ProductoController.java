package com.bodega.backend.controller;

import com.bodega.backend.dto.ProductoCreateDto;
import com.bodega.backend.dto.ProductoDto;
import com.bodega.backend.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @PostMapping
    public ProductoDto crear(@RequestBody ProductoCreateDto dto) {
        return service.crear(dto);
    }

    @GetMapping
    public List<ProductoDto> listarActivos() {
        return service.listarActivos();
    }
}
