package com.elizabeth.material_management.dto.response;

import com.elizabeth.material_management.entity.EstadoMaterial;
import com.elizabeth.material_management.entity.TipoMaterial;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaterialResponse(
        Long id,
        String nombre,
        String descripcion,
        TipoMaterial tipo,
        BigDecimal precio,
        LocalDate fechaCompra,
        LocalDate fechaVenta,
        EstadoMaterial estado,
        String ciudad,
        String departamento
) {
}
