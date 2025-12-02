package com.bodega.backend.controller;

import com.bodega.backend.dto.MovimientoCreateDto;
import com.bodega.backend.dto.MovimientoDto;
import com.bodega.backend.service.MovimientoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin
public class MovimientoController {

    private final MovimientoService service;

    public MovimientoController(MovimientoService service) {
        this.service = service;
    }

    @PostMapping
    public MovimientoDto crear(@RequestBody MovimientoCreateDto dto) {
        return service.registrar(dto);
    }

    @GetMapping
    public List<MovimientoDto> listarPorProducto(@RequestParam Long productoId) {
        return service.listarPorProducto(productoId);
    }
}
