package com.elizabeth.material_management.controller;

import com.elizabeth.material_management.dto.response.ApiResponse;
import com.elizabeth.material_management.dto.response.DepartamentoResponse;
import com.elizabeth.material_management.service.DepartamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartamentoResponse>>> findAll() {

        List<DepartamentoResponse> departamentos = departamentoService.findAll();

        return ResponseEntity.ok(new ApiResponse<>(200, "Departamentos encontrados", departamentos)
        );
    }
}