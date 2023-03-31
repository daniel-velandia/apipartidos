package com.fesc.apipartidos.Errors;

public class UsuarioExisteException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public UsuarioExisteException(String mensaje) {
        super(mensaje);
    }
}
