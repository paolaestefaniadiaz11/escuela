package com.paola.escuela.repositories;

import com.paola.escuela.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HorarioRepository extends JpaRepository<Horario, Long > {

    boolean existsByGrupo_Id(Long idGrupo);


        @Query(nativeQuery = true,
        value = """
        SELECT COUNT(*) FROM HORARIOS
                               WHERE ID_GRUPO = :idGrupo
                                 AND DIA = :dia
                                 AND (
                                     (:horaInicio < HORA_FIN AND :horaFin > HORA_INICIO)
                                 );
    """)
        int existsSolapamientoHorario(
                @Param("idGrupo") Long idGrupo,
                @Param("dia") String diaSemana,
                @Param("horaInicio") String horaInicio,
                @Param("horaFin") String horaFin
        );

}
