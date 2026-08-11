package com.paola.escuela.dto.cursos;

import com.paola.escuela.dto.datos.DatosCursos;



public record CursosResponse(
               Long id,
                String nombre,
                String descripcion,
                Integer creditos)

 {
        }