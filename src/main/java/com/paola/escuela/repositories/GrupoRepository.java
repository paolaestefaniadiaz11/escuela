package com.paola.escuela.repositories;

import com.paola.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    boolean existsByMaestroId(Long idMaestro);

    boolean existsById(Long id);

    boolean existsByCursoId(Long id);

    boolean existsByCurso_IdAndMaestro_IdAndAula_IdAndPeriodo(
            Long idCurso,
            Long idMaestro,
            Long idAula,
            String periodo
    );

    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(
            Long idCurso,
            Long idMaestro,
            Long idAula,
            String periodo,
            Long id
    );

    @Query(nativeQuery = true,
    value = """
    SELECT COUNT(*)FROM GRUPOS
          WHERE ID_CURSO = :idCurso
            AND ID_MAESTRO = :idMaestro
            AND ID_AULA = :idAula
            AND PERIODO = :periodo;
""")
    int existeCombinacionGrupo(
            @Param("idCurso") Long idCurso,
            @Param("idMaestro") Long idMaestro,
            @Param("idAula") Long idAula,
            @Param("periodo") String periodo
    );



}
