package com.fesc.apipartidos.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fesc.apipartidos.Data.Entidades.PartidoEntity;
import com.fesc.apipartidos.Data.Entidades.UsuarioEntity;
import com.fesc.apipartidos.Data.Repositorios.IPartidoRepository;
import com.fesc.apipartidos.Data.Repositorios.IUsuarioRepository;
import com.fesc.apipartidos.Shared.PartidoDto;
import com.fesc.apipartidos.Shared.UsuarioDto;

@Service
public class UsuarioService implements IUsuarioService{
    
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IUsuarioRepository iUsuarioRepository;

    @Autowired
    IPartidoRepository iPartidoRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UsuarioDto crearUsuario(UsuarioDto usuarioCrearDto) {
        
        if (iUsuarioRepository.findByEmail(usuarioCrearDto.getEmail()) != null) {
            throw new RuntimeException("el email ya ha sido registrado previamente");
        }

        if (iUsuarioRepository.findByUsername(usuarioCrearDto.getUsername()) != null) {
            throw new RuntimeException("el username ya ha sido registrado previamente");
        }

        UsuarioEntity usuarioEntityDto = modelMapper.map(usuarioCrearDto, UsuarioEntity.class);
        
        usuarioEntityDto.setIdUsuario(UUID.randomUUID().toString());
        usuarioEntityDto.setPasswordEncriptada(bCryptPasswordEncoder.encode(usuarioCrearDto.getPassword()));

        UsuarioEntity usuarioEntity = iUsuarioRepository.save(usuarioEntityDto);

        UsuarioDto usuarioDto = modelMapper.map(usuarioEntity, UsuarioDto.class);
        
        return usuarioDto;
    }

    @Override
    public UsuarioDto leerUsuario(String username) {
        
        UsuarioEntity usuarioEntity = iUsuarioRepository.findByUsername(username);

        if (usuarioEntity == null) {
            throw new UsernameNotFoundException(username);
        }

        UsuarioDto usuarioDto = modelMapper.map(usuarioEntity, UsuarioDto.class);

        return usuarioDto;
    }

    @Override
    public List<PartidoDto> leerMisPartidos(String username) {
        
        UsuarioEntity usuarioEntity = iUsuarioRepository.findByUsername(username);
        
        List<PartidoEntity> partidoEntityList = iPartidoRepository.getByUsuarioEntityIdOrderByCreadoDesc(usuarioEntity.getId());

        List<PartidoDto> partidoDtoList = new ArrayList<PartidoDto>();

        for (PartidoEntity partidoEntity : partidoEntityList) {
            
            PartidoDto partidoDto = modelMapper.map(partidoEntity, PartidoDto.class);

            partidoDtoList.add(partidoDto);
        }

        return partidoDtoList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UsuarioEntity usuarioEntity = iUsuarioRepository.findByUsername(username);
     
        if(usuarioEntity ==  null) {
            throw new UsernameNotFoundException(username);
        }

        User usuario = new User(usuarioEntity.getUsername(), usuarioEntity.getPasswordEncriptada(), new ArrayList<>());

        return usuario;
    }
}
