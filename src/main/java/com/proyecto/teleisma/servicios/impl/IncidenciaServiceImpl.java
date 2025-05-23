package com.proyecto.teleisma.servicios.impl;

import com.proyecto.teleisma.db.DBUtil;
import com.proyecto.teleisma.entidades.Incidencia;
import com.proyecto.teleisma.servicios.api.IIncidenciaService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaServiceImpl implements IIncidenciaService {

    @Override
    public List<Incidencia> getAllIncidencias() throws Exception {
        List<Incidencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM incidencias ORDER BY fecha_registro DESC";
        try (Connection c = DBUtil.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Incidencia(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("descripcion"),
                        rs.getTimestamp("fecha_registro").toLocalDateTime()
                ));
            }
        }
        return lista;
    }

    @Override
    public void addIncidencia(Incidencia i) throws Exception {
        String sql = "INSERT INTO incidencias (usuario_id, direccion, telefono, descripcion) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, i.getUsuarioId());
            ps.setString(2, i.getDireccion());
            ps.setString(3, i.getTelefono());
            ps.setString(4, i.getDescripcion());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteIncidencia(int id) throws Exception {
        String sql = "DELETE FROM incidencias WHERE id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
