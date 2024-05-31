package com.prueba.almi.controladores;

import com.prueba.almi.dto.UsuarioAdministradorDto;
import com.prueba.almi.excepciones.LoginExcepcion;
import com.prueba.almi.servicios.UsuarioServicio;
import com.prueba.almi.serviciosImpl.UsuarioServicioImpl;
import com.prueba.almi.utilitarios.Constantes;
import org.springframework.web.bind.annotation.*;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;
    private final UsuarioServicioImpl usuarioServicioImpl;

    public UsuarioControlador(UsuarioServicio usuarioServicio, UsuarioServicioImpl usuarioServicioImpl) {
        this.usuarioServicio = usuarioServicio;
        this.usuarioServicioImpl = usuarioServicioImpl;
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioAdministradorDto usuario) throws NoSuchAlgorithmException {
        return usuarioServicio.login(usuario);
    }

    @GetMapping("/consultarCreditos/{numeroIdentificacion}")
    public Object consultarCreditos(@PathVariable String numeroIdentificacion, @RequestHeader("token") String tokenHeader){
        boolean validaToken = usuarioServicioImpl.validateToken(tokenHeader);
        if (validaToken){
            return usuarioServicio.consultarCreditosDelCliente(numeroIdentificacion);
        }else {
            throw new LoginExcepcion(Constantes.CredencialesError.TOKEN_NO_VALIDO);
        }

    }

}
