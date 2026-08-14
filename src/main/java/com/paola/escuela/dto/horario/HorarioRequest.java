package com.paola.escuela.dto.horario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HorarioRequest(
        @NotNull(message = "El grupo del horario es requerido")
        Long idGrupo,

        @NotNull(message = "El dia del horario es requerido")
        @Size(min = 1, max =15, message = "El nombre del dia debe tener de 1-15 caracteres")
        String dia,

        @NotNull(message = "La hora de inicio es requerida")
        @Size(min = 1, max =5, message = "La hora de inicio debe tener de 1-5 caracteres")
        String horaInicio,

        @NotNull(message = "La hora de fin es requerida")
        @Size(min = 1, max =5, message = "La hora de fin debe tener de 1-5 caracteres")
        String horaFin
) {

}
