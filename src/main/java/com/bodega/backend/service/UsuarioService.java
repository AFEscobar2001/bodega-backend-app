package com.bodega.backend.service;

import com.bodega.backend.dto.AuthResponse;
import com.bodega.backend.dto.LoginRequest;
import com.bodega.backend.dto.RegisterRequest;
import com.bodega.backend.dto.UsuarioDto;
import com.bodega.backend.dto.UsuarioUpdateRequest;
import com.bodega.backend.model.Usuario;
import com.bodega.backend.repository.UsuarioRepository;
import com.bodega.backend.util.BusinessException;
import com.bodega.backend.util.MapperUtil;
import com.bodega.backend.util.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }


    public List<UsuarioDto> listar() {
        return repo.findAll()
                .stream()
                .map(MapperUtil::toDto)
                .toList();
    }



    @Transactional
    public UsuarioDto registrar(RegisterRequest request) {
        if (request.email() == null || request.email().isBlank()) {
            throw new BusinessException("Email es obligatorio");
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new BusinessException("Password es obligatorio");
        }

        Usuario u = new Usuario();
        u.setNombre(request.nombre());
        u.setEmail(request.email());
        u.setUsername(request.username());   
        u.setPasswordHash(request.password()); 
        u.setActivo(true);

        Usuario guardado = repo.save(u);
        return MapperUtil.toDto(guardado);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        if (request.email() == null || request.email().isBlank()
                || request.password() == null || request.password().isBlank()) {
            throw new BusinessException("Credenciales inválidas");
        }

        Usuario u = repo.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Credenciales inválidas"));

        if (!u.getPasswordHash().equals(request.password())) {
            throw new BusinessException("Credenciales inválidas");
        }

        String token = UUID.randomUUID().toString(); 

        return new AuthResponse(
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                token
        );
    }

     public Usuario obtenerEntidad(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    public UsuarioDto obtenerDto(Long id) {
        return MapperUtil.toDto(obtenerEntidad(id));
    }

    @Transactional
    public UsuarioDto actualizar(Long id, UsuarioUpdateRequest req) {
        Usuario u = obtenerEntidad(id);
        if (req.nombre() != null) u.setNombre(req.nombre());
        if (req.email() != null) u.setEmail(req.email());
        if (req.username() != null) u.setUsername(req.username());
        Usuario guardado = repo.save(u);
        return MapperUtil.toDto(guardado);
    }

}
