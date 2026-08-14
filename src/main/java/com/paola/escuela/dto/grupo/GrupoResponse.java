package com.paola.escuela.dto.grupo;

import com.paola.escuela.dto.datos.DatosAula;
import com.paola.escuela.dto.datos.DatosCurso;
import com.paola.escuela.dto.datos.DatosMaestro;

import java.util.List;

public record GrupoResponse(

        Long id,
        List<DatosCurso> curso,
        List<DatosMaestro> maestro,
        List<DatosAula> aula,
        List<String> horarios,
        String periodo
) {
}
