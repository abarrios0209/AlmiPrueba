package com.prueba.almi.serviciosImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.almi.dto.ClientesDto;
import com.prueba.almi.dto.EstadoClienteDto;
import com.prueba.almi.excepciones.ClienteExcepcion;
import com.prueba.almi.modelos.Clientes;
import com.prueba.almi.repositorios.IClienteRepositorio;
import com.prueba.almi.servicios.ClienteService;
import com.prueba.almi.utilitarios.Constantes;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Service
public class ClienteServicesImpl implements ClienteService {

    private final IClienteRepositorio clienteRepositorio;
    private final ObjectMapper objectMapper;

    public ClienteServicesImpl(IClienteRepositorio clienteRepositorio, ObjectMapper objectMapper) {
        this.clienteRepositorio = clienteRepositorio;
        this.objectMapper = objectMapper;
    }

    @Override
    public String crearCliente(ClientesDto cliente) {
        Clientes clienteExiste = clienteRepositorio.findByNumeroIdentificacion(cliente.getNumeroIdentificacion());
        if(clienteExiste != null && clienteExiste.getNumeroIdentificacion().equals(cliente.getNumeroIdentificacion())){
            throw new ClienteExcepcion(Constantes.CredencialesError.EL_CLIENTE_YA_EXISTE);
        }else if(clienteExiste == null){
            Clientes clienteEntidad = objectMapper.convertValue(cliente,Clientes.class);
            clienteRepositorio.save(clienteEntidad);
            return Constantes.ClienteRegistrado.CLIENTE_CREADO;
        }
        return null;
    }

    @Override
    public ClientesDto actualizarCliente(ClientesDto cliente) {
        Clientes clienteModificado = new Clientes();
        Clientes clienteExiste = clienteRepositorio.findByNumeroIdentificacion(cliente.getNumeroIdentificacion());
        try {
            clienteModificado = agregarDatosParaActualizar(clienteExiste,cliente);
            ClientesDto clienteDto = objectMapper.convertValue(clienteModificado,ClientesDto.class);
            clienteRepositorio.save(clienteModificado);
            return clienteDto;
        }catch (ClienteExcepcion e){
            throw new ClienteExcepcion(Constantes.CredencialesError.ERROR_ACTUALIZAR_CLIENTE);
        }

    }

    @Override
    public Boolean eliminarCliente(String numeroIdentificacion) {
        Clientes clienteExiste = clienteRepositorio.findByNumeroIdentificacion(numeroIdentificacion);
        if (clienteExiste != null){
            clienteRepositorio.delete(clienteExiste);
            return true;
        }else{
            throw new ClienteExcepcion(Constantes.CredencialesError.NO_HAY_CLIENTE_PARA_ELIMINAR);
        }

    }

    @Override
    public String activarODesactivarCliente(EstadoClienteDto cliente) {
        Clientes clienteExiste = clienteRepositorio.findByNumeroIdentificacion(cliente.getNumeroIdentificacion());
        if( clienteExiste != null ){
            clienteExiste.setEstado(cliente.getEstadoCliente());
            return Constantes.ComplementosRespuesta.CAMBIO_ESTADO + clienteExiste.getEstado();
        }else{
            return Constantes.ComplementosRespuesta.NO_CAMBIO_ESTADO_CLIENTE;
        }

    }

    @Override
    public String definirTipoRiesgo(String numeroIdentificacion) {
        Clientes clienteExiste = clienteRepositorio.findByNumeroIdentificacion(numeroIdentificacion);
        if( clienteExiste != null){
            return calcularTipoRiesgoPersona(clienteExiste);
        }else {
            return Constantes.ComplementosRespuesta.DEFINIR_TIPO_RIESGO_1 + numeroIdentificacion + Constantes.ComplementosRespuesta.DEFINIR_TIPO_RIESGO_2 ;
        }
    }

    private Clientes agregarDatosParaActualizar(Clientes clienteExiste, ClientesDto cliente){
        if (cliente.getNombre() != null && !cliente.getNombre().isEmpty()) {
            clienteExiste.setNombre(cliente.getNombre());
        }
        if (cliente.getFechaNacimiento() != null) {
            clienteExiste.setFechaNacimiento(cliente.getFechaNacimiento());
        }
        if (cliente.getGenero() != null && !cliente.getGenero().isEmpty()) {
            clienteExiste.setGenero(cliente.getGenero());
        }
        if (cliente.getNumeroCuenta() != null && cliente.getNumeroCuenta().toString().isEmpty()) {
            clienteExiste.setNumeroCuenta(cliente.getNumeroCuenta());
        }
        if (cliente.getTipoCuenta() != null && !cliente.getTipoCuenta().isEmpty()) {
            clienteExiste.setTipoCuenta(cliente.getTipoCuenta());
        }
        if (cliente.getNombreBanco() != null && !cliente.getNombreBanco().isEmpty()) {
            clienteExiste.setNombreBanco(cliente.getNombreBanco());
        }
        if (cliente.getFechaInicioContrato() != null) {
            clienteExiste.setFechaInicioContrato(cliente.getFechaInicioContrato());
        }
        if (cliente.getEsIndependiente() != null) {
            clienteExiste.setEsIndependiente(cliente.getEsIndependiente());
        }
        return clienteExiste;
    }

    private String calcularTipoRiesgoPersona(Clientes cliente){
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicioContrato = cliente.getFechaInicioContrato().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period periodo = Period.between(fechaInicioContrato, fechaActual);
        if(periodo.getYears() < 1 || Boolean.TRUE.equals(cliente.getEsIndependiente())){
            return Constantes.TiposDeRiesgo.TIPO_A;
        }else if(periodo.getYears() <= 2){
            return Constantes.TiposDeRiesgo.TIPO_B;
        }else{
            return Constantes.TiposDeRiesgo.TIPO_C;
        }
    }

}
