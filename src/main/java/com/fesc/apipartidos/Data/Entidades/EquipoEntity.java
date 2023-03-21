package com.fesc.apipartidos.Data.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "equipo")
public class EquipoEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 20)
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipoEntityLocal")
    private List<PartidoEntity> partidoEntityLocalList = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipoEntityVisitante")
    private List<PartidoEntity> partidoEntityVisitanteList = new ArrayList<>();

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<PartidoEntity> getPartidoEntityLocalList() {
        return this.partidoEntityLocalList;
    }

    public void setPartidoEntityLocalList(List<PartidoEntity> partidoEntityLocalList) {
        this.partidoEntityLocalList = partidoEntityLocalList;
    }

    public List<PartidoEntity> getPartidoEntityVisitanteList() {
        return this.partidoEntityVisitanteList;
    }

    public void setPartidoEntityVisitanteList(List<PartidoEntity> partidoEntityVisitanteList) {
        this.partidoEntityVisitanteList = partidoEntityVisitanteList;
    }
}
