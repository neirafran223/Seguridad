package com.cosanostra.seguridad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//ifjerifheiuf
@Entity
@Data
@Table (name = "Empleado")
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = false)
    private String nombreEmpresa;

    @Column(unique = false)
    private int cantPersonal;

    @Column(unique = false)
    private String tipoSeguridad;
}
