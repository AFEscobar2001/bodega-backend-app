package com.bodega.backend.controller;

import com.bodega.backend.dto.ProductoCreateRequest;
import com.bodega.backend.dto.ProductoDto;
import com.bodega.backend.model.Producto;
import com.bodega.backend.service.ProductoService;
import com.bodega.backend.util.MapperUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductoDto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ProductoDto obtener(@PathVariable Long id) {
        Producto p = service.obtenerEntidad(id);
        return MapperUtil.toDto(p);
    }

    @PostMapping
    public ProductoDto crear(@RequestBody ProductoCreateRequest request) {
        return service.crear(request);
    }
}
