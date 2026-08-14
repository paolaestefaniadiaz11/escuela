package com.paola.escuela.mappers;

import com.paola.escuela.dto.datos.DatosCurso;
import com.paola.escuela.dto.datos.DatosMaestro;
import com.paola.escuela.dto.maestro.MaestroRequest;
import com.paola.escuela.dto.maestro.MaestroResponse;
import com.paola.escuela.entities.Grupo;
import com.paola.escuela.entities.Maestro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

@AllArgsConstructor

public class MaestroMapper implements CommonMapper<MaestroRequest, MaestroResponse, Maestro> {

    private final CursoMapper cursoMapper;

    @Override

    public Maestro requestAEntidad(MaestroRequest request) {
        if (request == null) return null;

        return Maestro.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .email(request.email().trim())
                .telefono(request.telefono().trim())
                .build();
    }

    @Override
    public MaestroResponse entidadAResponse(Maestro entidad) {

        if (entidad == null) return null;

        List<DatosCurso> cursos = entidadADatosCurso(entidad);

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


    private List<DatosCurso> entidadADatosCurso(Maestro entidad) {
        if (entidad == null) return List.of();

        return entidad.getGrupos().stream()
                .map(Grupo::getCurso)
                .map(cursoMapper::entidadADatosCurso)
                .toList();

    }

    public DatosMaestro entidadADatosMaestro(Maestro maestro) {
        if (maestro == null) return null;

        return new DatosMaestro(
                maestro.getNombre(),
                maestro.getEmail(),
                maestro.getTelefono()

        );


    }

}