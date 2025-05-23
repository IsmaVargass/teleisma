package com.proyecto.teleisma.servicios.gui;

import com.formdev.flatlaf.FlatLightLaf;
import com.proyecto.teleisma.entidades.Usuario;
import com.proyecto.teleisma.servicios.api.IOfertaService;
import com.proyecto.teleisma.servicios.api.IPizzaService;
import com.proyecto.teleisma.servicios.api.IUsuarioService;
import com.proyecto.teleisma.servicios.impl.GerenteServiceImpl;
import com.proyecto.teleisma.servicios.impl.OfertaServiceImpl;
import com.proyecto.teleisma.servicios.impl.PizzaServiceImpl;
import com.proyecto.teleisma.servicios.impl.UsuarioServiceImpl;
import com.proyecto.teleisma.servicios.api.IGerenteService;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class LoginFrame extends JFrame {
    private final JTextField userField = new JTextField();
    private final JPasswordField passField = new JPasswordField();
    private final IUsuarioService userService = new UsuarioServiceImpl();
    IGerenteService gerenteService = new GerenteServiceImpl();

    public LoginFrame() {
        FlatLightLaf.setup();
        setTitle("TeleIsma");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 720);
        setMinimumSize(new Dimension(900, 720));
        setLocationRelativeTo(null);
        setResizable(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(135, 31, 31), 3));
        getContentPane().setBackground(new Color(245, 245, 245));

        // Icono de la aplicación
        try {
            URL iconUrl = getClass().getResource("/images/icono.png");
            if (iconUrl != null) {
                Image icon = ImageIO.read(iconUrl);
                setIconImage(icon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        initUI();
    }

    private void initUI() {
        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(135, 31, 31));
        header.setPreferredSize(new Dimension(getWidth(), 60));

        // Logo
        URL logoUrl = getClass().getResource("/images/teleisma.png");
        if (logoUrl != null) {
            try {
                Image img = ImageIO.read(logoUrl).getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                JLabel logo = new JLabel(new ImageIcon(img));
                logo.setBorder(new EmptyBorder(5, 15, 5, 15));
                header.add(logo, BorderLayout.WEST);
            } catch (IOException ignored) {}
        }

        // Título
        JLabel title = new JLabel("Ingresa tus credenciales", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Tahoma", Font.BOLD, 23));
        header.add(title, BorderLayout.CENTER);

        // FORMULARIO
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(40, 100, 40, 100));
        form.setBackground(new Color(245, 245, 245));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Usuario
        c.gridx = 0; c.gridy = 0;
        form.add(new JLabel("Usuario:"), c);
        c.gridx = 1;
        userField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        userField.setPreferredSize(new Dimension(320, 40));
        form.add(userField, c);

        // Contraseña
        c.gridx = 0; c.gridy = 1;
        form.add(new JLabel("Contraseña:"), c);
        c.gridx = 1;
        passField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        passField.setPreferredSize(new Dimension(320, 40));
        form.add(passField, c);

        // Botones
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnEntrar.setBackground(new Color(200, 0, 0));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        btnEntrar.addActionListener(e -> authenticate());
        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        form.add(btnEntrar, c);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnSalir.setBackground(new Color(200, 0, 0));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        btnSalir.addActionListener(e -> System.exit(0));
        c.gridy = 3;
        form.add(btnSalir, c);

        // LAYOUT
        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }

    private void authenticate() {
        try {
            Usuario u = userService.login(
                    userField.getText(),
                    String.valueOf(passField.getPassword())
            );
            if (u != null) {
                // Creamos aquí los servicios que necesite el MainFrame
                IOfertaService ofertaService = new OfertaServiceImpl();
                IPizzaService  pizzaService  = new PizzaServiceImpl();
                IUsuarioService usuarioService = userService; // reusamos la misma instancia
                MainFrame mainFrame = new MainFrame(u, ofertaService, pizzaService, usuarioService, gerenteService);
                mainFrame.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Credenciales incorrectas",
                        "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
