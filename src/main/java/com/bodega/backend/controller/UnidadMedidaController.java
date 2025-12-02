package com.bodega.backend.controller;

import com.bodega.backend.dto.UnidadMedidaDto;
import com.bodega.backend.model.UnidadMedida;
import com.bodega.backend.service.UnidadMedidaService;
import com.bodega.backend.util.MapperUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades-medida")
public class UnidadMedidaController {

    private final UnidadMedidaService service;

    public UnidadMedidaController(UnidadMedidaService service) {
        this.service = service;
    }

    @GetMapping
    public List<UnidadMedidaDto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public UnidadMedidaDto obtener(@PathVariable Long id) {
        UnidadMedida u = service.obtenerEntidad(id);
        return MapperUtil.toDto(u);
    }
}
