package com.prueba.almi.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler(LoginExcepcion.class)
    public ResponseEntity<String> excepcionLogin(LoginExcepcion excepcionLogin){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(excepcionLogin.getMessage());
    }

    @ExceptionHandler(ClienteExcepcion.class)
    public ResponseEntity<String> excepcionCliente(ClienteExcepcion excepcionCliente){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(excepcionCliente.getMessage());
    }

    @ExceptionHandler(CreditoExcepcion.class)
    public ResponseEntity<String> excepcionCredito(CreditoExcepcion excepcionCredito){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(excepcionCredito.getMessage());
    }
}
