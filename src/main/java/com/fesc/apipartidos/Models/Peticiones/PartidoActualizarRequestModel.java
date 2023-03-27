package com.fesc.apipartidos.Models.Peticiones;

public class PartidoActualizarRequestModel {
    
    private String golesLocal;
    private String golesVisitante;

    public String getGolesLocal() {
        return this.golesLocal;
    }

    public void setGolesLocal(String golesLocal) {
        this.golesLocal = golesLocal;
    }

    public String getGolesVisitante() {
        return this.golesVisitante;
    }

    public void setGolesVisitante(String golesVisitante) {
        this.golesVisitante = golesVisitante;
    }
}
