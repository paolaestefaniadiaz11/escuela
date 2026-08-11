package com.paola.escuela.dto.maestros;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MaestroRequest(
        @NotNull(message = "Nombre de maestro requerido")
        @Size(min = 1, max =50, message = "El nombre del maestro debe tener de 1-50 caracteres")
        String nombre,

        @NotNull(message = "Apellido Paterno de maestro requerido")
        @Size(min = 1, max =50, message = "El Apellido Paterno del maestro debe tener de 1-50 caracteres")
        String apellidoPaterno,

        @NotNull(message = "Apellido Materno de maestro requerido")
        @Size(min = 1, max =50, message = "El Apellido Materno del maestro debe tener de 1-50 caracteres")
        String apellidoMaterno,

        @NotNull(message = "Email de maestro requerido")
        @Size(min = 8, max =100, message = "El email del maestro debe tener de 1-100 caracteres")
        @Email (message = "El email debe tener un formato valido (ejemplo@dominio.com)")
        String email,


        @NotNull(message = "Nombre de maestro requerido")
        @Pattern(regexp = "^[0-9]{10}", message = "El telefono debe tener de 10 caracteres")
        String telefono
) {
}
