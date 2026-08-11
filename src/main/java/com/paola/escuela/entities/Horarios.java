package com.paola.escuela.entities;

import com.paola.escuela.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "HORARIOS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Horarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupos grupos;

    @Enumerated (EnumType.STRING)
    @Column(name = "DIA",nullable = false)
    private DiaSemana dia;

    @Column(name = "HORA_INICIO", length = 5, nullable = false)
    private String horaInicio;

    @Column(name = "HORA_FIN", length = 5, nullable = false)
    private String horaFin;
}
