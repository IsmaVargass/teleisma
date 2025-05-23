package com.proyecto.teleisma.servicios.impl;

import com.proyecto.teleisma.db.DBUtil;
import com.proyecto.teleisma.entidades.Pizza;
import com.proyecto.teleisma.entidades.Usuario;
import com.proyecto.teleisma.servicios.api.IGerenteService;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerenteServiceImpl extends UsuarioServiceImpl implements IGerenteService {

    @Override
    public double calcularBeneficioTotal() throws Exception {
        String sql = "SELECT p.precio, v.cantidad FROM ventas v " +
                "JOIN pizzas p ON v.pizza_id = p.id";
        try (Connection c = DBUtil.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                double beneficio = rs.getDouble("beneficio");
                return rs.wasNull() ? 0.0 : beneficio;
            }
        } catch (SQLException e) {
            throw new Exception("Error al calcular beneficio total", e);
        }
        return 0.0;
    }

    @Override
    public List<Pizza> getPizzasVendidasPorFecha(String fecha) throws Exception {
        List<Pizza> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT p.id, p.nombre, p.ingredientes, p.precio " +
                "FROM pizzas p " +
                "JOIN ventas v ON p.id = v.pizza_id " +
                "WHERE DATE(v.fecha) = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, fecha);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pizza p = new Pizza(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("ingredientes"),
                            rs.getDouble("precio")
                    );
                    lista.add(p);
                }
            }
        }
        return lista;
    }

    @Override
    public double calcularBeneficioDesdeVentas() {
        double beneficio = 0.0;
        String sql = "SELECT p.precio, v.cantidad FROM ventas v " +
                "JOIN pizzas p ON v.pizza_id = p.id";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/teleismabbdd", "root", "");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");
                beneficio += precio * cantidad;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al calcular beneficio", e);
        }

        return beneficio;
    }

}
