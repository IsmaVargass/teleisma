package com.proyecto.teleisma.servicios.impl;

import com.proyecto.teleisma.db.DBUtil;
import com.proyecto.teleisma.entidades.ContactoCliente;
import com.proyecto.teleisma.servicios.api.IAtencionService;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class AtencionServiceImpl implements IAtencionService {

    /** Correo que recibirá todos los mensajes de clientes */
    private final String destino = "ismaelvargasduque14@alumnos.ilerna.com";

    /** Credenciales y servidor SMTP (pon aquí los tuyos) */
    private final String smtpHost = "smtp.gmail.com";         // e.g. smtp.gmail.com
    private final String smtpPort = "587";                    // e.g. 587
    private final String smtpUser = "ismaelvargasduque14@gmail.com";     // tu cuenta SMTP
    private final String smtpPass = "muevfpmkmraiwfxg";          // tu contraseña de aplicación

    @Override
    public void enviarMensaje(ContactoCliente msg) throws Exception {
        // 1) Inserción en la tabla contacto_clientes (graba el mensaje para histórico)
        String sql = "INSERT INTO contacto_clientes(usuario_id, asunto, mensaje, telefono, fecha) VALUES (?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, msg.getUsuarioId());
            ps.setString(2, msg.getAsunto());
            ps.setString(3, msg.getMensaje());
            ps.setString(4, msg.getTelefono());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        }

        // 2) Configuración de JavaMail para envío por SMTP con STARTTLS
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUser, smtpPass);
            }
        });

        // 3) Construcción y envío del email
        Message correo = new MimeMessage(session);
        correo.setFrom(new InternetAddress(smtpUser)); // O bien "no-reply@tudominio.com" si tu SMTP lo permite
        correo.setRecipient(Message.RecipientType.TO, new InternetAddress(destino));
        correo.setSubject("Nuevo mensaje de usuario #" + msg.getUsuarioId() + ": " + msg.getAsunto());
        correo.setSentDate(new java.util.Date());

        StringBuilder cuerpo = new StringBuilder()
                .append("Usuario ID: ").append(msg.getUsuarioId()).append("\n")
                .append("Teléfono:    ").append(msg.getTelefono()).append("\n\n")
                .append(msg.getMensaje());

        correo.setText(cuerpo.toString());
        Transport.send(correo);
    }

    @Override
    public java.util.List<ContactoCliente> getHistorial(int usuarioId) throws Exception {
        List<ContactoCliente> lista = new ArrayList<>();
        String sql = "SELECT id, usuario_id, asunto, mensaje, telefono, fecha " +
                "FROM contacto_clientes WHERE usuario_id = ? ORDER BY fecha DESC";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ContactoCliente m = new ContactoCliente();
                    m.setId(rs.getInt("id"));
                    m.setUsuarioId(rs.getInt("usuario_id"));
                    m.setAsunto(rs.getString("asunto"));
                    m.setMensaje(rs.getString("mensaje"));
                    m.setTelefono(rs.getString("telefono"));
                    m.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    lista.add(m);
                }
            }
        }
        return lista;
    }

    @Override
    public void deleteMensaje(int id) throws Exception {
        String sql = "DELETE FROM contacto_clientes WHERE id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
