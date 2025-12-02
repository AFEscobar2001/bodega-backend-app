package com.bodega.backend.controller;

import com.bodega.backend.model.Bodega;
import com.bodega.backend.service.BodegaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bodegas")
@CrossOrigin
public class BodegaController {

    private final BodegaService service;

    public BodegaController(BodegaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Bodega> listar() {
        return service.listar();
    }
}
