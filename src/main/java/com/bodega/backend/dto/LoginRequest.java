package com.bodega.backend.dto;

public record LoginRequest(
        String email,
        String password
) { }
