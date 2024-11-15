package org.practicadao.serviciosimpl;

import org.practicadao.conexion.FactoriaConexion;
import org.practicadao.entidades.Produccion;
import org.practicadao.servicios.DaoException;
import org.practicadao.servicios.ProduccionDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProduccionDaoImpl implements ProduccionDao {

    // Atributo para la conexion;
    private Connection connection;

    // Atributos queries;
    private static final String SAVE_QUERY = "INSERT INTO produccion (cuadrilla_id, olivar_id, almazara_id, fecha, cantidadRecolectada) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_ONE_QUERY = "SELECT * FROM produccion WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM produccion";
    private static final String FIND_BY_CUADRILLA_ALMAZARA_FECHA = "SELECT * FROM produccion p" +
            " WHERE p.cuadrilla_id = ? AND p.almazara_id = ? AND p.fecha = ?;";
    private static final String FIND_PRODUCCION_BY_ALMAZARA_HASTA_FECHA = "SELECT SUM(p.cantidadRecolectada) AS total_produccion " +
            "FROM produccion p " +
            "WHERE p.almazara_id = ? AND p.fecha <= ?;";
    private static final String UPDATE_QUERY = "UPDATE produccion SET cuadrilla_id = ?, olivar_id = ?, almazara_id = ?, fecha = ?, cantidadRecolectada = ?, WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM produccion WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM produccion";

    // Constructor
    public ProduccionDaoImpl(){ connection = FactoriaConexion.getConnection();}

    /**
     * Método para guardar la produccion en una base de datos
     * @param produccion objeto produccion que se quiere guardar
     * @return produccion guardada
     * @throws DaoException
     */
    @Override
    public Produccion save(Produccion produccion) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, produccion.getCuadrilla_id());
            statement.setInt(2, produccion.getOlivar_id());
            statement.setInt(3, produccion.getAlmazara_id());
            statement.setDate(4, Date.valueOf(produccion.getFecha())); //método para pasar de un localDate a un Date.
            statement.setDouble(5, produccion.getCantidadRecolectada());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La inserción no tuvo éxito, no se creó ninguna produccion.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    produccion.setId(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("No se obtuvo el ID generado para la produccion.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al guardar la produccion.", e);
        }

        return produccion;
    }

    /**
     * Método para encontrar una produccion por un ID en la base de datos
     * @param id identificador de la produccion que se quiere buscar
     * @return produccion encontrada en la base de datos
     * @throws DaoException
     */
    @Override
    public Optional<Produccion> findById(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ONE_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToProduccion(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al obtener la produccion por el ID.", e);
        }

        return Optional.empty();
    }

    /**
     * Método para recuperar todas las producciones guardadas en la base de datos
     * @return una lista de produccion
     * @throws DaoException
     */
    @Override
    public List<Produccion> findAll() throws DaoException {
        List<Produccion> produccion = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                produccion.add(mapResultSetToProduccion(resultSet));
            }

        } catch (SQLException e) {
            throw new DaoException("Error al obtener todos los clientes.", e);
        }
        return produccion;
    }

    /**
     * Recupera una produccion, de una cuadrilla concreta en una almazara concreta en una fecha concreta
     * @param idCuadrilla Identificador de la cuadrilla que queremos buscar
     * @param idAlmazara Identificador de la almazara a buscar
     * @param fecha Fecha de la produccion que queremos buscar
     * @return Produccion en esos datos concretos.
     */
    public Produccion findByCuadrillaAlmazaraYFecha(int idCuadrilla, int idAlmazara, LocalDate fecha) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND_BY_CUADRILLA_ALMAZARA_FECHA)) {
            stmt.setInt(1, idCuadrilla);
            stmt.setInt(2, idAlmazara);
            stmt.setDate(3, java.sql.Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Produccion produccion = mapResultSetToProduccion(rs);
                return produccion;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Devuelve la suma total de la cantidad recolectada de la produccion de una almazara HASTA una determinada fecha
     * @param idAlmazara el identificador de la almazara
     * @param fecha Hasta la fecha que se quiere mirar
     * @return cantidad total recolectada en ese tiempo
     */
    public double findTotalProduccionByAlmazaraFecha(int idAlmazara, LocalDate fecha) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND_PRODUCCION_BY_ALMAZARA_HASTA_FECHA)) {
            stmt.setInt(1, idAlmazara);
            stmt.setDate(2, java.sql.Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total_produccion"); // Obtiene la suma de la producción
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Si no hay resultados, devuelve 0
    }


    /**
     * Método para actualizar los datos de una produccion en la base de datos
     * @param produccion la produccion que se quiere actualizar
     * @throws DaoException
     */
    @Override
    public void update(Produccion produccion) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, produccion.getCuadrilla_id());
            statement.setInt(2, produccion.getOlivar_id());
            statement.setInt(3, produccion.getAlmazara_id());
            statement.setDate(4, Date.valueOf(produccion.getFecha()));
            statement.setDouble(5, produccion.getCantidadRecolectada());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La actualización no tuvo éxito, no se encontró la almazara.");
            }

        } catch (SQLException e) {
            throw new DaoException("Error al actualizar la almazara.", e);
        }
    }

    /**
     * Método para eliminar una produccion de la base de datos
     * @param id identificador de la produccion a eliminar
     * @throws DaoException
     */
    @Override
    public void delete(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La eliminación no tuvo éxito, no se encontró la produccion.");
            }

        } catch (SQLException e) {
            throw new DaoException("Error al eliminar la produccion.", e);
        }
    }

    /**
     * Método para contar la cantidad total de producciones que existen en la base de datos
     * @return número total de producciones
     * @throws DaoException
     */
    @Override
    public long count() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(COUNT_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al contar las producciones.", e);
        }
        return 0;
    }

    /**
     * Método para pasar de los datos de la base de datos a un objeto Produccion
     * @param resultSet los datos de la base de datos de una produccion
     * @return objeto Produccion
     * @throws SQLException
     */
    private Produccion mapResultSetToProduccion(ResultSet resultSet) throws SQLException {
        Produccion produccion = new Produccion();
        produccion.setId(resultSet.getInt("id"));
        produccion.setCuadrilla_id(resultSet.getInt("cuadrilla_id"));
        produccion.setOlivar_id(resultSet.getInt("olivar_id"));
        produccion.setAlmazara_id(resultSet.getInt("almazara_id"));
        produccion.setFecha(resultSet.getDate("fecha").toLocalDate());
        produccion.setCantidadRecolectada(resultSet.getDouble("cantidadRecolectada"));

        return produccion;
    }

}
