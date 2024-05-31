package com.prueba.almi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagoCuotaDto {
    private String numeroIdentificacion;
    private Long idCuota;
    private Long idCredito;
}
