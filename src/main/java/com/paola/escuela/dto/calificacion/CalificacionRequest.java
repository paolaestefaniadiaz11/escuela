package com.paola.escuela.dto.calificacion;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CalificacionRequest(

        @NotNull(message = "Id de la inscripcion es requerido")
        Long idInscripcion,

        @NotNull(message = "La calificacion es requerida")
        @Min(value = 0, message = "La calificación mínima permitida es 0")
        @Max(value = 10, message = "La calificación máxima permitida es 10")
        BigDecimal calificacion
) {
}
