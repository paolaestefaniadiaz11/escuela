package com.paola.escuela.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CALIFICACIONES")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALIFICACION")
    private Long id;

    @Column(name = "CALIFICACION", nullable = false)
    private BigDecimal calificacion;

    //entre inscripion y calificacion
    @OneToOne
    @JoinColumn(name = "ID_INSCRIPCION", nullable = false, unique = true)
    private Inscripcion inscripcion;

    @Builder.Default
    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDate fechaRegistro = LocalDate.now();


    public void actualizar(Inscripcion inscripcion, BigDecimal calificacion){
        this.inscripcion = inscripcion;
        this.calificacion = calificacion;
    }
}
