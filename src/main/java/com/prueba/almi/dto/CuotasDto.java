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
public class CuotasDto {

    private String numeroIdentificacion;
    private LocalDate fechaPago;
    private double montoCapital;
    private float interes;
    private Long idCredito;
    private double montoTotal;
    private String estadoDeCuota;
    private double totalCuotaFija;

}
