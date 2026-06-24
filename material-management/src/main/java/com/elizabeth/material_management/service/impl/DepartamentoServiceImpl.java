package com.elizabeth.material_management.service.impl;

import com.elizabeth.material_management.dto.response.DepartamentoResponse;
import com.elizabeth.material_management.entity.Departamento;
import com.elizabeth.material_management.repository.DepartamentoRepository;
import com.elizabeth.material_management.service.DepartamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    @Override
    public List<DepartamentoResponse> findAll() {

        return departamentoRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    private DepartamentoResponse toResponse(Departamento departamento) {

        return new DepartamentoResponse(
                departamento.getId(),
                departamento.getCodigo(),
                departamento.getNombre()
        );
    }
}