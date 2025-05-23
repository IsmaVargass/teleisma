package com.proyecto.teleisma.entidades;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String password;
    private int roleId;
    private String direccion;
    private LocalDate fechaNacimiento;
    private String dni;

    // Constructor vacío
    public Usuario() {}

    // Constructor completo
    public Usuario(int id, String nombre, String email, String password, int roleId, String direccion, LocalDate fechaNacimiento, String dni) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }



    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
