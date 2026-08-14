package com.paola.escuela.dto.aula;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AulaRequest(

    @NotNull(message = "Nombre del aula es requerido")
    @Size(min = 1, max =100, message = "El nombre del aula debe tener de 1-100 caracteres")
    String nombre,

    @NotNull(message = "Numero de capacidad del aula es requerido")
    Integer capacidad

){
}
