package com.proyecto.teleisma.servicios.gui;

import com.formdev.flatlaf.FlatLightLaf;
import com.proyecto.teleisma.entidades.Usuario;
import com.proyecto.teleisma.servicios.api.*;
import com.proyecto.teleisma.servicios.impl.AtencionServiceImpl;
import com.proyecto.teleisma.servicios.impl.IncidenciaServiceImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MainFrame extends JFrame {
    private final Usuario usuario;
    private final IOfertaService ofertaService;
    private final IPizzaService pizzaService;
    private final IUsuarioService usuarioService;
    private final IGerenteService gerenteService;  //
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel content = new JPanel(cardLayout);

    // Constructor modificado
    public MainFrame(Usuario u,
                     IOfertaService ofertaService,
                     IPizzaService pizzaService,
                     IUsuarioService usuarioService,
                     IGerenteService gerenteService) {
        this.usuario        = u;
        this.ofertaService  = ofertaService;
        this.pizzaService   = pizzaService;
        this.usuarioService = usuarioService;
        this.gerenteService = gerenteService;

        FlatLightLaf.setup();
        setTitle("TeleIsma - Gestión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 720);
        setMinimumSize(new Dimension(900, 720));
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(135, 31, 31), 3));

        try {
            URL iconUrl = getClass().getResource("/images/icono.png");
            if (iconUrl != null) {
                setIconImage(ImageIO.read(iconUrl));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error cargando icono: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        initUI();
    }

    private void initUI() {
        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(135, 31, 31));
        header.setPreferredSize(new Dimension(getWidth(), 60));

        URL logoUrl = getClass().getResource("/images/teleisma.png");
        if (logoUrl != null) {
            try {
                Image img = ImageIO.read(logoUrl).getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                JLabel logo = new JLabel(new ImageIcon(img));
                logo.setBorder(new EmptyBorder(5, 15, 5, 15));
                header.add(logo, BorderLayout.WEST);
            } catch (IOException ignored) {}
        }

        JLabel title = new JLabel("", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Verdana", Font.BOLD, 20));
        header.add(title, BorderLayout.CENTER);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(
                "EEEE d 'de' MMMM, HH:mm:ss", new Locale("es", "ES"));
        new Timer(1000, e -> {
            String txt = LocalDateTime.now().format(fmt);
            title.setText(Character.toUpperCase(txt.charAt(0)) + txt.substring(1));
        }).start();

        // SIDEBAR
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(new Color(51, 51, 51));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));

        JPanel menu = new JPanel();
        menu.setBackground(new Color(51, 51, 51));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(20, 10, 10, 10));

        JLabel lblMenu = new JLabel("Panel de Gestión");
        lblMenu.setForeground(Color.WHITE);
        lblMenu.setFont(new Font("Verdana", Font.BOLD, 15));
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        menu.add(lblMenu);
        menu.add(Box.createVerticalStrut(20));

        if (hasPermiso("PIZZAS")) {
            JButton btnPizzas = makeSidebarButton("Pizzas");
            btnPizzas.addActionListener(e -> cardLayout.show(content, "PIZZAS"));
            menu.add(btnPizzas);
            menu.add(Box.createVerticalStrut(10));
        }

        if (hasPermiso("OFERTAS")) {
            JButton btnOfertas = makeSidebarButton("Ofertas");
            btnOfertas.addActionListener(e -> cardLayout.show(content, "OFERTAS"));
            menu.add(btnOfertas);
            menu.add(Box.createVerticalStrut(10));
        }

        if (hasPermiso("USUARIOS")) {
            JButton btnUsuarios = makeSidebarButton("Usuarios");
            btnUsuarios.addActionListener(e -> cardLayout.show(content, "USUARIOS"));
            menu.add(btnUsuarios);
            menu.add(Box.createVerticalStrut(10));
        }

        if (usuario.getRoleId() == 3) {
            JButton btnGerente = makeSidebarButton("Venta Bruta");
            btnGerente.addActionListener(e -> cardLayout.show(content, "GERENTE"));
            menu.add(btnGerente);
            menu.add(Box.createVerticalStrut(10));
        }

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(51, 51, 51));
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(new EmptyBorder(10, 10, 20, 10));

        JButton btnCerrar = makeSidebarButton("Cerrar sesión");
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        });
        bottom.add(btnCerrar);
        bottom.add(Box.createVerticalStrut(20));

        JLabel lblUsu = new JLabel("Usuario:");
        lblUsu.setForeground(Color.LIGHT_GRAY);
        lblUsu.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblUsu.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottom.add(lblUsu);
        bottom.add(Box.createVerticalStrut(5));

        JLabel lblName = new JLabel(usuario.getNombre());
        lblName.setForeground(Color.WHITE);
        lblName.setFont(new Font("Verdana", Font.BOLD, 15));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottom.add(lblName);

        sidebar.add(menu, BorderLayout.NORTH);
        sidebar.add(bottom, BorderLayout.SOUTH);

        // CONTENT
        if (hasPermiso("USUARIOS"))
            content.add(new UsuariosPanel(usuarioService), "USUARIOS");

        if (hasPermiso("PIZZAS"))
            content.add(new PizzasPanel(pizzaService), "PIZZAS");

        if (hasPermiso("OFERTAS"))
            content.add(new OfertasPanel(ofertaService), "OFERTAS");

        if (hasPermiso("GERENTE"))
            content.add(new GerentePanel(gerenteService), "GERENTE");

        if (hasPermiso("GERENTE")) {
            IIncidenciaService incService = new IncidenciaServiceImpl();
            content.add(new IncidenciasPanel(incService), "INCIDENCIAS");
            JButton btnInc = makeSidebarButton("Incidencias");
            btnInc.addActionListener(e -> cardLayout.show(content, "INCIDENCIAS"));
            menu.add(btnInc);
            menu.add(Box.createVerticalStrut(10));
        }

        IAtencionService atSvc = new AtencionServiceImpl();
        if (hasPermiso("PIZZAS")||hasPermiso("OFERTAS")||hasPermiso("USUARIOS")||hasPermiso("GERENTE")) {
            content.add(new AtencionClientePanel(atSvc, usuario.getId()), "CSR");
        }
        JButton btnAtencion = makeSidebarButton("CSR");
        btnAtencion.addActionListener(e -> cardLayout.show(content,"CSR"));
        menu.add(btnAtencion);
        menu.add(Box.createVerticalStrut(10));



        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        if (hasPermiso("PIZZAS")) {
            cardLayout.show(content, "PIZZAS");
        } else if (hasPermiso("OFERTAS")) {
            cardLayout.show(content, "OFERTAS");
        } else if (hasPermiso("USUARIOS")) {
            cardLayout.show(content, "USUARIOS");
        }
    }

    private boolean hasPermiso(String modulo) {
        int rol = usuario.getRoleId();
        switch (modulo) {
            case "PIZZAS":
                return rol == 1 || rol == 2 || rol == 3;
            case "OFERTAS":
                return rol == 2 || rol == 3;
            case "USUARIOS":
                return rol == 2;
            case "GERENTE":
                return rol == 3;
            default:
                return false;
        }
    }

    private JButton makeSidebarButton(String text) {
        JButton b = new JButton(text);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setFont(new Font("Verdana", Font.PLAIN, 16));
        b.setBackground(new Color(200, 0, 0));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private static class UsuariosPanel extends JPanel {
        public UsuariosPanel(IUsuarioService usuarioService) {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);

            JLabel titulo = new JLabel("Gestión de Usuarios", SwingConstants.CENTER);
            titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
            titulo.setBorder(new EmptyBorder(15, 20, 10, 20));
            add(titulo, BorderLayout.NORTH);

            JPanel usersContainer = new JPanel(new GridLayout(0, 2, 20, 20));
            usersContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
            usersContainer.setBackground(new Color(245, 245, 245));

            try {
                for (Usuario u : usuarioService.getAllUsuarios()) {
                    usersContainer.add(createUserCard(u));
                }
            } catch (Exception ex) {
                JLabel errorLabel = new JLabel("Error cargando usuarios: " + ex.getMessage());
                errorLabel.setForeground(Color.RED);
                usersContainer.add(errorLabel);
            }

            JScrollPane scrollPane = new JScrollPane(usersContainer);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            add(scrollPane, BorderLayout.CENTER);
        }

        private JPanel createUserCard(Usuario u) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    new EmptyBorder(15, 15, 15, 15)
            ));
            card.setPreferredSize(new Dimension(200, 150));
            card.setMaximumSize(new Dimension(300, 200));

            JLabel lblNombre = new JLabel("Nombre: " + u.getNombre());
            lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lblEmail = new JLabel("Correo: " + u.getEmail());
            lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblEmail.setForeground(Color.DARK_GRAY);
            lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lblId = new JLabel("ID: " + u.getId());
            lblId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lblId.setForeground(new Color(120, 120, 120));
            lblId.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lblDireccion = new JLabel("Dirección: " + u.getDireccion());
            JLabel lblNacimiento = new JLabel("Fecha de nacimiento: " +
                    (u.getFechaNacimiento() != null ? u.getFechaNacimiento().toString() : "N/D"));
            JLabel lblDni = new JLabel("DNI: " + u.getDni());

            card.add(lblNombre);
            card.add(Box.createVerticalStrut(8));
            card.add(lblEmail);
            card.add(lblId);
            card.add(Box.createVerticalStrut(8));
            card.add(lblDireccion);
            card.add(lblNacimiento);
            card.add(lblDni);


            return card;
        }
    }
}
