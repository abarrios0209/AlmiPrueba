package com.prueba.almi.servicios;

import com.prueba.almi.dto.ConsultaCreditoDto;
import com.prueba.almi.dto.CreditosDto;
import com.prueba.almi.dto.CuotasDto;
import com.prueba.almi.dto.PagoCuotaDto;


public interface CreditosService {

    ConsultaCreditoDto calcularCuotas(CreditosDto credito);

    CuotasDto pagarCuota(PagoCuotaDto datosCuota);

}
