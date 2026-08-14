package com.paola.escuela.mappers;

import com.paola.escuela.dto.curso.CursosRequest;
import com.paola.escuela.dto.curso.CursosResponse;
import com.paola.escuela.dto.datos.DatosCurso;
import com.paola.escuela.entities.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements CommonMapper<CursosRequest, CursosResponse, Curso>{

    @Override

    public Curso requestAEntidad(CursosRequest request){
        if (request == null) return null;

        String descripcion = request.descripcion()!= null
                ? request.descripcion().trim(): null;

        return Curso.builder()
                .nombre(request.nombre().trim())
                        .despcripcion(descripcion)
                        .creditos(request.creditos())
                                .build();


    }

    @Override

    public CursosResponse entidadAResponse(Curso entidad) {
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

public DatosCurso entidadADatosCurso(Curso entidad) {
    if (entidad == null) return null;

    String descripcion = entidad.getDespcripcion() == null
            ? "Sin descripcion" : entidad.getDespcripcion();

    return new DatosCurso(
            entidad.getNombre(),
            descripcion,
            entidad.getCreditos()
           );

    }
}

