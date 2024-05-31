package com.prueba.almi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditosDto {

    private String numeroIdentificacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double monto;
    private String estadoCredito;

}
