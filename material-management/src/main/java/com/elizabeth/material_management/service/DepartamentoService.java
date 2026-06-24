package com.elizabeth.material_management.service;

import com.elizabeth.material_management.dto.response.DepartamentoResponse;

import java.util.List;

public interface DepartamentoService {

    List<DepartamentoResponse> findAll();

}
