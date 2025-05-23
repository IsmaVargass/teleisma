package com.proyecto.teleisma.servicios.gui;

import com.proyecto.teleisma.entidades.ContactoCliente;
import com.proyecto.teleisma.servicios.api.IAtencionService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public class AtencionClientePanel extends JPanel {
    private final IAtencionService atencionService;
    private final int usuarioId;

    private final JTextField asuntoField = new JTextField();
    private final JTextField telefonoField = new JTextField();
    private final JTextArea mensajeArea = new JTextArea(6, 30);
    private final JButton btnEnviar = new JButton("Enviar correo");
    private final JButton btnIntranet = new JButton("Visitar FAQ");
    private final JButton btnBorrarHistorial = new JButton("Borrar historial");

    private final DefaultListModel<String> historialModel = new DefaultListModel<>();
    private final String destino = "ismaelvargasduque14@alumnos.ilerna.com";

    public AtencionClientePanel(IAtencionService svc, int usuarioId) {
        this.atencionService = svc;
        this.usuarioId = usuarioId;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        // Formulario de envío
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        form.add(new JLabel("Asunto:"), c);
        c.gridx = 1;
        form.add(asuntoField, c);

        c.gridx = 0;
        c.gridy = 1;
        form.add(new JLabel("Teléfono:"), c);
        c.gridx = 1;
        form.add(telefonoField, c);

        c.gridx = 0;
        c.gridy = 2;
        form.add(new JLabel("Mensaje:"), c);
        c.gridx = 1;
        mensajeArea.setLineWrap(true);
        mensajeArea.setWrapStyleWord(true);
        form.add(new JScrollPane(mensajeArea), c);

        c.gridx = 1;
        c.gridy = 3;
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btns.setBackground(Color.WHITE);
        btns.add(btnEnviar);
        btns.add(btnIntranet);
        form.add(btns, c);

        add(form, BorderLayout.NORTH);

        // Historial de envíos con botón para borrar
        JList<String> historialList = new JList<>(historialModel);
        JScrollPane historialScroll = new JScrollPane(historialList);

        JPanel historialPanel = new JPanel(new BorderLayout(5, 5));
        historialPanel.setBackground(Color.WHITE);
        historialPanel.setBorder(BorderFactory.createTitledBorder("Historial de mensajes enviados"));
        historialPanel.add(historialScroll, BorderLayout.CENTER);

        // Botón borrar historial
        btnBorrarHistorial.setBackground(new Color(220, 53, 69));
        btnBorrarHistorial.setForeground(Color.WHITE);
        btnBorrarHistorial.setFocusPainted(false);
        btnBorrarHistorial.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnBorrarHistorial.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que quieres borrar el historial?",
                    "Confirmar borrado",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                historialModel.clear(); // Solo visual, puedes añadir lógica de backend aquí si la tienes
            }
        });

        historialPanel.add(btnBorrarHistorial, BorderLayout.SOUTH);

        add(historialPanel, BorderLayout.CENTER);

        // Acciones
        btnEnviar.addActionListener(e -> enviar());
        btnIntranet.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.telepizza.es/info/faqs.html"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se pudo abrir FAQ");
            }
        });

        cargarHistorial();
    }

    private void enviar() {
        String asunto = asuntoField.getText().trim();
        String msg = mensajeArea.getText().trim();
        String telefono = telefonoField.getText().trim();
        if (asunto.isEmpty() || msg.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Rellena asunto, mensaje y teléfono.");
            return;
        }
        try {
            ContactoCliente c = new ContactoCliente();
            c.setUsuarioId(usuarioId);
            c.setAsunto(asunto);
            c.setMensaje(msg);
            c.setTelefono(telefono);
            c.setFecha(LocalDateTime.now());
            atencionService.enviarMensaje(c);
            JOptionPane.showMessageDialog(this, "Correo enviado correctamente.");
            asuntoField.setText("");
            mensajeArea.setText("");
            telefonoField.setText("");
            cargarHistorial();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al enviar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarHistorial() {
        historialModel.clear();
        try {
            List<ContactoCliente> lista = atencionService.getHistorial(usuarioId);
            for (ContactoCliente c : lista) {
                historialModel.addElement(
                        String.format("%s  [%s]\n%s",
                                c.getAsunto(),
                                c.getFecha().toLocalDate(),
                                c.getMensaje()
                        )
                );
            }
        } catch (Exception ex) {
            historialModel.addElement("Error cargando historial: " + ex.getMessage());
        }
    }
}
