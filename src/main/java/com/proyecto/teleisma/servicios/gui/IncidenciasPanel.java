package com.proyecto.teleisma.servicios.gui;

import com.proyecto.teleisma.entidades.Incidencia;
import com.proyecto.teleisma.servicios.api.IIncidenciaService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class IncidenciasPanel extends JPanel {
    private final IIncidenciaService incService;
    private final JPanel grid;

    public IncidenciasPanel(IIncidenciaService incService) {
        this.incService = incService;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Gestión de Incidencias", SwingConstants.CENTER);
        titulo.setFont(new Font("Verdana", Font.BOLD, 20));
        titulo.setBorder(new EmptyBorder(10, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(0, 2, 20, 20));
        grid.setBackground(new Color(245, 245, 245));
        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        JButton btnAdd = new JButton("Nueva incidencia");
        btnAdd.setFont(new Font("Verdana", Font.PLAIN, 14));
        btnAdd.setBackground(new Color(0, 120, 0));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> abrirFormulario());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.WHITE);
        bottom.add(btnAdd);
        add(bottom, BorderLayout.SOUTH);

        cargarIncidencias();
    }

    private void cargarIncidencias() {
        grid.removeAll();
        try {
            List<Incidencia> lista = incService.getAllIncidencias();
            for (Incidencia inc : lista) {
                grid.add(createCard(inc));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error cargando incidencias: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        revalidate();
        repaint();
    }

    private JPanel createCard(Incidencia inc) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);

        JLabel lblId = new JLabel("ID: " + inc.getId());
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel lblUser = new JLabel("Cliente ID: " + inc.getUsuarioId());
        JLabel lblDir = new JLabel("Dirección: " + inc.getDireccion());
        JLabel lblTel = new JLabel("Teléfono: " + inc.getTelefono());
        JLabel lblFecha = new JLabel("Fecha: " + inc.getFechaRegistro().toString());
        JLabel lblDesc = new JLabel("Descripción:");

        JTextArea area = new JTextArea(inc.getDescripcion());
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setFont(new Font("Verdana", Font.PLAIN, 12));
        area.setBackground(new Color(245, 245, 245));
        area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        card.add(lblId);
        card.add(Box.createVerticalStrut(8));
        card.add(lblUser);
        card.add(Box.createVerticalStrut(8));
        card.add(lblDir);
        card.add(Box.createVerticalStrut(8));
        card.add(lblTel);
        card.add(Box.createVerticalStrut(8));
        card.add(lblFecha);
        card.add(Box.createVerticalStrut(8));
        card.add(lblDesc);
        card.add(Box.createVerticalStrut(5));
        card.add(new JScrollPane(area));
        card.add(Box.createVerticalStrut(15)); // espacio extra antes del botón

        JButton del = new JButton("Eliminar");
        del.setFont(new Font("Verdana", Font.BOLD, 12));
        del.setForeground(Color.WHITE);
        del.setBackground(new Color(200, 0, 0));
        del.setFocusPainted(false);
        del.setAlignmentX(Component.CENTER_ALIGNMENT);
        del.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this,
                    "¿Eliminar incidencia ID " + inc.getId() + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    incService.deleteIncidencia(inc.getId());
                    cargarIncidencias();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        card.add(del);

        return card;
    }

    private void abrirFormulario() {
        JDialog dlg = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Nueva incidencia", true);
        dlg.setSize(400, 400);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        JTextField userIdF = new JTextField();
        JTextField dirF = new JTextField();
        JTextField telF = new JTextField();
        JTextArea descF = new JTextArea(4, 20);

        form.add(new JLabel("Cliente ID:")); form.add(userIdF);
        form.add(new JLabel("Dirección:"));  form.add(dirF);
        form.add(new JLabel("Teléfono:"));   form.add(telF);
        form.add(new JLabel("Descripción:"));
        form.add(new JScrollPane(descF));

        dlg.add(form, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton save = new JButton("Guardar");
        save.addActionListener(e -> {
            try {
                Incidencia inc = new Incidencia();
                inc.setUsuarioId(Integer.parseInt(userIdF.getText().trim()));
                inc.setDireccion(dirF.getText().trim());
                inc.setTelefono(telF.getText().trim());
                inc.setDescripcion(descF.getText().trim());
                incService.addIncidencia(inc);
                dlg.dispose();
                cargarIncidencias();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg,
                        "Error guardando: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton cancel = new JButton("Cancelar");
        cancel.addActionListener(e -> dlg.dispose());
        btns.add(save);
        btns.add(cancel);

        dlg.add(btns, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }
}
