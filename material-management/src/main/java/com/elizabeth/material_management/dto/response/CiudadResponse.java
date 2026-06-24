package com.elizabeth.material_management.dto.response;

public record CiudadResponse(
        Long id,
        String codigo,
        String nombre,
        String departamento
) {
}
