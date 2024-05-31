package com.prueba.almi.controladores;


import com.prueba.almi.dto.ClientesDto;
import com.prueba.almi.dto.EstadoClienteDto;
import com.prueba.almi.excepciones.LoginExcepcion;
import com.prueba.almi.servicios.ClienteService;
import com.prueba.almi.serviciosImpl.UsuarioServicioImpl;
import com.prueba.almi.utilitarios.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cliente")
public class ClienteControlador {

    private final ClienteService clienteService;
    private final  UsuarioServicioImpl usuarioServicio;

    public ClienteControlador(ClienteService clienteService, UsuarioServicioImpl usuarioServicio) {
        this.clienteService = clienteService;
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/crearCliente")
    public String crearClienteControlador(@RequestBody ClientesDto cliente,@RequestHeader("token") String tokenHeader){
        boolean validaToken = usuarioServicio.validateToken(tokenHeader);
        if (validaToken){
            return clienteService.crearCliente(cliente);
        }else {
            throw new LoginExcepcion(Constantes.CredencialesError.TOKEN_NO_VALIDO);
        }

    }

    @PutMapping("/actualizarCliente")
    public ClientesDto actualizarClienteControlador(@RequestBody ClientesDto cliente,@RequestHeader("token") String tokenHeader){
        boolean validaToken = usuarioServicio.validateToken(tokenHeader);
        if (validaToken){
            return clienteService.actualizarCliente(cliente);
        }else{
            throw new LoginExcepcion(Constantes.CredencialesError.TOKEN_NO_VALIDO);
        }

    }

    @DeleteMapping("/eliminarCliente/{numeroIdentificacion}")
    public ResponseEntity<?> eliminarClienteController(@PathVariable String numeroIdentificacion,@RequestHeader("token") String tokenHeader){
        boolean validaToken = usuarioServicio.validateToken(tokenHeader);
        if (validaToken){
            clienteService.eliminarCliente(numeroIdentificacion);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuario Eliminado Correctamente");
        }else{
            throw new LoginExcepcion(Constantes.CredencialesError.TOKEN_NO_VALIDO);
        }

    }

    @PutMapping("/activarODesactivar")
    public String activarODesactivar(@RequestBody EstadoClienteDto cliente,@RequestHeader("token") String tokenHeader){
        boolean validaToken = usuarioServicio.validateToken(tokenHeader);
        if(validaToken){
            return clienteService.activarODesactivarCliente(cliente);
        }else {
            throw new LoginExcepcion(Constantes.CredencialesError.TOKEN_NO_VALIDO);
        }

    }

    @GetMapping("/consultarTipoRiesgo/{numeroIdentificacion}")
    public String consultarTipoRiesgo(@PathVariable String numeroIdentificacion,@RequestHeader("token") String tokenHeader){
        boolean validaToken = usuarioServicio.validateToken(tokenHeader);
        if (validaToken){
            return clienteService.definirTipoRiesgo(numeroIdentificacion);
        }else {
            throw new LoginExcepcion(Constantes.CredencialesError.TOKEN_NO_VALIDO);
        }

    }
}
