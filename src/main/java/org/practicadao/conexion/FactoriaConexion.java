package org.practicadao.conexion;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Patrón Factoría:
 *
 */
public class FactoriaConexion {
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

    /**
     * Crea un disparador para evitar que se introduzca una cantidad recolectada
     * negativa
     */
    public static void createTriggers(Connection connection) {
            try (Statement statement = connection.createStatement()) {

                // Eliminar el trigger si ya existe
                String sql = "DROP TRIGGER IF EXISTS `evitar_cantidad_negativa`";
                statement.execute(sql);

                // Crear el trigger
                sql = "CREATE TRIGGER `evitar_cantidad_negativa` \n" +
                        "BEFORE INSERT ON `produccion` \n" +
                        "FOR EACH ROW \n" +
                        "BEGIN \n" +
                        "    IF NEW.cantidadRecolectada < 0 THEN \n" +
                        "        SIGNAL SQLSTATE '45000' \n" +
                        "        SET MESSAGE_TEXT = 'La cantidad recolectada no puede ser negativa.'; \n" +
                        "    END IF; \n" +
                        "END;";
                statement.execute(sql);

                System.out.println("Disparador creado correctamente.");

            } catch (SQLException e) {
                System.err.println("Error al crear el disparador: " + e.getMessage());
            }
        }
    }



