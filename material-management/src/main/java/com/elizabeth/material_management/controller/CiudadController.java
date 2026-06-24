package com.elizabeth.material_management.controller;

import com.elizabeth.material_management.dto.response.ApiResponse;
import com.elizabeth.material_management.dto.response.CiudadResponse;
import com.elizabeth.material_management.service.CiudadService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
@RequiredArgsConstructor
public class CiudadController {

    private final CiudadService ciudadService;

    @Operation(summary = "Listar ciudades")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CiudadResponse>>> findAll() {

        List<CiudadResponse> ciudades = ciudadService.findAll();

        return ResponseEntity.ok(new ApiResponse<>(200, "Ciudades encontradas", ciudades)
        );
    }

    @Operation(summary = "Listar ciudades por departamento")
    @GetMapping("/departamento/{id}")
    public ResponseEntity<ApiResponse<List<CiudadResponse>>> findByDepartamento(
            @PathVariable Long id) {

        List<CiudadResponse> ciudades = ciudadService.findByDepartamento(id);

        return ResponseEntity.ok(new ApiResponse<>(200, "Ciudades encontradas", ciudades)
        );
    }
}
