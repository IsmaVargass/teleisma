package com.proyecto.teleisma.servicios.impl;

import com.proyecto.teleisma.db.DBUtil;
import com.proyecto.teleisma.entidades.Pizza;
import com.proyecto.teleisma.servicios.api.IPizzaService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaServiceImpl implements IPizzaService {
    @Override
    public List<Pizza> getAllPizzas() throws Exception {
        List<Pizza> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, ingredientes, precio FROM pizzas";
        try (Connection c = DBUtil.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Pizza(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("ingredientes"),
                        rs.getDouble("precio")
                ));
            }
        }
        return lista;
    }

    @Override
    public void addPizza(Pizza pizza) throws Exception {
        String sql = "INSERT INTO pizzas (nombre, ingredientes, precio) VALUES (?, ?, ?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, pizza.getNombre());
            ps.setString(2, pizza.getIngredientes());
            ps.setDouble(3, pizza.getPrecio());
            ps.executeUpdate();
        }
    }

    @Override
    public void updatePizza(Pizza pizza) throws Exception {
        String sql = "UPDATE pizzas SET nombre=?, ingredientes=?, precio=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, pizza.getNombre());
            ps.setString(2, pizza.getIngredientes());
            ps.setDouble(3, pizza.getPrecio());
            ps.setInt(4, pizza.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deletePizza(int id) throws Exception {
        String sql = "DELETE FROM pizzas WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Pizza getPizzaById(int id) throws Exception {
        String sql = "SELECT id, nombre, ingredientes, precio FROM pizzas WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pizza(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("ingredientes"),
                            rs.getDouble("precio")
                    );
                }
            }
        }
        return null;
    }
}
