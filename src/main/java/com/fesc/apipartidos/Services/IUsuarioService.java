package com.fesc.apipartidos.Services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.fesc.apipartidos.Shared.PartidoDto;
import com.fesc.apipartidos.Shared.UsuarioDto;

public interface IUsuarioService extends UserDetailsService{
    
    UsuarioDto crearUsuario(UsuarioDto usuarioCrearDto);
    
    UsuarioDto leerUsuario(String username);

    List<PartidoDto> leerMisPartidos(String username);
}
