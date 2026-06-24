package com.elizabeth.material_management.service.impl;

import com.elizabeth.material_management.dto.response.CiudadResponse;
import com.elizabeth.material_management.entity.Ciudad;
import com.elizabeth.material_management.repository.CiudadRepository;
import com.elizabeth.material_management.service.CiudadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CiudadServiceImpl implements CiudadService {

    private final CiudadRepository ciudadRepository;

    @Override
    public List<CiudadResponse> findAll() {

        return ciudadRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<CiudadResponse> findByDepartamento(Long departamentoId) {

        return ciudadRepository.findByDepartamentoId(departamentoId)
                .stream().map(this::toResponse).toList();
    }

    private CiudadResponse toResponse(Ciudad ciudad) {
        return new CiudadResponse(
                ciudad.getId(),
                ciudad.getCodigo(),
                ciudad.getNombre(),
                ciudad.getDepartamento().getNombre()
        );
    }
}
