package com.prueba.almi.controladores;

import com.prueba.almi.dto.ConsultaCreditoDto;
import com.prueba.almi.dto.CreditosDto;
import com.prueba.almi.dto.PagoCuotaDto;
import com.prueba.almi.excepciones.LoginExcepcion;
import com.prueba.almi.servicios.CreditosService;
import com.prueba.almi.serviciosImpl.UsuarioServicioImpl;
import com.prueba.almi.utilitarios.Constantes;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/creditos")
public class CreditosControlador {

    private final CreditosService creditosService;
    private final UsuarioServicioImpl usuarioServicio;

    public CreditosControlador(CreditosService creditosService, UsuarioServicioImpl usuarioServicio) {
        this.creditosService = creditosService;
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/crearCredito")
    public ConsultaCreditoDto crearCredito(@RequestBody CreditosDto credito,@RequestHeader("token") String tokenHeader){
        boolean validaToken = usuarioServicio.validateToken(tokenHeader);
        if(validaToken){
            return creditosService.calcularCuotas(credito);
        }else {
            throw new LoginExcepcion(Constantes.CredencialesError.TOKEN_NO_VALIDO);
        }

    }

    @PostMapping("/pagarCuota")
    public Object pagarCredito(@RequestBody PagoCuotaDto pagoCuota,@RequestHeader("token") String tokenHeader){
        boolean validaToken = usuarioServicio.validateToken(tokenHeader);
        if(validaToken){
            return creditosService.pagarCuota(pagoCuota);
        }else{
            throw new LoginExcepcion(Constantes.CredencialesError.TOKEN_NO_VALIDO);
        }

    }
}
