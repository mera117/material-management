package com.elizabeth.material_management.dto.request;

import com.elizabeth.material_management.entity.EstadoMaterial;
import com.elizabeth.material_management.entity.TipoMaterial;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaterialRequest(
        @NotBlank
        String nombre,
        String descripcion,
        @NotNull
        TipoMaterial tipo,
        @NotNull
        BigDecimal precio,

        @NotNull
        LocalDate fechaCompra,
        LocalDate fechaVenta,
        @NotNull
        EstadoMaterial estado,
        @NotNull
        Long ciudadId
) {
}
