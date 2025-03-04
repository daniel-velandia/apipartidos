package com.fesc.apipartidos.Data.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "usuario")
@Table(indexes = {
    @Index(columnList = "idUsuario", name = "index_idUsuario", unique = true),
    @Index(columnList = "email", name = "index_email", unique = true),
    @Index(columnList = "username", name = "index_username", unique = true)
})
public class UsuarioEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String idUsuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 12)
    private String username;

    @Column(nullable = false)
    private String passwordEncriptada;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioEntity")
    private List<PartidoEntity> partidoEntityList = new ArrayList<>();

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getPasswordEncriptada() {
        return this.passwordEncriptada;
    }

    public void setPasswordEncriptada(String passwordEncriptada) {
        this.passwordEncriptada = passwordEncriptada;
    }

    public List<PartidoEntity> getPartidoEntityList() {
        return this.partidoEntityList;
    }

    public void setPartidoEntityList(List<PartidoEntity> partidoEntityList) {
        this.partidoEntityList = partidoEntityList;
    }

}
