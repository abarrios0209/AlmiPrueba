package com.prueba.almi.excepciones;

public class LoginExcepcion extends RuntimeException{
    public LoginExcepcion(String message) {
        super(message);
    }
}
