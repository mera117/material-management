package com.elizabeth.material_management.service;

import com.elizabeth.material_management.dto.response.CiudadResponse;

import java.util.List;

public interface CiudadService {

    List<CiudadResponse> findAll();
    List<CiudadResponse> findByDepartamento(Long departamentoId);

}
