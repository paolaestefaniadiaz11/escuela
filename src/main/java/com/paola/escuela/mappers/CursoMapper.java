package com.paola.escuela.mappers;

import com.paola.escuela.dto.CustomErrorResponse;
import com.paola.escuela.dto.cursos.CursosRequest;
import com.paola.escuela.dto.cursos.CursosResponse;
import com.paola.escuela.dto.datos.DatosCursos;
import com.paola.escuela.dto.maestros.MaestroRequest;
import com.paola.escuela.dto.maestros.MaestroResponse;
import com.paola.escuela.entities.Cursos;
import com.paola.escuela.entities.Maestros;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements CommonMapper<CursosRequest, CursosResponse, Cursos>{

    @Override

    public Cursos requestAEntidad(CursosRequest request){
        if (request == null) return null;

        String descripcion = request.descripcion()!= null
                ? request.descripcion().trim(): null;

        return Cursos.builder()
                .nombre(request.nombre().trim())
                        .despcripcion(descripcion)
                        .creditos(request.creditos())
                                .build();


    }

    @Override

    public CursosResponse entidadAResponse(Cursos entidad) {
        if (entidad == null) return null;

        String descripcion = entidad.getDespcripcion() == null
                ? "Sin descripcion" : entidad.getDespcripcion();

        return new CursosResponse(
                entidad.getId(),
                        entidad.getNombre(),
                        descripcion,
                entidad.getCreditos()
        );

}

public DatosCursos entidadADatosCursos(Cursos entidad) {
    if (entidad == null) return null;

    String descripcion = entidad.getDespcripcion() == null
            ? "Sin descripcion" : entidad.getDespcripcion();

    return new DatosCursos(
            entidad.getNombre(),
            descripcion,
            entidad.getCreditos()
           );

    }
}

