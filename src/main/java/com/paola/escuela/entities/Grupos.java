package com.paola.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "GRUPOS", uniqueConstraints = @UniqueConstraint
        (name= "GRUPO_CU_MA_AU_PE_UK",
                columnNames = {"ID_CURSO","ID_MAESTRO","ID_AULA","PERIODO"}
        ))
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Grupos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Cursos curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MAESTRO", nullable = false)
    private Maestros maestros;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AULA", nullable = false)
    private Aulas aulas;

    @Column(name = "PERIODO",nullable = false)
    private String periodo;
}
