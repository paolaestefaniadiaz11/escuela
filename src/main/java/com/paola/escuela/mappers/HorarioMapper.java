package com.paola.escuela.mappers;

import com.paola.escuela.dto.datos.DatosGrupo;
import com.paola.escuela.dto.horario.HorarioRequest;
import com.paola.escuela.dto.horario.HorarioResponse;
import com.paola.escuela.entities.*;
import com.paola.escuela.enums.DiaSemana;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class HorarioMapper implements CommonMapper <HorarioRequest, HorarioResponse, Horario>{

    private final GrupoMapper grupoMapper;

    @Override

    public Horario requestAEntidad(HorarioRequest request) {

        if (request == null) return null;

        return Horario.builder()
                .grupo(request.idGrupo() != null ? Grupo.builder().id(request.idGrupo()).build() : null)
                .dia(request.dia() != null ? DiaSemana.valueOf(request.dia().trim().toUpperCase()) : null)
                .horaInicio(request.horaInicio())
                .horaFin(request.horaFin())
                .build();
    }

    @Override
    public HorarioResponse entidadAResponse (Horario entidad){

        if (entidad ==null) return null;

        Grupo grupo = entidad.getGrupo();

        String nombreCurso = (grupo.getCurso() != null) ? grupo.getCurso().getNombre() : null;

        String nombreMaestro = (grupo.getMaestro() != null)
                ? grupo.getMaestro().getNombre() + " " + grupo.getMaestro().getApellidoPaterno() + " " + grupo.getMaestro().getApellidoMaterno()
                : null;

        String nombreAula = (grupo.getAula() != null) ? grupo.getAula().getNombre() : null;


        DatosGrupo datosGrupo = new DatosGrupo(
                nombreCurso,
                nombreMaestro,
                nombreAula,
                grupo.getPeriodo()
        );

        String diaFormateado = (entidad.getDia() != null) ? entidad.getDia().getDescripcion() : "";

        String cadenaHorario = String.format("%s %s %s",
                diaFormateado,
                entidad.getHoraInicio() != null ? entidad.getHoraInicio() : "",
                entidad.getHoraFin() != null ? entidad.getHoraFin() : ""
        ).trim();

        return new HorarioResponse(
                entidad.getId(),
                datosGrupo,
                cadenaHorario
        );

    }

}
