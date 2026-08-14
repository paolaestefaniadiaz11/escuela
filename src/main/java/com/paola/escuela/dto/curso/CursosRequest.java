package com.paola.escuela.dto.curso;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CursosRequest(
        @NotNull(message = "Nombre del curso requerido")
        @Size(min = 1, max =100, message = "El nombre del curso debe tener de 1-100 caracteres")
        String nombre,

        @Size(min = 1, max =200, message = "La descripcion o debe tener de 1-200 caracteres")
        String descripcion,

        @NotNull(message = "Los creditos son requerido")
        @Min(value = 1, message = "Los creditos minimos con 1")
        @Max(value = 10,message = "Los creditos maximos son 10")
        Integer creditos

) {
}
