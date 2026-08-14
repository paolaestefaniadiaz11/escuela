package com.paola.escuela.entities;

import com.paola.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CURSOS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter


public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 200, nullable = false)
    private String despcripcion;

    @Column(name = "CREDITOS", nullable = false)
    private Integer creditos;



    public void validarDatos(String nombre, String despcripcion){
        StringCustomUtils.validarTamanio(nombre, 1, 100,
                "El nombre del curso es requerido y debe tener entre 1 y 100 caracteres");

        StringCustomUtils.validarTamanio(despcripcion, 1, 200,
                "La descripcion del curso es requerido y debe tener entre 1 y 200 caracteres");

    }

    public void actualizar (String nombre,  String despcripcion, Integer creditos){

        validarDatos(nombre,despcripcion);

        this.nombre =nombre.trim();
        this.despcripcion =despcripcion.trim();
        this.creditos = creditos;



    }

}
