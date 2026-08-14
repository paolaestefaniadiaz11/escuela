package com.paola.escuela.repositories;

import com.paola.escuela.entities.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaRepository extends JpaRepository<Aula, Long > {

    boolean existsByNombre(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    boolean existsById(Long id);
}
