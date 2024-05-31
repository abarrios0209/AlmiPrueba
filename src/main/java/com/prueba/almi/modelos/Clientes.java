package com.prueba.almi.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "CLIENTES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "NUMERO_IDENTIFICACION")
    private String numeroIdentificacion;

    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    @Column(name = "GENERO")
    private String genero;

    @Column(name = "NUMERO_CUENTA")
    private Long numeroCuenta;

    @Column(name = "TIPO_CUENTA")
    private String tipoCuenta;

    @Column(name = "NOMBRE_BANCO")
    private String nombreBanco;

    @Column(name = "FECHA_INICIO_CONTRATO")
    private Date fechaInicioContrato;

    @Column(name = "ES_INDEPENDIENTE")
    private Boolean esIndependiente;

    @Column(name = "ESTADO")
    private Boolean estado;
}
