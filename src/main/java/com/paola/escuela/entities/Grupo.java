package com.paola.escuela.entities;

import com.paola.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "GRUPOS", uniqueConstraints = @UniqueConstraint
        (name= "GRUPO_CU_MA_AU_PE_UK",
                columnNames = {"ID_CURSO","ID_MAESTRO","ID_AULA","PERIODO"}
        ))
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MAESTRO", nullable = false)
    private Maestro maestro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AULA", nullable = false)
    private Aula aula;

    @OneToMany(mappedBy = "grupo") // o @ManyToMany
    private List<Horario> horarios;

    @Column(name = "PERIODO",nullable = false)
    private String periodo;


    public void validarDatos(String periodo){
        StringCustomUtils.validarTamanio(periodo, 1, 20,
                "El periodo del grupo es requerido y debe tener entre 1 y 20 caracteres");

    }

    public void actualizar(Curso curso, Maestro maestro, Aula aula, String periodo) {
        this.curso = curso;
        this.maestro = maestro;
        this.aula = aula;
        this.periodo = periodo;
    }

}
