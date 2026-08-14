package com.paola.escuela.dto.calificacion;

import com.paola.escuela.dto.datos.DatosInscripcion;

import java.math.BigDecimal;

public record CalificacionResponse (
        Long id,
        DatosInscripcion inscripcion,
        BigDecimal calificacion,
        String fechaRegistro
){
}
