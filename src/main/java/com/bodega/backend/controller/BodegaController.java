package com.bodega.backend.controller;

import com.bodega.backend.dto.BodegaDto;
import com.bodega.backend.dto.ExistenciaDto;
import com.bodega.backend.model.Bodega;
import com.bodega.backend.service.BodegaService;
import com.bodega.backend.service.ExistenciaService;
import com.bodega.backend.util.MapperUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bodegas")
public class BodegaController {

    private final BodegaService service;
    private final ExistenciaService existenciaService;

    public BodegaController(BodegaService service, ExistenciaService existenciaService) {
        this.service = service;
        this.existenciaService = existenciaService;
    }

    @GetMapping
    public List<BodegaDto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public BodegaDto obtener(@PathVariable Long id) {
        Bodega b = service.obtenerEntidad(id);
        return MapperUtil.toDto(b);
    }

    @GetMapping("/{id}/productos")
    public List<ExistenciaDto> productosDeBodega(@PathVariable Long id) {
        Bodega bodega = service.obtenerEntidad(id);
        return existenciaService.listarPorBodega(bodega);
    }
}
