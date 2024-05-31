package com.prueba.almi.serviciosImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.almi.dto.ConsultaCreditoDto;
import com.prueba.almi.dto.CreditosDto;
import com.prueba.almi.dto.CuotasDto;
import com.prueba.almi.dto.PagoCuotaDto;
import com.prueba.almi.excepciones.ClienteExcepcion;
import com.prueba.almi.modelos.Clientes;
import com.prueba.almi.modelos.Creditos;
import com.prueba.almi.modelos.Cuotas;
import com.prueba.almi.repositorios.IClienteRepositorio;
import com.prueba.almi.repositorios.ICreditoRepositorio;
import com.prueba.almi.repositorios.ICuotasRepositorio;
import com.prueba.almi.servicios.ClienteService;
import com.prueba.almi.servicios.CreditosService;
import com.prueba.almi.utilitarios.Constantes;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreditosServiceImpl implements CreditosService {

    private final ICreditoRepositorio creditoRepositorio;
    private final ClienteService clienteService;
    private final IClienteRepositorio clienteRepositorio;
    private final ICuotasRepositorio cuotasRepositorio;
    private ObjectMapper objectMapper;
    float tasaInteres;

    public CreditosServiceImpl(ObjectMapper objectMapper, ICuotasRepositorio cuotasRepositorio, ClienteService clienteService, ICreditoRepositorio creditoRepositorio, IClienteRepositorio clienteRepositorio) {
        this.objectMapper = objectMapper;
        this.cuotasRepositorio = cuotasRepositorio;
        this.clienteService = clienteService;
        this.creditoRepositorio = creditoRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public ConsultaCreditoDto calcularCuotas(CreditosDto credito) {
        Clientes clienteExiste = clienteRepositorio.findByNumeroIdentificacion(credito.getNumeroIdentificacion());
        if(clienteExiste !=null){
            credito.setEstadoCredito(Constantes.EstadosCreditoYCuotas.PENDIENTE);
            Creditos creditoEntidad = objectMapper.convertValue(credito,Creditos.class);
            Creditos creditoGuardado = creditoRepositorio.save(creditoEntidad);
            LocalDate fechaInicio = credito.getFechaInicio();
            LocalDate fechaFin = credito.getFechaFin();
            Period periodo = Period.between(fechaInicio, fechaFin);
            double totalMeses = periodo.getMonths();
            double montoCapital = credito.getMonto();
            double montoPorCuota = montoCapital/totalMeses;
            String tipoRiesgo = clienteService.definirTipoRiesgo(credito.getNumeroIdentificacion());
            switch (tipoRiesgo) {
                case Constantes.TiposDeRiesgo.TIPO_A -> tasaInteres = 0.03F;
                case Constantes.TiposDeRiesgo.TIPO_B -> tasaInteres = 0.02F;
                case Constantes.TiposDeRiesgo.TIPO_C -> tasaInteres = 0.01F;
            }
            List<Cuotas> cuotas = new ArrayList<>();
            LocalDate fechaCuota = credito.getFechaInicio().plusMonths(1);
            double cuotaFija = 0;
            for (int i = 0; i < totalMeses; i++) {
                Cuotas cuota = new Cuotas();
                if( i >= 6 ){
                    tasaInteres += 0.001F;
                }
                double interes = montoCapital * tasaInteres;
                cuota.setNumeroIdentificacion(credito.getNumeroIdentificacion());
                cuota.setMontoTotal(montoPorCuota + interes);
                cuotaFija += cuota.getMontoTotal();
                cuota.setMontoCapital(montoCapital);
                cuota.setIdCredito(creditoGuardado.getId());
                cuota.setInteres(tasaInteres);
                cuota.setFechaPago(fechaCuota);
                cuota.setEstadoDeCuota(Constantes.EstadosCreditoYCuotas.PENDIENTE);

                cuotas.add(cuota);
                fechaCuota = fechaCuota.plusMonths(1);
                montoCapital -= montoPorCuota;
            }
            double finalCuotaFija = cuotaFija/totalMeses;
            cuotas.forEach(x -> x.setTotalCuotaFija(finalCuotaFija));
            cuotasRepositorio.saveAll(cuotas);

            ConsultaCreditoDto creditoCreado = new ConsultaCreditoDto();
            creditoCreado.setNumeroIdentificacion(credito.getNumeroIdentificacion());
            creditoCreado.setMontoDelCredito(credito.getMonto());
            creditoCreado.setFechaInicioCredito(credito.getFechaInicio());
            creditoCreado.setFechaFinCredito(credito.getFechaFin());
            creditoCreado.setEstadoDelCredito(credito.getEstadoCredito());
            creditoCreado.setCuotasDelCredito(cuotas);

            return creditoCreado;
        }else{
            throw new ClienteExcepcion(Constantes.CredencialesError.CLIENTE_NO_EXISTE_PARA_CREDITO);
        }

    }

    @Override
    public CuotasDto pagarCuota(PagoCuotaDto datosCuota) {
        Creditos creditoExiste = creditoRepositorio.findByNumeroIdentificacionAndId(datosCuota.getNumeroIdentificacion(),datosCuota.getIdCredito());
        if(creditoExiste == null ){
            throw new ClienteExcepcion(Constantes.CredencialesError.NO_SE_ENCONTRO_CREDITO_A_PAGAR);
        }
        if(creditoExiste.getEstadoCredito().equals(Constantes.EstadosCreditoYCuotas.PAGADO)){
            throw new ClienteExcepcion(Constantes.CredencialesError.EL_CREDITO_YA_ESTA_PAGO);
        }else{
            Cuotas cuotaEncontrada = cuotasRepositorio.findByNumeroIdentificacionAndIdCreditoAndId(datosCuota.getNumeroIdentificacion(), datosCuota.getIdCredito(), datosCuota.getIdCuota());
            if (cuotaEncontrada == null || cuotaEncontrada.getEstadoDeCuota().equals(Constantes.EstadosCreditoYCuotas.PAGADA)) {
                throw new ClienteExcepcion(Constantes.CredencialesError.CUOTA_NO_EXISTE_O_PAGADA);
            } else if (cuotaEncontrada.getEstadoDeCuota().equals(Constantes.EstadosCreditoYCuotas.PENDIENTE)) {
                cuotaEncontrada.setEstadoDeCuota(Constantes.EstadosCreditoYCuotas.PAGADA);
                cuotasRepositorio.save(cuotaEncontrada);
                List<Cuotas> cuotasExistentes = cuotasRepositorio.findByNumeroIdentificacionAndIdCredito(datosCuota.getNumeroIdentificacion(),datosCuota.getIdCredito());
                boolean algunaPendiente = cuotasExistentes.stream().anyMatch(cuota -> cuota.getEstadoDeCuota().equals(Constantes.EstadosCreditoYCuotas.PENDIENTE));
                if (!algunaPendiente) {
                    creditoExiste.setEstadoCredito(Constantes.EstadosCreditoYCuotas.PAGADO);
                    creditoRepositorio.save(creditoExiste);
                }
                return objectMapper.convertValue(cuotaEncontrada,CuotasDto.class);
            }
        }
        return null;
    }


}
