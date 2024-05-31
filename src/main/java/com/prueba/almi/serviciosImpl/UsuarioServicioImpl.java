package com.prueba.almi.serviciosImpl;
import com.prueba.almi.dto.ConsultaCreditoDto;
import com.prueba.almi.dto.UsuarioAdministradorDto;
import com.prueba.almi.excepciones.LoginExcepcion;
import com.prueba.almi.modelos.Creditos;
import com.prueba.almi.modelos.Cuotas;
import com.prueba.almi.repositorios.ICreditoRepositorio;
import com.prueba.almi.repositorios.ICuotasRepositorio;
import com.prueba.almi.repositorios.IUsuarioRepositorio;
import com.prueba.almi.servicios.UsuarioServicio;
import com.prueba.almi.utilitarios.Constantes;
import com.prueba.almi.utilitarios.JwtUtilidad;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private final IUsuarioRepositorio usuarioRepositorio;
    private final ICreditoRepositorio creditosRepositorio;
    private final ICuotasRepositorio cuotasRepositorio;
    private final JwtUtilidad jwtUtil;

    public UsuarioServicioImpl(IUsuarioRepositorio usuarioRepositorio,
                               ICreditoRepositorio creditosRepositorio,
                               ICuotasRepositorio cuotasRepositorio, JwtUtilidad jwtUtil) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.creditosRepositorio = creditosRepositorio;
        this.cuotasRepositorio = cuotasRepositorio;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String login(UsuarioAdministradorDto usuarioAdmin) throws NoSuchAlgorithmException {
        if (!usuarioRepositorio.existsByEmailAndContraseña(usuarioAdmin.getEmail(), usuarioAdmin.getContraseña())) {
            throw new LoginExcepcion(Constantes.CredencialesError.CREDENCIALES_INVALIDAS_LOGIN);
        }
        String token = generateToken(usuarioAdmin.getEmail());
        Boolean validaToken = validateToken(token);

        if (validaToken){
            return token;
        }
        return Constantes.CredencialesError.TOKEN_NO_VALIDO;
    }

    @Override
    public List<ConsultaCreditoDto> consultarCreditosDelCliente(String numeroIdentificacion) {
            List<ConsultaCreditoDto> creditosYCuotasLista = new ArrayList<>();

            List<Creditos> creditosDelCliente = creditosRepositorio.findByNumeroIdentificacion(numeroIdentificacion);

            for (Creditos credito : creditosDelCliente) {
                ConsultaCreditoDto creditoYCuotas = new ConsultaCreditoDto();
                creditoYCuotas.setMontoDelCredito(credito.getMonto());
                creditoYCuotas.setFechaInicioCredito(credito.getFechaInicio());
                creditoYCuotas.setFechaFinCredito(credito.getFechaFin());
                creditoYCuotas.setNumeroIdentificacion(credito.getNumeroIdentificacion());
                creditoYCuotas.setEstadoDelCredito(credito.getEstadoCredito());

                List<Cuotas> cuotasDelCredito = cuotasRepositorio.findByNumeroIdentificacionAndIdCredito(numeroIdentificacion, credito.getId());
                List<Cuotas> cuotas = new ArrayList<>();

                for (Cuotas cuota : cuotasDelCredito) {
                    Cuotas cuotaNueva = new Cuotas();
                    cuotaNueva.setId(cuota.getId());
                    cuotaNueva.setNumeroIdentificacion(cuota.getNumeroIdentificacion());
                    cuotaNueva.setIdCredito(cuota.getIdCredito());
                    cuotaNueva.setMontoCapital(cuota.getMontoCapital());
                    cuotaNueva.setMontoTotal(cuota.getMontoTotal());
                    cuotaNueva.setFechaPago(cuota.getFechaPago());
                    cuotaNueva.setTotalCuotaFija(cuota.getTotalCuotaFija());
                    cuotaNueva.setEstadoDeCuota(cuota.getEstadoDeCuota());
                    cuotaNueva.setInteres(cuota.getInteres());
                    cuotas.add(cuotaNueva);
                }

                creditoYCuotas.setCuotasDelCredito(cuotas);
                creditosYCuotasLista.add(creditoYCuotas);
            }

            return creditosYCuotasLista;


    }

    public String generateToken(String username) throws NoSuchAlgorithmException {
        return jwtUtil.generateToken(username);
    }

    public boolean validateToken(String token) {
        try {
            return jwtUtil.validateToken(token);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new TokenValidationException("Error al validar el token: " + e.getMessage());
        }
    }

    public class TokenValidationException extends RuntimeException {

        public TokenValidationException(String message) {
            super(message);
        }

        public TokenValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
