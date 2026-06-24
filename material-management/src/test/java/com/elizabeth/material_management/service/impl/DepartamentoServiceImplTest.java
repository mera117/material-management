package com.elizabeth.material_management.service.impl;

import com.elizabeth.material_management.dto.response.DepartamentoResponse;
import com.elizabeth.material_management.entity.Departamento;
import com.elizabeth.material_management.repository.DepartamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartamentoServiceImplTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private DepartamentoServiceImpl departamentoService;

    @Test
    void shouldReturnAllDepartamentos() {

        Departamento departamento = Departamento.builder()
                .id(1L)
                .codigo("ANT")
                .nombre("Antioquia")
                .build();

        when(departamentoRepository.findAll()).thenReturn(List.of(departamento));

        List<DepartamentoResponse> result = departamentoService.findAll();

        assertEquals(1, result.size());
        assertEquals("ANT", result.get(0).codigo());
        assertEquals("Antioquia", result.get(0).nombre());
        verify(departamentoRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoDepartamentosExist() {

        when(departamentoRepository.findAll()).thenReturn(List.of());

        List<DepartamentoResponse> result = departamentoService.findAll();

        assertTrue(result.isEmpty());
        verify(departamentoRepository, times(1)).findAll();
    }
}