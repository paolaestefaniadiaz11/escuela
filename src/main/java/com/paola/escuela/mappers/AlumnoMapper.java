package com.paola.escuela.mappers;

import com.paola.escuela.dto.alumno.AlumnosRequest;
import com.paola.escuela.dto.alumno.AlumnosResponse;
import com.paola.escuela.dto.datos.DatosCalificacion;
import com.paola.escuela.entities.Alumno;
import com.paola.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlumnoMapper implements CommonMapper<AlumnosRequest, AlumnosResponse, Alumno> {

    @Override

    public Alumno requestAEntidad(AlumnosRequest request){
        if (request == null) return null;

        return Alumno.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .build();
    }

    public Alumno requestAEntidad(AlumnosRequest request, String email, String matricula){
        if (request == null) return null;

        Alumno alumno = requestAEntidad(request);

        alumno.asignarDatosAcademicos(email,matricula);

        return alumno;
    }

    @Override

    public AlumnosResponse entidadAResponse(Alumno entidad) {
        if (entidad == null) return null;

        List<DatosCalificacion> calificaciones= entidadADatosCalificacion(entidad);

        return  new AlumnosResponse(
                entidad.getId(),

                String.join("",
                entidad.getNombre(),
                entidad.getApellidoPaterno(),
                entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getMatricula(),
                StringCustomUtils.localDateAString(entidad.getFechaIngreso()),
                calificaciones,
                entidad.calcularPromedio()

        );
    }


    private List <DatosCalificacion> entidadADatosCalificacion (Alumno entidad){
        if(entidad == null || entidad.getInscripcion() == null   || entidad.getInscripcion().isEmpty())
            return List.of();

        return entidad.getInscripcion().stream()
                .map(inscripciones -> new DatosCalificacion(
                        inscripciones.getGrupo().getCurso().getNombre(),
                        inscripciones.getGrupo().getPeriodo(),
                        inscripciones.getCalificacion()!= null
                        ? inscripciones.getCalificacion().getCalificacion()
                                :null

                )).toList();

    }
}
