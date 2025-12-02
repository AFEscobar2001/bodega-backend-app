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
    public MovimientoDto registrar(@RequestBody MovimientoCreateDto dto) {
        return service.registrar(dto);
    }

    @PostMapping("/{id}/undo")
    public MovimientoDto deshacer(@PathVariable Long id,
                                  @RequestParam("usuarioId") Long usuarioId) {
        return service.deshacer(id, usuarioId);
    }

    @GetMapping
    public List<MovimientoDto> listarPorProducto(@RequestParam("productoId") Long productoId) {
        return service.listarPorProducto(productoId);
    }
}
