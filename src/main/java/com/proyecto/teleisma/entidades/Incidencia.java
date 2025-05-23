package com.proyecto.teleisma.entidades;


import java.time.LocalDateTime;

public class Incidencia {
    private int id;
    private int usuarioId;
    private String direccion;
    private String telefono;
    private String descripcion;
    private LocalDateTime fechaRegistro;

    public Incidencia() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Incidencia(int id, int usuarioId, String direccion, String telefono, String descripcion, LocalDateTime fechaRegistro) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.direccion = direccion;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
    }
}