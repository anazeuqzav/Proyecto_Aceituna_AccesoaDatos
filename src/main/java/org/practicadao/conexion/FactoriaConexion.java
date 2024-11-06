package org.practicadao.conexion;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Patrón Factoría:
 *
 */
public class FactoriaConexion {

    private static Connection conn;

    private FactoriaConexion() {
        // Constructor privado para evitar instancias
    }

    /**
     * Me devuelve una conexión en función de lo encontrado en
     * el fichero db.properties en el raíz del proyecto.
     *
     * @return el objeto con la conexión a la BBDD o
     * null si no soporta el driver indicado.
     */
    private static Connection connection = null;

    static {
        //obtiene el fichero de propiedades y la url de la base de datos
        try (InputStream input = FactoriaConexion.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                System.out.println("No se encontró el archivo db.properties en el classpath.");
            }

            // Cargar las propiedades desde el archivo
            properties.load(input);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            // Establecer la conexión
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión a la base de datos exitosa.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener la conexión
    public static Connection getConnection() {
        return connection;
    }
}

