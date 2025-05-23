package com.proyecto.teleisma.entidades;

public class Pizza {
    private int id;
    private String nombre;
    private String Ingredientes;
    private double precio;

    public Pizza(int id, String nombre, String Ingredientes, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.Ingredientes = Ingredientes;
        this.precio = precio;
    }

    public Pizza(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Pizza() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getIngredientes() { return Ingredientes; }
    public void setIngredientes(String Ingredientes) { this.Ingredientes = Ingredientes; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return nombre;
    }
}

