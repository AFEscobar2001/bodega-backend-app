package com.bodega.backend.controller;

import com.bodega.backend.dto.MotivoMovimientoDto;
import com.bodega.backend.service.MotivoMovimientoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motivos")
@CrossOrigin
public class MotivoMovimientoController {

    private final MotivoMovimientoService service;

    public MotivoMovimientoController(MotivoMovimientoService service) {
        this.service = service;
    }

    @GetMapping
    public List<MotivoMovimientoDto> listar() {
        return service.listar();
    }
}
