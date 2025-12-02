package com.bodega.backend.service;

import com.bodega.backend.dto.*;
import com.bodega.backend.model.Usuario;
import com.bodega.backend.repository.UsuarioRepository;
import com.bodega.backend.util.BusinessException;
import com.bodega.backend.util.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public UsuarioDto registrar(RegisterRequest req) {

        if (repo.existsByEmail(req.email())) {
            throw new BusinessException("El correo ya está registrado");
        }

        if (repo.existsByUsername(req.username())) {
            throw new BusinessException("El nombre de usuario ya está en uso");
        }

        Usuario u = new Usuario();
        u.setNombre(req.nombre());
        u.setEmail(req.email());
        u.setUsername(req.username());
        u.setPasswordHash(req.password());  

        Usuario guardado = repo.save(u);

        return new UsuarioDto(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getEmail(),
                guardado.getUsername(),
                guardado.isActivo()
        );
    }

    public AuthResponse login(LoginRequest req) {

        Usuario u = repo.findByEmail(req.email())
                .orElseThrow(() -> new BusinessException("Credenciales inválidas"));

        if (!u.getPasswordHash().equals(req.password())) {
            throw new BusinessException("Credenciales inválidas");
        }

        return new AuthResponse(
                u.getId(),
                u.getNombre(),
                u.getUsername()
        );
    }

    public UsuarioDto obtenerDto(Long id) {
        Usuario u = obtenerEntidad(id);

        return new UsuarioDto(
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                u.getUsername(),
                u.isActivo()
        );
    }

    public Usuario obtenerEntidad(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    public UsuarioDto actualizar(Long id, UsuarioUpdateRequest req) {

        Usuario u = obtenerEntidad(id);

        if (req.email() != null) {
            // verificar email duplicado, excepto el propio
            if (!req.email().equals(u.getEmail()) &&
                    repo.existsByEmail(req.email())) {
                throw new BusinessException("El correo ya está registrado");
            }
            u.setEmail(req.email());
        }

        if (!req.username().equals(u.getUsername()) &&
                repo.existsByUsername(req.username())) {
            throw new BusinessException("El nombre de usuario ya está en uso");
        }

        u.setNombre(req.nombre());
        u.setUsername(req.username());

        Usuario actualizado = repo.save(u);

        return new UsuarioDto(
                actualizado.getId(),
                actualizado.getNombre(),
                actualizado.getEmail(),
                actualizado.getUsername(),
                actualizado.isActivo()
        );
    }
}
