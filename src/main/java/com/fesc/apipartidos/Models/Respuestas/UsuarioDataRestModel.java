package com.fesc.apipartidos.Models.Respuestas;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDataRestModel {
    
    private String idUsuario;

    private String nombre;

    private String email;

    private String username;
    
    private List<PartidoDataRestModel> partidoDataRestModelList = new ArrayList<>();

    public String getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<PartidoDataRestModel> getPartidoDataRestModelList() {
        return this.partidoDataRestModelList;
    }

    public void setPartidoDataRestModelList(List<PartidoDataRestModel> partidoDataRestModelList) {
        this.partidoDataRestModelList = partidoDataRestModelList;
    }
    
}
