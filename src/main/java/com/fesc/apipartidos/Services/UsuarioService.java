package com.fesc.apipartidos.Services;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fesc.apipartidos.Data.Entidades.UsuarioEntity;
import com.fesc.apipartidos.Data.Repositorios.IUsuarioRepository;
import com.fesc.apipartidos.Shared.UsuarioDto;

@Service
public class UsuarioService implements IUsuarioService{
    
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IUsuarioRepository iUsuarioRepository;

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
}
