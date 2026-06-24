package com.elizabeth.material_management.repository;


import com.elizabeth.material_management.entity.Material;
import com.elizabeth.material_management.entity.TipoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    List<Material> findByTipo(TipoMaterial tipo);

    List<Material> findByFechaCompra(LocalDate fechaCompra);

    List<Material> findByCiudadId(Long ciudadId);

}
