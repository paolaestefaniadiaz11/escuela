package com.paola.escuela.dto.datos;

import java.math.BigDecimal;
import java.util.List;

public record DatosCursos(
        String nombre,
        String descripcion,
        Integer creditos
) {
}
