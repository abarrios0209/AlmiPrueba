package com.prueba.almi.servicios;

import com.prueba.almi.dto.ClientesDto;
import com.prueba.almi.dto.EstadoClienteDto;

public interface ClienteService {

    String crearCliente(ClientesDto cliente);

    ClientesDto actualizarCliente(ClientesDto cliente);

    Boolean eliminarCliente(String numeroIdentificacion);

    String activarODesactivarCliente(EstadoClienteDto cliente);

    String definirTipoRiesgo(String numeroIdentificacion);

}
