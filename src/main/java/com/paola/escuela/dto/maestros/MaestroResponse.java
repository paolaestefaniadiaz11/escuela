package com.paola.escuela.dto.maestros;

import com.paola.escuela.dto.datos.DatosCursos;

import java.util.List;

public record MaestroResponse(

        Long id,
        String nombre,
        String email,
        String telefono,
        List<DatosCursos> cursos

) {
}
