package com.elizabeth.material_management.service;

import com.elizabeth.material_management.dto.request.MaterialRequest;
import com.elizabeth.material_management.dto.response.MaterialResponse;

import java.time.LocalDate;
import java.util.List;

public interface MaterialService {
    List<MaterialResponse> findAll();

    List<MaterialResponse> findByTipo(String tipo);

    List<MaterialResponse> findByFechaCompra(LocalDate fecha);

    List<MaterialResponse> findByCiudad(Long ciudadId);

    MaterialResponse create(MaterialRequest request);

    MaterialResponse update(Long id, MaterialRequest request);
}
