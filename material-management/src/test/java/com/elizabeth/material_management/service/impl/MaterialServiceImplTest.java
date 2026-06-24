package com.elizabeth.material_management.service.impl;

import com.elizabeth.material_management.dto.request.MaterialRequest;
import com.elizabeth.material_management.dto.response.MaterialResponse;
import com.elizabeth.material_management.entity.*;
import com.elizabeth.material_management.exception.BusinessException;
import com.elizabeth.material_management.exception.ResourceNotFoundException;
import com.elizabeth.material_management.repository.CiudadRepository;
import com.elizabeth.material_management.repository.MaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceImplTest {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private CiudadRepository ciudadRepository;

    @InjectMocks
    private MaterialServiceImpl materialService;

    private Departamento departamento;
    private Ciudad ciudad;

    @BeforeEach
    void setUp() {
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

    private MaterialRequest buildRequest(Long ciudadId, LocalDate fechaCompra, LocalDate fechaVenta) {
        return new MaterialRequest(
                "Monitor Samsung",
                "Monitor 24",
                TipoMaterial.MONITOR,
                new BigDecimal("500000"),
                fechaCompra,
                fechaVenta,
                EstadoMaterial.DISPONIBLE,
                ciudadId
        );
    }

    @Test
    void shouldCreateMaterialSuccessfully() {

        MaterialRequest request = buildRequest(1L, LocalDate.now(), LocalDate.now().plusDays(10));

        when(ciudadRepository.findById(1L))
                .thenReturn(Optional.of(ciudad));
        when(materialRepository.save(any(Material.class)))
                .thenAnswer(invocation -> {
                    Material m = invocation.getArgument(0);
                    m.setId(10L);
                    return m;
                });

        MaterialResponse response = materialService.create(request);

        assertNotNull(response);
        assertEquals("Monitor Samsung", response.nombre());
        assertEquals("Medellin", response.ciudad());
        assertEquals("Antioquia", response.departamento());
        verify(materialRepository, times(1)).save(any(Material.class));
    }

    @Test
    void shouldThrowExceptionWhenCityDoesNotExist() {

        MaterialRequest request = buildRequest(999L, LocalDate.now(), LocalDate.now().plusDays(10));

        when(ciudadRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.create(request)
        );

        verify(materialRepository, never()).save(any(Material.class));
    }

    @Test
    void shouldThrowExceptionWhenPurchaseDateIsGreaterThanSaleDate() {

        MaterialRequest request = buildRequest(1L, LocalDate.now(), LocalDate.now().minusDays(1));

        assertThrows(
                BusinessException.class,
                () -> materialService.create(request)
        );

        verifyNoInteractions(ciudadRepository, materialRepository);
    }

    @Test
    void shouldCreateMaterialWhenFechaVentaIsNull() {

        MaterialRequest request = buildRequest(1L, LocalDate.now(), null);

        when(ciudadRepository.findById(1L))
                .thenReturn(Optional.of(ciudad));
        when(materialRepository.save(any(Material.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MaterialResponse response = materialService.create(request);

        assertNull(response.fechaVenta());
        verify(materialRepository, times(1)).save(any(Material.class));
    }

    @Test
    void shouldReturnAllMaterials() {

        Material material = Material.builder()
                .id(1L)
                .nombre("Monitor Samsung")
                .tipo(TipoMaterial.MONITOR)
                .precio(new BigDecimal("500000"))
                .fechaCompra(LocalDate.now())
                .estado(EstadoMaterial.DISPONIBLE)
                .ciudad(ciudad)
                .build();

        when(materialRepository.findAll()).thenReturn(List.of(material));

        List<MaterialResponse> result = materialService.findAll();

        assertEquals(1, result.size());
        assertEquals("Monitor Samsung", result.get(0).nombre());
    }

    @Test
    void shouldThrowExceptionWhenNoMaterialsFoundByTipo() {

        when(materialRepository.findByTipo(TipoMaterial.MONITOR)).thenReturn(List.of());

        assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.findByTipo("MONITOR")
        );
    }

    @Test
    void shouldReturnMaterialsByTipo() {

        Material material = Material.builder()
                .id(1L)
                .nombre("Monitor Samsung")
                .tipo(TipoMaterial.MONITOR)
                .precio(new BigDecimal("500000"))
                .fechaCompra(LocalDate.now())
                .estado(EstadoMaterial.DISPONIBLE)
                .ciudad(ciudad)
                .build();

        when(materialRepository.findByTipo(TipoMaterial.MONITOR)).thenReturn(List.of(material));

        List<MaterialResponse> result = materialService.findByTipo("MONITOR");

        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowExceptionWhenNoMaterialsFoundByFechaCompra() {

        LocalDate fecha = LocalDate.now();
        when(materialRepository.findByFechaCompra(fecha)).thenReturn(List.of());

        assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.findByFechaCompra(fecha)
        );
    }

    @Test
    void shouldThrowExceptionWhenNoMaterialsFoundByCiudad() {

        when(materialRepository.findByCiudadId(1L)).thenReturn(List.of());

        assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.findByCiudad(1L)
        );
    }

    @Test
    void shouldUpdateMaterialSuccessfully() {

        Material existing = Material.builder()
                .id(1L)
                .nombre("Monitor Samsung")
                .tipo(TipoMaterial.MONITOR)
                .precio(new BigDecimal("500000"))
                .fechaCompra(LocalDate.now())
                .estado(EstadoMaterial.DISPONIBLE)
                .ciudad(ciudad)
                .build();

        MaterialRequest request = buildRequest(1L, LocalDate.now(), LocalDate.now().plusDays(5));

        when(materialRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(ciudadRepository.findById(1L)).thenReturn(Optional.of(ciudad));
        when(materialRepository.save(any(Material.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MaterialResponse response = materialService.update(1L, request);

        assertNotNull(response);
        verify(materialRepository, times(1)).save(any(Material.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentMaterial() {

        MaterialRequest request = buildRequest(1L, LocalDate.now(), LocalDate.now().plusDays(5));

        when(materialRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.update(99L, request)
        );
    }
}