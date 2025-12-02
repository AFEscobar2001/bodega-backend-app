package com.bodega.backend.service;

import com.bodega.backend.dto.AuthResponse;
import com.bodega.backend.dto.LoginRequest;
import com.bodega.backend.dto.RegisterRequest;
import com.bodega.backend.dto.UsuarioDto;
import com.bodega.backend.model.Usuario;
import com.bodega.backend.repository.UsuarioRepository;
import com.bodega.backend.util.BusinessException;
import com.bodega.backend.util.HashUtil;
import com.bodega.backend.util.MapperUtil;
import com.bodega.backend.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDto registrar(RegisterRequest req) {
        usuarioRepository.findByUsername(req.username()).ifPresent(u -> {
            throw new BusinessException("Username ya está en uso");
        });
        if (req.email() != null && !req.email().isBlank()) {
            usuarioRepository.findByEmail(req.email()).ifPresent(u -> {
                throw new BusinessException("Email ya está en uso");
            });
        }

        Usuario u = new Usuario();
        u.setNombre(req.nombre());
        u.setEmail(req.email());
        u.setUsername(req.username());
        u.setPasswordHash(HashUtil.hashPassword(req.password()));
        u.setActivo(true);

        Usuario guardado = usuarioRepository.save(u);
        return MapperUtil.toDto(guardado);
    }

    public AuthResponse login(LoginRequest req) {

        Usuario u = usuarioRepository.findByEmail(req.email())
                .orElseThrow(() -> new BusinessException("Credenciales inválidas"));

        if (!u.isActivo()) {
            throw new BusinessException("Usuario inactivo");
        }

        if (!HashUtil.matches(req.password(), u.getPasswordHash())) {
            throw new BusinessException("Credenciales inválidas");
        }

        return new AuthResponse(
                u.getId(),
                u.getNombre(),
                u.getUsername()
        );
    }


    public List<UsuarioDto> listarActivos() {
        return usuarioRepository.findAll().stream()
                .filter(Usuario::isActivo)
                .map(MapperUtil::toDto)
                .toList();
    }

    public Usuario obtenerEntidad(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }
}
