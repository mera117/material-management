package com.elizabeth.material_management.controller;


import com.elizabeth.material_management.dto.request.MaterialRequest;
import com.elizabeth.material_management.dto.response.ApiResponse;
import com.elizabeth.material_management.dto.response.MaterialResponse;
import com.elizabeth.material_management.entity.EstadoMaterial;
import com.elizabeth.material_management.entity.TipoMaterial;
import com.elizabeth.material_management.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @Operation(summary = "Obtener tipos de materiales")
    @GetMapping("/tipos")
    public ResponseEntity<ApiResponse<List<String>>> getTipos() {

        List<String> tipos = Arrays.stream(TipoMaterial.values()).map(Enum::name).toList();
        return ResponseEntity.ok(new ApiResponse<>(200,"Tipos encontrados",tipos)
        );
    }

    @Operation(summary = "Obtener estados de materiales")
    @GetMapping("/estados")
    public ResponseEntity<ApiResponse<List<String>>> getEstados() {

        List<String> estados =Arrays.stream(EstadoMaterial.values()).map(Enum::name).toList();

        return ResponseEntity.ok(new ApiResponse<>(200,
                        "Estados encontrados",estados));
    }

    @Operation(summary = "Obtener todos los materiales")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MaterialResponse>>> findAll() {

        List<MaterialResponse> materiales = materialService.findAll();

        return ResponseEntity.ok(new ApiResponse<>( 200, "Materiales encontrados",materiales));
    }

    @Operation(summary = "Buscar materiales por tipo")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<ApiResponse<List<MaterialResponse>>> findByTipo(
            @PathVariable String tipo) {

        List<MaterialResponse> materiales =materialService.findByTipo(tipo);

        return ResponseEntity.ok(new ApiResponse<>(200,"Consulta realizada exitosamente",materiales));
    }

    @Operation(summary = "Buscar materiales por fecha de compra")
    @GetMapping("/fecha-compra")
    public ResponseEntity<ApiResponse<List<MaterialResponse>>> findByFechaCompra(
            @RequestParam LocalDate fecha) {

        List<MaterialResponse> materiales =materialService.findByFechaCompra(fecha);

        return ResponseEntity.ok(new ApiResponse<>(200, "Consulta realizada exitosamente",materiales));
    }

    @Operation(summary = "Buscar materiales por ciudad")
    @GetMapping("/ciudad/{ciudadId}")
    public ResponseEntity<ApiResponse<List<MaterialResponse>>> findByCiudad(
            @PathVariable Long ciudadId) {

        List<MaterialResponse> materiales = materialService.findByCiudad(ciudadId);

        return ResponseEntity.ok(new ApiResponse<>(200, "Consulta realizada exitosamente", materiales));
    }

    @Operation(summary = "Crear material")
    @PostMapping
    public ResponseEntity<ApiResponse<MaterialResponse>> create(
            @Valid @RequestBody MaterialRequest request) {

        MaterialResponse response = materialService.create(request);

        return ResponseEntity.ok(new ApiResponse<>(200,"Material creado exitosamente",response)
        );
    }

    @Operation(summary = "Actualizar material")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MaterialResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody MaterialRequest request) {

        MaterialResponse response = materialService.update(id, request);

        return ResponseEntity.ok( new ApiResponse<>(200,"Material actualizado exitosamente", response)
        );
    }
}
