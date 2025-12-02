package com.bodega.backend.service;

import com.bodega.backend.dto.ProductoCreateDto;
import com.bodega.backend.dto.ProductoDto;
import com.bodega.backend.model.Categoria;
import com.bodega.backend.model.Producto;
import com.bodega.backend.model.UnidadMedida;
import com.bodega.backend.repository.ProductoRepository;
import com.bodega.backend.util.BusinessException;
import com.bodega.backend.util.MapperUtil;
import com.bodega.backend.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;
    private final CategoriaService categoriaService;
    private final UnidadMedidaService unidadMedidaService;

    public ProductoService(ProductoRepository repo,
                           CategoriaService categoriaService,
                           UnidadMedidaService unidadMedidaService) {
        this.repo = repo;
        this.categoriaService = categoriaService;
        this.unidadMedidaService = unidadMedidaService;
    }

    public ProductoDto crear(ProductoCreateDto dto) {
        repo.findByCodigo(dto.codigo()).ifPresent(p -> {
            throw new BusinessException("Código de producto ya existe");
        });

        Categoria cat = categoriaService.obtenerEntidad(dto.categoriaId());
        UnidadMedida uom = unidadMedidaService.obtenerEntidad(dto.unidadMedidaId());

        Producto p = new Producto();
        p.setCodigo(dto.codigo());
        p.setNombre(dto.nombre());
        p.setCategoria(cat);
        p.setUnidadMedida(uom);
        p.setActivo(true);

        return MapperUtil.toDto(repo.save(p));
    }

    public List<ProductoDto> listarActivos() {
        return repo.findByActivoTrue().stream()
                .map(MapperUtil::toDto)
                .toList();
    }

    public Producto obtenerEntidad(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    }
}
