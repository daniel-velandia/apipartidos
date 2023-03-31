package com.fesc.apipartidos.Models.Respuestas;

import java.util.Date;

public class MensajeErrorModel {
    
    private Date Timestamp;
    private String mensaje;

    public MensajeErrorModel() {
    }

    public MensajeErrorModel(Date timestamp, String mensaje) {
        Timestamp = timestamp;
        this.mensaje = mensaje;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
