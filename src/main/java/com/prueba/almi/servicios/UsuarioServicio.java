package com.prueba.almi.servicios;
import com.prueba.almi.dto.ConsultaCreditoDto;
import com.prueba.almi.dto.UsuarioAdministradorDto;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UsuarioServicio {

    String login(UsuarioAdministradorDto usuarioAdmin) throws NoSuchAlgorithmException;

    List<ConsultaCreditoDto> consultarCreditosDelCliente(String numeroIdentificacion);

}
