package com.paola.escuela.mappers;

import com.paola.escuela.dto.calificacion.CalificacionRequest;
import com.paola.escuela.dto.calificacion.CalificacionResponse;
import com.paola.escuela.dto.datos.*;
import com.paola.escuela.entities.Alumno;
import com.paola.escuela.entities.Calificacion;
import com.paola.escuela.entities.Grupo;
import com.paola.escuela.entities.Inscripcion;
import com.paola.escuela.utils.StringCustomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalificacionMapper implements CommonMapper <CalificacionRequest, CalificacionResponse, Calificacion> {

    @Override

    public Calificacion requestAEntidad(CalificacionRequest request) {

        if (request == null) return null;

        return Calificacion.builder()
                .inscripcion(request.idInscripcion() != null ? Inscripcion.builder().id(request.idInscripcion()).build() : null)
                .calificacion(request.calificacion())
                .build();
    }

    @Override
    public CalificacionResponse entidadAResponse (Calificacion entidad) {

        if (entidad == null) return null;

        DatosInscripcion datosInscripcion = null;

        if (entidad.getInscripcion() != null) {
            Inscripcion inscripcion = entidad.getInscripcion();

            Alumno alumno = inscripcion.getAlumno();

            String nombreAlumno = (alumno != null)
                    ? alumno.getNombre() + " " + alumno.getApellidoPaterno() + " " + alumno.getApellidoMaterno()
                    : null;

            DatosAlumno alumnoData = (alumno != null)
                    ? new DatosAlumno(
                    nombreAlumno,
                    alumno.getMatricula(),
                    alumno.getEmail(),
                    StringCustomUtils.localDateAString(alumno.getFechaIngreso())
            )
                    : null;

            Grupo grupo = inscripcion.getGrupo();

            String nombreCurso = (grupo != null && grupo.getCurso() != null)
                    ? grupo.getCurso().getNombre()
                    : null;

            String nombreMaestro = (grupo != null && grupo.getMaestro() != null)
                    ? grupo.getMaestro().getNombre() + " " + grupo.getMaestro().getApellidoPaterno() + " " + grupo.getMaestro().getApellidoMaterno()
                    : null;

            String nombreAula = (grupo != null && grupo.getAula() != null)
                    ? grupo.getAula().getNombre()
                    : null;

            String periodo = (grupo != null) ? grupo.getPeriodo() : null;

            DatosGrupo grupoData = new DatosGrupo(
                    nombreCurso,
                    nombreMaestro,
                    nombreAula,
                    periodo
            );

            datosInscripcion = new DatosInscripcion(
                    alumnoData,
                    grupoData,
                    StringCustomUtils.localDateAString(inscripcion.getFechaInscripcion())
            );
        }

        return new CalificacionResponse(
                entidad.getId(),
                datosInscripcion,
                entidad.getCalificacion(),
                StringCustomUtils.localDateAString(entidad.getFechaRegistro())
        );
        }


    }
