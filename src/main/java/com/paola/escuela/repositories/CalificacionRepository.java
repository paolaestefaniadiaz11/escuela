package com.paola.escuela.repositories;

import com.paola.escuela.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    boolean existsById(Long id);

    boolean existsByInscripcion_Id(Long id);

    boolean existsByInscripcion_IdAndIdNot(Long idInscripcion, BigDecimal calificacion);

}
