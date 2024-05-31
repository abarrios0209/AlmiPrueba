package com.prueba.almi.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientesDto {
    private String nombre;
    private String numeroIdentificacion;
    private Date fechaNacimiento;
    private String genero;
    private Long numeroCuenta;
    private String tipoCuenta;
    private String nombreBanco;
    private Date fechaInicioContrato;
    private Boolean esIndependiente;
    private Boolean estado;
}
