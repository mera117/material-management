package com.elizabeth.material_management.service.impl;


import com.elizabeth.material_management.dto.request.MaterialRequest;
import com.elizabeth.material_management.dto.response.MaterialResponse;
import com.elizabeth.material_management.entity.Ciudad;
import com.elizabeth.material_management.entity.Material;
import com.elizabeth.material_management.entity.TipoMaterial;
import com.elizabeth.material_management.exception.BusinessException;
import com.elizabeth.material_management.exception.ResourceNotFoundException;
import com.elizabeth.material_management.repository.CiudadRepository;
import com.elizabeth.material_management.repository.MaterialRepository;
import com.elizabeth.material_management.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final CiudadRepository ciudadRepository;

    @Override
    public List<MaterialResponse> findAll() {
        return materialRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<MaterialResponse> findByTipo(String tipo) {
        TipoMaterial tipoMaterial = TipoMaterial.valueOf(tipo);

        List<MaterialResponse> materiales = materialRepository.findByTipo(tipoMaterial)
                .stream()
                .map(this::toResponse)
                .toList();

        if (materiales.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron materiales para el tipo: " + tipo);
        }
        return materiales;
    }

    @Override
    public List<MaterialResponse> findByFechaCompra(LocalDate fecha) {
        List<MaterialResponse> materiales = materialRepository.findByFechaCompra(fecha)
                .stream()
                .map(this::toResponse)
                .toList();

        if (materiales.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron materiales para la fecha: " + fecha);
        }
        return materiales;
    }

    @Override
    public List<MaterialResponse> findByCiudad(Long ciudadId) {
        List<MaterialResponse> materiales = materialRepository.findByCiudadId(ciudadId)
                .stream()
                .map(this::toResponse)
                .toList();
        if (materiales.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron materiales en esta ciudad");
        }
        return materiales;
    }

    @Override
    public MaterialResponse create(MaterialRequest request) {

        validateDates(request);

        Ciudad ciudad = ciudadRepository.findById(request.ciudadId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ciudad no encontrada"));

        Material material = Material.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .tipo(request.tipo())
                .precio(request.precio())
                .fechaCompra(request.fechaCompra())
                .fechaVenta(request.fechaVenta())
                .estado(request.estado())
                .ciudad(ciudad)
                .build();

        return toResponse(materialRepository.save(material));
    }

    @Override
    public MaterialResponse update(Long id, MaterialRequest request) {

        validateDates(request);

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado"));

        Ciudad ciudad = ciudadRepository.findById(request.ciudadId())
                .orElseThrow(() -> new ResourceNotFoundException("Ciudad no encontrada"));

        material.setNombre(request.nombre());
        material.setDescripcion(request.descripcion());
        material.setTipo(request.tipo());
        material.setPrecio(request.precio());
        material.setFechaCompra(request.fechaCompra());
        material.setFechaVenta(request.fechaVenta());
        material.setEstado(request.estado());
        material.setCiudad(ciudad);

        return toResponse(
                materialRepository.save(material)
        );
    }

    private void validateDates(MaterialRequest request) {

        if (request.fechaVenta() != null && request.fechaCompra().isAfter(request.fechaVenta())) {

            throw new BusinessException(
                    "La fecha de compra no puede ser superior a la fecha de venta"
            );
        }
    }

    private MaterialResponse toResponse(Material material) {

        return new MaterialResponse(
                material.getId(),
                material.getNombre(),
                material.getDescripcion(),
                material.getTipo(),
                material.getPrecio(),
                material.getFechaCompra(),
                material.getFechaVenta(),
                material.getEstado(),
                material.getCiudad().getNombre(),
                material.getCiudad()
                        .getDepartamento()
                        .getNombre()
        );
    }
}
