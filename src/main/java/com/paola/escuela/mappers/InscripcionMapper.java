package com.paola.escuela.mappers;

import com.paola.escuela.dto.datos.DatosAlumno;
import com.paola.escuela.dto.datos.DatosGrupo;
import com.paola.escuela.dto.inscripciones.InscripcionRequest;
import com.paola.escuela.dto.inscripciones.InscripcionResponse;
import com.paola.escuela.entities.Alumno;
import com.paola.escuela.entities.Grupo;
import com.paola.escuela.entities.Inscripcion;
import com.paola.escuela.utils.StringCustomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class InscripcionMapper implements CommonMapper <InscripcionRequest, InscripcionResponse, Inscripcion> {

    @Override

    public Inscripcion requestAEntidad(InscripcionRequest request) {

        if (request == null) return null;

        return Inscripcion.builder()
                .alumno(request.idAlumno() != null ? Alumno.builder().id(request.idAlumno()).build() : null)
                .grupo(request.idGrupo() != null ? Grupo.builder().id(request.idGrupo()).build() : null)
                .build();
    }

    @Override
    public InscripcionResponse entidadAResponse (Inscripcion entidad){

        if (entidad ==null) return null;

            Alumno alumno = entidad.getAlumno();

            String nombreAlumno = (entidad.getAlumno() != null)
                    ? alumno.getNombre() + " " + alumno.getApellidoPaterno() + " " + alumno.getApellidoMaterno()
                    : null;

            DatosAlumno datosAlumno = new DatosAlumno(
                    nombreAlumno,
                    entidad.getAlumno().getMatricula(),
                    entidad.getAlumno().getEmail(),
                    StringCustomUtils.localDateAString(entidad.getAlumno().getFechaIngreso())
            );


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

        BigDecimal calificacion = (entidad.getCalificacion() != null)
                ? entidad.getCalificacion().getCalificacion()
                : null;

            return new InscripcionResponse(
                    entidad.getId(),
                    datosAlumno,
                    datosGrupo,
                    calificacion,
                    StringCustomUtils.localDateAString(entidad.getFechaInscripcion())
            );

        }


    }




