package com.paola.escuela.dto.maestro;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MaestroRequest(
        @NotNull(message = "Nombre de idMaestro requerido")
        @Size(min = 1, max =50, message = "El nombre del idMaestro debe tener de 1-50 caracteres")
        String nombre,

        @NotNull(message = "Apellido Paterno de idMaestro requerido")
        @Size(min = 1, max =50, message = "El Apellido Paterno del idMaestro debe tener de 1-50 caracteres")
        String apellidoPaterno,

        @NotNull(message = "Apellido Materno de idMaestro requerido")
        @Size(min = 1, max =50, message = "El Apellido Materno del idMaestro debe tener de 1-50 caracteres")
        String apellidoMaterno,

        @NotNull(message = "Email de idMaestro requerido")
        @Size(min = 8, max =100, message = "El email del idMaestro debe tener de 1-100 caracteres")
        @Email (message = "El email debe tener un formato valido (ejemplo@dominio.com)")
        String email,


        @NotNull(message = "Nombre de idMaestro requerido")
        @Pattern(regexp = "^[0-9]{10}", message = "El telefono debe tener de 10 caracteres")
        String telefono
) {
}
