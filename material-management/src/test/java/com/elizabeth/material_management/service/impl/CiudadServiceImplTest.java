package com.elizabeth.material_management.service.impl;

import com.elizabeth.material_management.dto.response.CiudadResponse;
import com.elizabeth.material_management.entity.Ciudad;
import com.elizabeth.material_management.entity.Departamento;
import com.elizabeth.material_management.repository.CiudadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CiudadServiceImplTest {

    @Mock
    private CiudadRepository ciudadRepository;

    @InjectMocks
    private CiudadServiceImpl ciudadService;

    private Departamento departamento;
    private Ciudad ciudad;

    private void setUpData() {
        departamento = Departamento.builder()
                .id(1L)
                .codigo("ANT")
                .nombre("Antioquia")
                .build();

        ciudad = Ciudad.builder()
                .id(1L)
                .codigo("MED")
                .nombre("Medellin")
                .departamento(departamento)
                .build();
    }

    @Test
    void shouldReturnAllCiudades() {
        setUpData();

        when(ciudadRepository.findAll()).thenReturn(List.of(ciudad));

        List<CiudadResponse> result = ciudadService.findAll();

        assertEquals(1, result.size());
        assertEquals("Medellin", result.get(0).nombre());
        assertEquals("Antioquia", result.get(0).departamento());
        verify(ciudadRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoCiudadesExist() {
        when(ciudadRepository.findAll()).thenReturn(List.of());

        List<CiudadResponse> result = ciudadService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnCiudadesByDepartamento() {
        setUpData();

        when(ciudadRepository.findByDepartamentoId(1L)).thenReturn(List.of(ciudad));

        List<CiudadResponse> result = ciudadService.findByDepartamento(1L);

        assertEquals(1, result.size());
        assertEquals("MED", result.get(0).codigo());
        verify(ciudadRepository, times(1)).findByDepartamentoId(1L);
    }

    @Test
    void shouldReturnEmptyListWhenDepartamentoHasNoCiudades() {
        when(ciudadRepository.findByDepartamentoId(99L)).thenReturn(List.of());

        List<CiudadResponse> result = ciudadService.findByDepartamento(99L);

        assertTrue(result.isEmpty());
    }
}