package com.paola.escuela.mappers;

import com.paola.escuela.dto.datos.DatosCursos;
import com.paola.escuela.dto.maestros.MaestroRequest;
import com.paola.escuela.dto.maestros.MaestroResponse;
import com.paola.escuela.entities.Grupos;
import com.paola.escuela.entities.Maestros;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

@AllArgsConstructor

public class MaestroMapper implements CommonMapper<MaestroRequest, MaestroResponse, Maestros> {

    private final CursoMapper cursoMapper;

    @Override

    public Maestros requestAEntidad(MaestroRequest request){
        if (request == null) return null;

        return Maestros.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .email(request.email().trim())
                .telefono(request.telefono().trim())
                .build();
    }

    @Override
    public MaestroResponse entidadAResponse (Maestros entidad){

        if (entidad ==null) return null;

        List<DatosCursos> cursos = entidadADatosCurso(entidad);

        return new MaestroResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getTelefono(),
                cursos
        );

    }


    private List<DatosCursos> entidadADatosCurso(Maestros entidad){
        if (entidad ==null) return List.of();

        return entidad.getGrupos().stream()
                .map(Grupos::getCurso)
                .map(cursoMapper::entidadADatosCursos)
                .toList();

    }

}
