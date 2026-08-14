package com.paola.escuela.repositories;

import com.paola.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long > {

    boolean existsByAlumnoId(Long idAlumno);

    boolean existsByGrupo_Id(Long idGrupo);

    boolean existsByAlumno_Id(Long idAlumno);

    long countByGrupo_Id(Long idGrupo);

    boolean existsByAlumno_IdAndGrupo_Id(
            Long idAlumno,
            Long idGrupo
    );

}
