package com.paola.escuela.dto.alumnos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AlumnosRequest(
        /*@NotNull(message = "Id de alumno requerido")
        @Positive(message = "Id de alumno debe ser positivo")
        Long idAlumno,

        @NotNull(message = "Nombre de alumno requerido")
        @Size(min = 1, max =50, message = "El nombre del alumno debe tener de 1-30 caracteres")
        String nombreAlumno,

        @NotNull(message = "Nombre de alumno requerido")
        @Size(min = 1, max =50, message = "El nombre del alumno debe tener de 1-30 caracteres")
        String nombreAlumno,

        */


        //@NotEmpty(message = "Lista de productos requerida. No debe estar vacía")
       // List<@Valid DetalleVentaRequest> productos


) {
}
