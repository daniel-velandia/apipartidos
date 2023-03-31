package com.fesc.apipartidos.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fesc.apipartidos.Models.Peticiones.UsuarioCrearRequestModel;
import com.fesc.apipartidos.Models.Respuestas.PartidoDataRestModel;
import com.fesc.apipartidos.Models.Respuestas.UsuarioDataRestModel;
import com.fesc.apipartidos.Services.IUsuarioService;
import com.fesc.apipartidos.Shared.PartidoDto;
import com.fesc.apipartidos.Shared.UsuarioDto;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IUsuarioService iUsuarioService;

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UsuarioDataRestModel leerUsuario() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getPrincipal().toString();

        UsuarioDto usuarioDto = iUsuarioService.leerUsuario(username);

        UsuarioDataRestModel usuarioDataRestModel = modelMapper.map(usuarioDto, UsuarioDataRestModel.class);

        return usuarioDataRestModel;
    }

    @GetMapping(path = "/mispartidos")
    public List<PartidoDataRestModel> leerMisPartidos() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getPrincipal().toString();

        List<PartidoDto> partidoDtoList = iUsuarioService.leerMisPartidos(username);

        List<PartidoDataRestModel> partidoDataRestModelList = new ArrayList<>();

        for (PartidoDto partidoDto : partidoDtoList) {
            
            PartidoDataRestModel partidoDataRestModel = modelMapper.map(partidoDto, PartidoDataRestModel.class);

            if (partidoDataRestModel.getFecha().compareTo(new Date(System.currentTimeMillis())) < 0) {
                partidoDataRestModel.setJugado(true);
            }

            partidoDataRestModelList.add(partidoDataRestModel);
        }

        return partidoDataRestModelList;
    }

    @PostMapping
    public UsuarioDataRestModel crearUsuario(@RequestBody UsuarioCrearRequestModel usuarioCrearRequestModel) {
        
        UsuarioDto usuarioCrearDto = modelMapper.map(usuarioCrearRequestModel, UsuarioDto.class);
        UsuarioDto usuarioDto = iUsuarioService.crearUsuario(usuarioCrearDto);

        UsuarioDataRestModel usuarioDataRestModel = modelMapper.map(usuarioDto, UsuarioDataRestModel.class);
        
        return usuarioDataRestModel;
    }
}
