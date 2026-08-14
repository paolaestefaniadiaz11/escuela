package com.paola.escuela.dto.inscripciones;

import com.paola.escuela.dto.datos.DatosAlumno;
import com.paola.escuela.dto.datos.DatosGrupo;

import java.math.BigDecimal;

public record InscripcionResponse(
        Long id,
        DatosAlumno alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInscripcion
) {
}
