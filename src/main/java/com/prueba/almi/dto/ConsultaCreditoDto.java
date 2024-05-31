package com.prueba.almi.dto;

import com.prueba.almi.modelos.Cuotas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaCreditoDto {

    private String numeroIdentificacion;

    private LocalDate fechaInicioCredito;

    private LocalDate fechaFinCredito;

    private double montoDelCredito;

    private String estadoDelCredito;

    private List<Cuotas> cuotasDelCredito;
}
