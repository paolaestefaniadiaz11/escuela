package com.paola.escuela.entities;

import com.paola.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MAESTROS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Maestro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MAESTRO")
    private Long id;

    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", length = 50, nullable = false)
    private String apellidoMaterno;

    @Column(name = "EMAIL", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "TELEFONO", length = 10, nullable = false, unique = true)
    private String telefono;

    @Builder.Default
    @OneToMany (mappedBy = "maestro",fetch = FetchType.LAZY)
    private List<Grupo> grupos =  new ArrayList<>();


    private void validarDatos(String nombre, String apellidoPaterno,
                              String apellidoMaterno, String email, String telefono) {


        StringCustomUtils.validarTamanio(nombre, 1, 50,
                "El nombre es requerido y debe tener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoPaterno, 1, 50,
                "El apellido paterno es requerido y debe tener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoMaterno, 1, 50,
                "El apellido materno es requerido y debe tener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(email, 1, 100,
                "El email es requerido y debe tener entre 1 y 100 caracteres");

        StringCustomUtils.validarTamanio(telefono, 10, 10,
                "El telefon es requerido y debe tener exactamente 10 caracteres");


    }

    public void actualizar(String nombre, String apellidoPaterno,
                           String apellidoMaterno, String email, String telefono) {

        validarDatos(nombre, apellidoPaterno, apellidoMaterno, email, telefono);

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
        this.email =email.toLowerCase().trim();
        this.telefono =telefono.trim();


    }




}
