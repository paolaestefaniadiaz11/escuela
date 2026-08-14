package com.paola.escuela.dto.maestro;

import com.paola.escuela.dto.datos.DatosCurso;

import java.util.List;

public record MaestroResponse(

        Long id,
        String nombre,
        String email,
        String telefono,
        List<DatosCurso> cursos

) {
}
