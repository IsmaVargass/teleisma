package com.proyecto.teleisma.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilidad para gestionar la conexión con la base de datos MySQL.
 * Proporciona métodos para obtener y cerrar conexiones.
 */
public class DBUtil {
    // Nombre de la clase del driver JDBC de MySQL
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // URL de conexión a la base de datos, incluyendo parámetros para SSL y zona horaria
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/teleismabbdd?useSSL=false&serverTimezone=UTC";

    // Usuario de la base de datos
    private static final String USER = "root";

    // Contraseña del usuario (vacía en este caso)
    private static final String PASSWORD = "";

    /**
     * Bloque estático que se ejecuta al cargar la clase.
     * Carga el driver JDBC de MySQL en el ClassLoader.
     */
    static {
        try {
            // Intenta cargar la clase del driver
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            // En caso de no encontrar el driver, muestra un mensaje de error
            System.out.println("Error: driver JDBC no encontrado. " + e.getMessage());
        }
    }

    /**
     * Obtiene una conexión nueva a la base de datos usando los parámetros configurados.
     *
     * @return Connection objeto de conexión a la base de datos
     * @throws SQLException si ocurre un error al intentar conectar
     */
    public static Connection getConnection() throws SQLException {
        // Devuelve una nueva conexión a la base de datos
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Cierra la conexión a la base de datos, si no es null.
     *
     * @param connection la conexión a cerrar
     */
    public static void cerrarConexion(Connection connection) {
        if (connection != null) {
            try {
                // Cierra la conexión
                connection.close();
            } catch (SQLException e) {
                // Muestra un mensaje de error en caso de fallo al cerrar
                System.out.println("Se ha producido un error al cerrar la BBDD: " + e);
            }
        }
    }
}
