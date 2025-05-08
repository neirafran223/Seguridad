package com.cosanostra.seguridad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table (name = "Empleado")
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 13, nullable = false)
    private String run;

    @Column(unique = false)
    private String nombreCompleto;

    @Column(unique = false)
    private int edad;

    @Column(unique = false)
    private String empresaOrigen;

    @Column(unique = false)
    private String correo;

    @Column(unique = false)
    private int numero;

    @Column(unique = false)
    private String tipoSeguridad;

}
