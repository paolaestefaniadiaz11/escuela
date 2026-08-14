package com.paola.escuela.dto.grupo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record GrupoRequest(
    @NotNull(message = "Id del curso requerido")
    Long idCurso,

    @NotNull(message = "Id del idMaestro requerido")
    Long idMaestro,

    @NotNull(message = "Id del aula requerido")
    Long idAula,

    @NotNull(message = "Periodo de grupo requerido")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "El periodo debe tener el formato YYYY-MM")
    String periodo

    ) {

}
