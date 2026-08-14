package com.paola.escuela.dto.inscripciones;

import jakarta.validation.constraints.NotNull;

public record InscripcionRequest(

        @NotNull(message = "Id del alumno requerido")
        Long idAlumno,

        @NotNull(message = "Id del grupo requerido")
        Long idGrupo
) {
}
