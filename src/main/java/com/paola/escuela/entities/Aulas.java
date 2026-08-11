package com.paola.escuela.entities;

import com.paola.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AULAS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Aulas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    public void validarDatos(String nombre
            //, Integer capacidad
                             ){
        StringCustomUtils.validarTamanio(nombre, 1, 100,
                "El nombre del aula es requerido y debe tener entre 1 y 100 caracteres");

        //StringCustomUtils.validarTamanio(capacidad,1, 4,
              //  "La capacidad es requerida y debe tener exactamente 10 caracteres");

    }

    public void actualizar (String nombre, Integer capacidad){

        validarDatos(nombre);

        this.nombre =nombre.trim();
        this.capacidad = capacidad;



    }

}
