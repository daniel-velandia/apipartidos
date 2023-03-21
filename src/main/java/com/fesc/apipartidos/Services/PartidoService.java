package com.fesc.apipartidos.Services;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fesc.apipartidos.Data.Entidades.EquipoEntity;
import com.fesc.apipartidos.Data.Entidades.PartidoEntity;
import com.fesc.apipartidos.Data.Entidades.UsuarioEntity;
import com.fesc.apipartidos.Data.Repositorios.IEquipoRepository;
import com.fesc.apipartidos.Data.Repositorios.IPartidoRepository;
import com.fesc.apipartidos.Data.Repositorios.IUsuarioRepository;
import com.fesc.apipartidos.Shared.PartidoDto;

@Service
public class PartidoService implements IPartidoService{
    
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IEquipoRepository iEquipoRepository;

    @Autowired
    IUsuarioRepository iUsuarioRepository;

    @Autowired
    IPartidoRepository iPartidoRepository;

    @Override
    public PartidoDto crearPartido(PartidoDto partidoCrearDto) {

        UsuarioEntity usuarioEntity = iUsuarioRepository.findByUsername(partidoCrearDto.getUsername());
        
        EquipoEntity equipoEntityLocal = iEquipoRepository.findById(partidoCrearDto.getEquipoLocal());

        EquipoEntity equipoEntityVisitante = iEquipoRepository.findById(partidoCrearDto.getEquipoVisitante());

        PartidoEntity partidoEntity = new PartidoEntity();

        partidoEntity.setIdPartido(UUID.randomUUID().toString());
        partidoEntity.setFecha(partidoCrearDto.getFecha());
        partidoEntity.setGolesLocal("0");
        partidoEntity.setGolesVisitante("0");
        partidoEntity.setUsuarioEntity(usuarioEntity);
        partidoEntity.setEquipoEntityLocal(equipoEntityLocal);
        partidoEntity.setEquipoEntityVisitante(equipoEntityVisitante);

        PartidoEntity partidoCreado = iPartidoRepository.save(partidoEntity);

        PartidoDto partidoDto = modelMapper.map(partidoCreado, PartidoDto.class);

        return partidoDto;
    }
}
