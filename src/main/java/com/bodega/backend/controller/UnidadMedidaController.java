package com.bodega.backend.controller;

import com.bodega.backend.dto.UnidadMedidaDto;
import com.bodega.backend.service.UnidadMedidaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades-medida")
@CrossOrigin
public class UnidadMedidaController {

    private final UnidadMedidaService service;

    public UnidadMedidaController(UnidadMedidaService service) {
        this.service = service;
    }

    @GetMapping
    public List<UnidadMedidaDto> listar() {
        return service.listar();
    }
}
