package com.fesc.apipartidos.Services;

import java.util.List;

import com.fesc.apipartidos.Shared.PartidoDto;

public interface IPartidoService {

    PartidoDto crearPartido(PartidoDto partidoCrearDto);

    List<PartidoDto> partidosCreados();

    PartidoDto detallePartido(String id);

    PartidoDto actualizarPartido(String idPartido, PartidoDto partidoActualizarDto);
    
    void eliminarPartido(String idPartido, long idUsuario);
}
