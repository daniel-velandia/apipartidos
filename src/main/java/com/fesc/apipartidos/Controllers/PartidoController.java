package com.fesc.apipartidos.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fesc.apipartidos.Models.Peticiones.PartidoActualizarRequestModel;
import com.fesc.apipartidos.Models.Peticiones.PartidoCrearRequestModel;
import com.fesc.apipartidos.Models.Respuestas.MensajeRestModel;
import com.fesc.apipartidos.Models.Respuestas.PartidoDataRestModel;
import com.fesc.apipartidos.Services.IPartidoService;
import com.fesc.apipartidos.Services.IUsuarioService;
import com.fesc.apipartidos.Shared.PartidoDto;
import com.fesc.apipartidos.Shared.UsuarioDto;

@RestController
@RequestMapping("/partido")
public class PartidoController {
    
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IPartidoService iPartidoService;

    @Autowired
    IUsuarioService iUsuarioService;

    @GetMapping
    public List<PartidoDataRestModel> leerPartidos() {

        List<PartidoDto> partidoDtoList = iPartidoService.partidosCreados();

        List<PartidoDataRestModel> partidoDataRestModelList = new ArrayList<>();

        for (PartidoDto partidoDto : partidoDtoList) {
            PartidoDataRestModel partidoDataRestModel = modelMapper.map(partidoDto, PartidoDataRestModel.class);
            partidoDataRestModelList.add(partidoDataRestModel);
        }

        return partidoDataRestModelList;
    }

    @GetMapping(path = "/{id}")
    public PartidoDataRestModel detallePartido(@PathVariable String id) {
        
        PartidoDto partidoDto = iPartidoService.detallePartido(id);

        PartidoDataRestModel partidoDataRestModel = modelMapper.map(partidoDto, PartidoDataRestModel.class);

        return partidoDataRestModel;
    }

    @PostMapping
    public PartidoDataRestModel crearPartido(@RequestBody PartidoCrearRequestModel partidoCrearRequestModel) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getPrincipal().toString();

        PartidoDto partidoCrearDto = modelMapper.map(partidoCrearRequestModel, PartidoDto.class);
        partidoCrearDto.setUsername(username);

        PartidoDto partidoDto = iPartidoService.crearPartido(partidoCrearDto);

        PartidoDataRestModel partidoDataRestModel = modelMapper.map(partidoDto, PartidoDataRestModel.class);

        return partidoDataRestModel;
    }

    @PutMapping(path = "/{id}")
    public PartidoDataRestModel actualizarPartido(@PathVariable String id, @RequestBody PartidoActualizarRequestModel partidoActualizarRequestModel) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getPrincipal().toString();
        
        PartidoDto partidoActualizarDto = modelMapper.map(partidoActualizarRequestModel, PartidoDto.class);
        partidoActualizarDto.setUsername(username);

        PartidoDto partidoDto = iPartidoService.actualizarPartido(id, partidoActualizarDto);

        PartidoDataRestModel partidoDataRestModel = modelMapper.map(partidoDto, PartidoDataRestModel.class);

        return partidoDataRestModel;
    }

    @DeleteMapping(path = "/{id}")
    public MensajeRestModel eliminarPartido(@PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getPrincipal().toString();

        UsuarioDto usuarioDto = iUsuarioService.leerUsuario(username);

        MensajeRestModel mensajeRestModel = new MensajeRestModel();
        mensajeRestModel.setNombre("Eliminar");

        iPartidoService.eliminarPartido(id, usuarioDto.getId());

        mensajeRestModel.setResultado("Partido eliminardo con exito");

        return mensajeRestModel;
    }
}
