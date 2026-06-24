package com.elizabeth.material_management.repository;

import com.elizabeth.material_management.entity.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    List<Ciudad> findByDepartamentoId(Long departamentoId);

}
