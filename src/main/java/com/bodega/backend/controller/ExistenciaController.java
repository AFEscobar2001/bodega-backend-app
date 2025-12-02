package com.bodega.backend.controller;

import com.bodega.backend.dto.ExistenciaDto;
import com.bodega.backend.service.ExistenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/existencias")
public class ExistenciaController {

    private final ExistenciaService service;

    public ExistenciaController(ExistenciaService service) {
        this.service = service;
    }

    @GetMapping("/producto/{productoId}")
    public List<ExistenciaDto> listarPorProducto(@PathVariable Long productoId) {
        return service.listarPorProducto(productoId);
    }
}
