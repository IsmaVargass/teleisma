package com.proyecto.teleisma;

import javax.swing.SwingUtilities;
import com.proyecto.teleisma.servicios.gui.LoginFrame;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}