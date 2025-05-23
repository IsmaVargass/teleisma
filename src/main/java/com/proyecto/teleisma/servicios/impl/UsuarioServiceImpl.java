package com.proyecto.teleisma.servicios.impl;

import com.proyecto.teleisma.db.DBUtil;
import com.proyecto.teleisma.entidades.Usuario;
import com.proyecto.teleisma.servicios.api.IUsuarioService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioServiceImpl implements IUsuarioService {

    @Override
    public void register(Usuario u) throws Exception {
        String sql = "INSERT INTO usuarios (nombre, email, password, role_id, direccion, fecha_nacimiento, dni) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setInt(4, u.getRoleId());
            ps.setString(5, u.getDireccion());

            if (u.getFechaNacimiento() != null) {
                ps.setDate(6, Date.valueOf(u.getFechaNacimiento()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            ps.setString(7, u.getDni());
            ps.executeUpdate();
        }
    }

    @Override
    public Usuario login(String nombre, String password) throws Exception {
        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Usuario> getAllUsuarios() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection c = DBUtil.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapResultSetToUsuario(rs));
            }
        }
        return lista;
    }

    @Override
    public void deleteUsuario(int id) throws Exception {
        String sql = "DELETE FROM usuarios WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setRoleId(rs.getInt("role_id"));
        u.setDireccion(rs.getString("direccion"));

        Date fecha = rs.getDate("fecha_nacimiento");
        if (fecha != null) {
            u.setFechaNacimiento(fecha.toLocalDate());
        }

        u.setDni(rs.getString("dni"));
        return u;
    }
}
