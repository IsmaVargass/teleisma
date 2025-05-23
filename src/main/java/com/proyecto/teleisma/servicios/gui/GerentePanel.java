package com.proyecto.teleisma.servicios.gui;

import com.proyecto.teleisma.entidades.Pizza;
import com.proyecto.teleisma.servicios.api.IGerenteService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class GerentePanel extends JPanel {
    private final IGerenteService gerenteService;
    private final JLabel beneficioLabel;
    private final JTable tablaPizzas;
    private final DefaultTableModel tableModel;
    private final JTextField fechaField;

    public GerentePanel(IGerenteService gerenteService) {
        this.gerenteService = gerenteService;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Panel de Gerente", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        // Panel contenedor vertical
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(Color.WHITE);

        // Panel beneficio
        JPanel panelBeneficio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBeneficio.setBackground(Color.WHITE);
        beneficioLabel = new JLabel("Beneficio total: ");
        beneficioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton btnCalcular = new JButton("Calcular beneficio");
        btnCalcular.addActionListener(e -> mostrarBeneficio());
        panelBeneficio.add(beneficioLabel);
        panelBeneficio.add(btnCalcular);

        // Panel fecha
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFecha.setBackground(Color.WHITE);
        panelFecha.add(new JLabel("Fecha (YYYY-MM-DD): "));
        fechaField = new JTextField(10);
        panelFecha.add(fechaField);
        JButton btnBuscar = new JButton("Buscar ventas");
        btnBuscar.addActionListener(e -> buscarVentasPorFecha());
        panelFecha.add(btnBuscar);

        // Agregar a panelCentro
        panelCentro.add(panelBeneficio);
        panelCentro.add(panelFecha);

        add(panelCentro, BorderLayout.CENTER);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Ingredientes", "Precio"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaPizzas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaPizzas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Pizzas vendidas"));
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void mostrarBeneficio() {
        try {
            double beneficio = gerenteService.calcularBeneficioDesdeVentas();
            beneficioLabel.setText("Beneficio total: " + String.format("%.2f €", beneficio));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al calcular beneficio: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarVentasPorFecha() {
        try {
            String fechaTexto = fechaField.getText().trim();
            LocalDate fecha;
            try {
                fecha = LocalDate.parse(fechaTexto, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD.");
                return;
            }

            List<Pizza> pizzas = gerenteService.getPizzasVendidasPorFecha(fecha.toString());
            tableModel.setRowCount(0); // Limpiar tabla

            for (Pizza p : pizzas) {
                tableModel.addRow(new Object[]{
                        p.getId(), p.getNombre(), p.getIngredientes(), p.getPrecio()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al buscar ventas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
