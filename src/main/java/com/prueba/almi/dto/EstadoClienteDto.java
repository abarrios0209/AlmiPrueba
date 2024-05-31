package com.prueba.almi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoClienteDto {

    private String numeroIdentificacion;
    private Boolean estadoCliente;

}
