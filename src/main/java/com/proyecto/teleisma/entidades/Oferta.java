package com.proyecto.teleisma.entidades;

public class Oferta {
    private int id;
    private String descripcion;
    private String descuento;
    private int pizzaId;

    public Oferta(){}

    public Oferta(int id, String descripcion, String descuento, int pizzaId) {
        this.id = id;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.pizzaId = pizzaId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDescuento() { return descuento; }
    public void setDescuento(String descuento) { this.descuento = descuento; }

    public int getPizzaId() { return pizzaId; }
    public void setPizzaId(int pizzaId) { this.pizzaId = pizzaId; }
}
