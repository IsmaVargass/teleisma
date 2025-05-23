package com.proyecto.teleisma.servicios.impl;

import com.proyecto.teleisma.db.DBUtil;
import com.proyecto.teleisma.entidades.Oferta;
import com.proyecto.teleisma.entidades.Pizza;
import com.proyecto.teleisma.servicios.api.IOfertaService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfertaServiceImpl implements IOfertaService {

    @Override
    public void addOferta(Oferta oferta) throws Exception {
        String sql = "INSERT INTO ofertas (descripcion, descuento, pizza_id) VALUES (?, ?, ?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, oferta.getDescripcion());
            ps.setString(2, oferta.getDescuento());
            ps.setInt(3, oferta.getPizzaId());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Oferta> getAllOfertas() throws Exception {
        List<Oferta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ofertas";
        try (Connection c = DBUtil.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Oferta(
                        rs.getInt("id"),
                        rs.getString("descripcion"),
                        rs.getString("descuento"),
                        rs.getInt("pizza_id")
                ));
            }
        }
        return lista;
    }

    @Override
    public Oferta getOfertaById(int id) throws Exception {
        String sql = "SELECT * FROM ofertas WHERE id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Oferta(
                            rs.getInt("id"),
                            rs.getString("descripcion"),
                            rs.getString("descuento"),
                            rs.getInt("pizza_id")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public void updateOferta(Oferta oferta) throws Exception {
        String sql = "UPDATE ofertas SET descripcion=?, descuento=?, pizza_id=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, oferta.getDescripcion());
            ps.setString(2, oferta.getDescuento());
            ps.setInt(3, oferta.getPizzaId());
            ps.setInt(4, oferta.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteOferta(int id) throws Exception {
        String sql = "DELETE FROM ofertas WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void insertOferta(Oferta o) {

    }

    @Override
    public List<Pizza> getAllPizzas() throws Exception {
        List<Pizza> pizzas = new ArrayList<>();
        String sql = "SELECT id, nombre FROM pizzas";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pizzas.add(new Pizza(rs.getInt("id"), rs.getString("nombre")));
                }
            }
            return pizzas;
        }
    }
}
