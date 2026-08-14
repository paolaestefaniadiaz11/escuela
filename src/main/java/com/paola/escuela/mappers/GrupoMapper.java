package com.paola.escuela.mappers;

import com.paola.escuela.dto.datos.DatosAula;
import com.paola.escuela.dto.datos.DatosCurso;
import com.paola.escuela.dto.datos.DatosMaestro;
import com.paola.escuela.dto.grupo.GrupoRequest;
import com.paola.escuela.dto.grupo.GrupoResponse;
import com.paola.escuela.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GrupoMapper implements CommonMapper <GrupoRequest, GrupoResponse, Grupo>{

    private final CursoMapper cursoMapper;
    private final MaestroMapper maestroMapper;
    private final AulaMapper aulaMapper;


    @Override

    public Grupo requestAEntidad(GrupoRequest request) {

        if (request == null) return null;

        return Grupo.builder()
                .curso(request.idCurso() != null ? Curso.builder().id(request.idCurso()).build() : null)

                .maestro(request.idMaestro() != null ? Maestro.builder().id(request.idMaestro()).build() : null)

                .aula(request.idAula() != null ? Aula.builder().id(request.idAula()).build() : null)

                .periodo(request.periodo())

                .build();
    }


    @Override
    public GrupoResponse entidadAResponse (Grupo entidad){

        if (entidad ==null) return null;

        List<DatosCurso> cursos = entidadADatosCurso(entidad);

        List<DatosMaestro> maestros = entidadADatosMaestro(entidad);

        List<DatosAula> aulas = entidadADatosAula(entidad);

        List<String> horarios = entidadADatosHorarios(entidad);

        return new GrupoResponse(
                entidad.getId(),
                cursos,
                maestros,
                aulas,
                horarios,
                entidad.getPeriodo()

        );

    }

    private List<DatosCurso> entidadADatosCurso(Grupo entidad) {
        if (entidad == null || entidad.getCurso() == null) {
            return List.of();
        }

        DatosCurso datosCurso = cursoMapper.entidadADatosCurso(entidad.getCurso());
        return List.of(datosCurso);
    }

    private List<DatosMaestro> entidadADatosMaestro(Grupo entidad) {
        if (entidad == null || entidad.getMaestro() == null) {
            return List.of();
        }

        DatosMaestro datosMaestro = maestroMapper.entidadADatosMaestro(entidad.getMaestro());
        return List.of(datosMaestro);
    }


    private List<DatosAula> entidadADatosAula(Grupo entidad) {
        if (entidad == null || entidad.getAula() == null) {
            return List.of();
        }

        DatosAula datosAula = aulaMapper.entidadADatosAula(entidad.getAula());
        return List.of(datosAula);
    }

    private List<String> entidadADatosHorarios(Grupo entidad) {
        if (entidad == null || entidad.getHorarios() == null || entidad.getHorarios().isEmpty()) {
            return List.of();
        }

        return entidad.getHorarios().stream()
                .map(horario -> horario.getDia() + " " + horario.getHoraInicio() + " - " + horario.getHoraFin())
                .toList();
    }



}
