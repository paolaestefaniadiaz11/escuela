package com.paola.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "INSCRIPCIONES", uniqueConstraints = @UniqueConstraint
        (name= "INSCRIPCION_ALU_GRU_UK",
                columnNames = {"ID_ALUMNO","ID_GRUPO"}
        ))
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Inscripciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSCRIPCION")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUMNO", nullable = false)
    private Alumnos alumnos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupos grupos;

    @Builder.Default
    @Column(name = "FECHA_INSCRIPCION")
    private LocalDate fechaInscripcion = LocalDate.now();

    @OneToOne(mappedBy = "inscripciones")
    private Calificaciones calificaciones;
}
