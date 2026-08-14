package com.paola.escuela.dto.alumno;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlumnosRequest(
        @NotNull(message = "El nombre de alumno es requerido")
        @Size(min = 1, max =50, message = "El nombre del alumno debe tener de 1-50 caracteres")
        String nombre,

        @NotNull(message = "Apellido paterno de alumno requerido")
        @Size(min = 1, max =50, message = "El apellido paterno del alumno debe tener de 1-50 caracteres")
        String apellidoPaterno,

        @NotNull(message = "Apellido materno de alumno requerido")
        @Size(min = 1, max =50, message = "El apellido materno del alumno debe tener de 1-50 caracteres")
        String apellidoMaterno

) {
}
