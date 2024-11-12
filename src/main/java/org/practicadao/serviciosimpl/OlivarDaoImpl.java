package org.practicadao.serviciosimpl;

import org.practicadao.conexion.FactoriaConexion;
import org.practicadao.entidades.Cuadrilla;
import org.practicadao.entidades.Olivar;
import org.practicadao.entidades.Trabajador;
import org.practicadao.servicios.DaoException;
import org.practicadao.servicios.OlivarDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OlivarDaoImpl implements OlivarDao {

    // Atributo para la conexion
    private Connection connection;

    // Atributos: Querys
    private static final String SAVE_QUERY = "INSERT INTO olivar (ubicacion, hectareas, produccionAnual) VALUES (?, ?, ?)";
    private static final String FIND_ONE_QUERY = "SELECT * FROM olivar WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM olivar";
    private static final String FIND_BY_CUADRILLA = "SELECT o.id, o.ubicacion, o.hectareas, o.produccionAnual, FROM olivar o " +
            "INNER JOIN cuadrilla_olivar co ON o.id = co.olivar_id" +
            "WHERE co.cuadrilla_id = ?";
    private static final String UPDATE_QUERY = "UPDATE olivar SET ubcacion = ?, hectareas = ?, produccionAnual = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM olivar WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM olivar";

    // Constructor
    public OlivarDaoImpl(){ connection = FactoriaConexion.getConnection();}

    /**
     * Método para guardar un olivar en la base de datos
     *
     * @param olivar que se desea guardar
     * @return devuelve el olivar que se ha guardado
     * @throws DaoException
     */
    @Override
    public Olivar save(Olivar olivar) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, olivar.getUbicacion());
            statement.setDouble(2, olivar.getHectareas());
            statement.setDouble(3, olivar.getProduccionAnual());


            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La inserción no tuvo éxito, no se creó ningún olivar.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    olivar.setId(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("No se obtuvo el ID generado para el olivar.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al guardar el olivar.", e);
        }
        return olivar;
    }

    /**
     * Método para encontrar un olivar por un id en la base de datos
     *
     * @param id identificador de l olivar que se desea buscar
     * @return el olivar buscado si existe
     * @throws DaoException
     */
    @Override
    public Optional<Olivar> findById(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ONE_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToOlivar(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al obtener el olivar por el ID.", e);
        }
        return Optional.empty();
    }

    /**
     * Encontrar todos los olivares que existen en la base de datos
     *
     * @return una lista de olivares
     * @throws DaoException
     */
    @Override
    public List<Olivar> findAll() throws DaoException {
        List<Olivar> olivares = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                olivares.add(mapResultSetToOlivar(resultSet));
            }

        } catch (SQLException e) {
            throw new DaoException("Error al obtener todos los olivares.", e);
        }
        return olivares;
    }

    /**
     * Método para encontrar un olivar por el id de una cuadrilla
     *
     * @param idCuadrilla identificador de la cuadrilla que ha trabajado en el olivar
     * @return una lista de olivares donde ha trabajado la cuadrilla
     * @throws DaoException
     */
    @Override
    public List<Olivar> findByCuadrilla(int idCuadrilla) throws DaoException {
        List<Olivar> olivares = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(FIND_BY_CUADRILLA)) {
            stmt.setInt(1, idCuadrilla);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Olivar olivar = new Olivar(
                        rs.getInt("id"),
                        rs.getString("ubicacion"),
                        rs.getDouble("hectareas"),
                        rs.getDouble("produccionAnual")
                );
                olivares.add(olivar);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al obtener todos los olivares.", e);
        }
        return olivares;
    }

    /**
     * Método para actualizar los datos de un olivar en la base de datos
     *
     * @param olivar el olivar que se quiere actualizar
     * @throws DaoException
     */
    @Override
    public void update(Olivar olivar) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, olivar.getUbicacion());
            statement.setDouble(2, olivar.getHectareas());
            statement.setDouble(3, olivar.getProduccionAnual());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La actualización no tuvo éxito, no se encontró el olivo.");
            }

        } catch (SQLException e) {
            throw new DaoException("Error al actualizar el olivo.", e);
        }
    }

    /**
     * Método para eliminar un olivar de la base de datos
     *
     * @param id identificador del olivar que se quiere eliminar
     * @throws DaoException
     */
    @Override
    public void delete(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La eliminación no tuvo éxito, no se encontró el olivar.");
            }

        } catch (SQLException e) {
            throw new DaoException("Error al eliminar el olivar.", e);
        }

    }

    /**
     * Método que cuenta la cantidad de olivares que hay en la base de datos
     *
     * @return número de olivares que se encuentran guardados en la base de datos
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
            throw new DaoException("Error al contar los olivos.", e);
        }
        return 0;
    }

    private Olivar mapResultSetToOlivar(ResultSet resultSet) throws SQLException {
        Olivar olivar = new Olivar();
        olivar.setId(resultSet.getInt("id"));
        olivar.setUbicacion(resultSet.getString("ubicacion"));
        olivar.setHectareas(resultSet.getDouble("hectareas"));
        olivar.setProduccionAnual(resultSet.getDouble("produccionAnual"));

        return olivar;
    }
}
