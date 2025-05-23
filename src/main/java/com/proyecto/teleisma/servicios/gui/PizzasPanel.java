package com.proyecto.teleisma.servicios.gui;

import com.proyecto.teleisma.entidades.Pizza;
import com.proyecto.teleisma.servicios.api.IPizzaService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class PizzasPanel extends JPanel {
    private final IPizzaService pizzaService;
    private final JPanel grid;

    public PizzasPanel(IPizzaService pizzaService) {
        this.pizzaService = pizzaService;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Gestión de Pizzas");
        titulo.setFont(new Font("Verdana", Font.BOLD, 20));
        titulo.setBorder(new EmptyBorder(15,20,10,0));
        add(titulo, BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(0,3,15,15));
        grid.setBackground(Color.WHITE);
        grid.setBorder(new EmptyBorder(10,20,10,20));

        JScrollPane scroll = new JScrollPane(grid);
        scroll.getVerticalScrollBar().setUnitIncrement(16);   // velocidad vertical
        scroll.getHorizontalScrollBar().setUnitIncrement(16); // velocidad horizontal si fuera necesario
        add(scroll, BorderLayout.CENTER);

        JButton btnAdd = new JButton("Añadir nueva pizza");
        btnAdd.setBackground(new Color(0,120,0));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Verdana", Font.BOLD,14));
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new Dimension(200,40));
        btnAdd.addActionListener(e -> abrirFormulario(null));

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(new EmptyBorder(10,0,15,0));
        bottom.add(btnAdd);
        add(bottom, BorderLayout.SOUTH);

        cargarPizzas();
    }

    public void cargarPizzas() {
        grid.removeAll();
        try {
            List<Pizza> lista = pizzaService.getAllPizzas();
            for (Pizza p : lista) {
                grid.add(createPizzaCard(p));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error cargando pizzas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        grid.revalidate();
        grid.repaint();
    }

    private JPanel createPizzaCard(Pizza pizza) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(245,245,245));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(10,10,10,10)
        ));

        // 1) Imagen fija para todas las pizzas
        JLabel imgLabel = new JLabel();
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            String rutaImagen = "/images/pizzas.jpg";
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(
                    getClass().getResource(rutaImagen)));
            Image scaled = icon.getImage()
                    .getScaledInstance(190, 160, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            imgLabel.setText("Imagen no encontrada");
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        // Ahora sí añadimos la imagen al card
        card.add(imgLabel);
        card.add(Box.createVerticalStrut(10));

        // 2) Nombre y precio
        JLabel name  = new JLabel(pizza.getNombre());
        name.setFont(new Font("Verdana", Font.BOLD,14));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel price = new JLabel(String.format("%.2f €", pizza.getPrecio()));
        price.setFont(new Font("Verdana", Font.PLAIN,12));
        price.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 3) Botones
        JButton edit = new JButton("Editar");
        edit.setAlignmentX(Component.CENTER_ALIGNMENT);
        edit.addActionListener(e -> abrirFormulario(pizza));

        JButton del  = new JButton("Eliminar");
        del.setForeground(Color.RED);
        del.setAlignmentX(Component.CENTER_ALIGNMENT);
        del.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this,
                    "¿Eliminar pizza '"+pizza.getNombre()+"'?",
                    "Confirmar", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                try {
                    pizzaService.deletePizza(pizza.getId());
                    cargarPizzas();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar: "+ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Montaje final
        card.add(name);
        card.add(Box.createVerticalStrut(5));
        card.add(price);
        card.add(Box.createVerticalStrut(10));
        card.add(edit);
        card.add(Box.createVerticalStrut(5));
        card.add(del);

        return card;
    }

    private void abrirFormulario(Pizza existente) {
        JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setTitle(existente==null?"Añadir Pizza":"Editar Pizza");
        dialog.setSize(400,250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3,2,10,10));
        form.setBorder(new EmptyBorder(15,15,15,15));

        form.add(new JLabel("Nombre:"));
        JTextField nameF = new JTextField();
        if (existente!=null) nameF.setText(existente.getNombre());
        form.add(nameF);

        form.add(new JLabel("Ingredientes:"));
        JTextField descF = new JTextField();
        if (existente!=null) descF.setText(existente.getIngredientes());
        form.add(descF);

        form.add(new JLabel("Precio (€):"));
        JTextField priceF = new JTextField();
        if (existente!=null) priceF.setText(
                String.valueOf(existente.getPrecio()));
        form.add(priceF);

        dialog.add(form, BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton save = new JButton("Guardar");
        save.addActionListener(e -> {
            String nom = nameF.getText().trim();
            String des = descF.getText().trim();
            String pr  = priceF.getText().trim();
            if (nom.isEmpty()||des.isEmpty()||pr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Todos los campos son obligatorios.");
                return;
            }
            double precio;
            try {
                // Acepta coma o punto decimal
                precio = Double.parseDouble(pr.replace(',', '.'));
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(dialog,
                        "Precio inválido. Usa coma o punto decimal.");
                return;
            }
            try {
                if (existente==null) {
                    Pizza p = new Pizza();
                    p.setNombre(nom);
                    p.setIngredientes(des);
                    p.setPrecio(precio);
                    pizzaService.addPizza(p);
                } else {
                    existente.setNombre(nom);
                    existente.setIngredientes(des);
                    existente.setPrecio(precio);
                    pizzaService.updatePizza(existente);
                }
                dialog.dispose();
                cargarPizzas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Error al guardar: "+ex.getMessage(),
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
