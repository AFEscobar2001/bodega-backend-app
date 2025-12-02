package com.bodega.backend.service;

import com.bodega.backend.dto.ProductoCreateRequest;
import com.bodega.backend.dto.ProductoDto;
import com.bodega.backend.model.Bodega;
import com.bodega.backend.model.Categoria;
import com.bodega.backend.model.Existencia;
import com.bodega.backend.model.Producto;
import com.bodega.backend.model.UnidadMedida;
import com.bodega.backend.repository.ProductoRepository;
import com.bodega.backend.util.MapperUtil;
import com.bodega.backend.util.NotFoundException;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;
    private final CategoriaService categoriaService;
    private final UnidadMedidaService unidadService;
    private final BodegaService bodegaService;
    private final ExistenciaService existenciaService;

    public ProductoService(ProductoRepository repo,
                           CategoriaService categoriaService,
                           UnidadMedidaService unidadService,
                           BodegaService bodegaService,
                           ExistenciaService existenciaService) {
        this.repo = repo;
        this.categoriaService = categoriaService;
        this.unidadService = unidadService;
        this.bodegaService = bodegaService;
        this.existenciaService = existenciaService;
    }

    public List<ProductoDto> listar() {
        return repo.findAll()
                .stream()
                .map(MapperUtil::toDto)
                .toList();
    }

    public Producto obtenerEntidad(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    }

    @Transactional
    public ProductoDto crear(ProductoCreateRequest req) {
        Categoria categoria = categoriaService.obtenerEntidad(req.categoriaId());
        UnidadMedida unidad = unidadService.obtenerEntidad(req.unidadMedidaId());

        Producto p = new Producto();
        p.setCodigo(req.codigo());
        p.setNombre(req.nombre());
        p.setCategoria(categoria);
        p.setUnidadMedida(unidad);
        p.setActivo(true);

        Producto guardado = repo.save(p);

        // stock inicial
        if (req.cantidadInicial() != null && req.cantidadInicial().signum() > 0) {
            Long bodegaId = req.bodegaId() != null ? req.bodegaId() : 1L; 
            Bodega bodega = bodegaService.obtenerEntidad(bodegaId);

            Existencia ex = existenciaService.obtenerOCrear(guardado, bodega);
            ex.setCantidad(ex.getCantidad().add(req.cantidadInicial()));
            existenciaService.guardar(ex);
        }

        return MapperUtil.toDto(guardado);
    }
}