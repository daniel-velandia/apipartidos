package com.fesc.apipartidos.Services;

import com.fesc.apipartidos.Shared.UsuarioDto;

public interface IUsuarioService {
    
    UsuarioDto crearUsuario(UsuarioDto usuarioCrearDto);
    
    UsuarioDto leerUsuario(String username);
}
