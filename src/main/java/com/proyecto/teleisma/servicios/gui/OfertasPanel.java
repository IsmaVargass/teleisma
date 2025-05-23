package com.proyecto.teleisma.servicios.gui;

import com.proyecto.teleisma.entidades.Oferta;
import com.proyecto.teleisma.servicios.api.IOfertaService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class OfertasPanel extends JPanel {
    private final IOfertaService ofertaService;
    private final JPanel grid;

    public OfertasPanel(IOfertaService ofertaService) {
        this.ofertaService = ofertaService;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Título
        JLabel titulo = new JLabel("Gestión de Ofertas");
        titulo.setFont(new Font("Verdana", Font.BOLD, 20));
        titulo.setBorder(new EmptyBorder(15, 20, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // Grid de cards
        grid = new JPanel(new GridLayout(0, 3, 15, 15));
        grid.setBackground(Color.WHITE);
        grid.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Scroll con velocidad ajustada
        JScrollPane scroll = new JScrollPane(grid);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getHorizontalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        // Botón añadir
        JButton btnAdd = new JButton("Añadir nueva oferta");
        btnAdd.setBackground(new Color(0, 120, 0));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Verdana", Font.BOLD, 14));
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new Dimension(200, 40));
        btnAdd.addActionListener(e -> abrirFormulario(null));

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(new EmptyBorder(10, 0, 15, 0));
        bottom.add(btnAdd);
        add(bottom, BorderLayout.SOUTH);

        cargarOfertas();
    }

    public void cargarOfertas() {
        grid.removeAll();
        try {
            List<Oferta> lista = ofertaService.getAllOfertas();
            for (Oferta oferta : lista) {
                grid.add(createOfertaCard(oferta));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error cargando ofertas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        grid.revalidate();
        grid.repaint();
    }

    private JPanel createOfertaCard(Oferta oferta) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(10, 10, 10, 10)
        ));

        // Imagen fija para todas las ofertas
        JLabel imgLabel = new JLabel();
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            String rutaImagen = "/images/ofertas.jpg";
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(
                    getClass().getResource(rutaImagen)));
            Image scaled = icon.getImage()
                    .getScaledInstance(190, 160, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            imgLabel.setText("Imagen no encontrada");
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        // Descripción
        JLabel descLabel = new JLabel(oferta.getDescripcion());
        descLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descLabel.setBorder(new EmptyBorder(10, 0, 5, 0));

        // Descuento
        JLabel dtoLabel = new JLabel("Descuento: " + oferta.getDescuento());
        dtoLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        dtoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botones
        JButton edit = new JButton("Editar");
        edit.setFont(new Font("Verdana", Font.PLAIN, 12));
        edit.setAlignmentX(Component.CENTER_ALIGNMENT);
        edit.addActionListener(e -> abrirFormulario(oferta));

        JButton del = new JButton("Eliminar");
        del.setFont(new Font("Verdana", Font.PLAIN, 12));
        del.setForeground(Color.RED);
        del.setAlignmentX(Component.CENTER_ALIGNMENT);
        del.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this,
                    "¿Eliminar oferta '" + oferta.getDescripcion() + "'?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    ofertaService.deleteOferta(oferta.getId());
                    cargarOfertas();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Montaje de la tarjeta
        card.add(imgLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(descLabel);
        card.add(dtoLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(edit);
        card.add(Box.createVerticalStrut(5));
        card.add(del);

        return card;
    }

    private void abrirFormulario(Oferta existente) {
        JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setTitle(existente == null ? "Añadir Oferta" : "Editar Oferta");
        dialog.setSize(400, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(new EmptyBorder(15, 15, 15, 15));

        form.add(new JLabel("Descripción:"));
        JTextField descF = new JTextField();
        if (existente != null) descF.setText(existente.getDescripcion());
        form.add(descF);

        form.add(new JLabel("Descuento:"));
        JTextField dtoF = new JTextField();
        if (existente != null) dtoF.setText(existente.getDescuento());
        form.add(dtoF);

        dialog.add(form, BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton save = new JButton("Guardar");
        save.addActionListener(e -> {
            String d = descF.getText().trim();
            String ds = dtoF.getText().trim();
            if (d.isEmpty() || ds.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Todos los campos son obligatorios.");
                return;
            }
            try {
                if (existente == null) {
                    Oferta o = new Oferta();
                    o.setDescripcion(d);
                    o.setDescuento(ds);
                    // asigna pizzaId
                    o.setPizzaId(1);
                    ofertaService.addOferta(o);
                } else {
                    existente.setDescripcion(d);
                    existente.setDescuento(ds);
                    ofertaService.updateOferta(existente);
                }
                dialog.dispose();
                cargarOfertas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Error al guardar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton cancel = new JButton("Cancelar");
        cancel.addActionListener(e -> dialog.dispose());
        btns.add(save);
        btns.add(cancel);

        dialog.add(btns, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
