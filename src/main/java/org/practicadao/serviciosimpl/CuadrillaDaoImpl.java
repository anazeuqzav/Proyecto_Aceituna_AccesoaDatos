package org.practicadao.serviciosimpl;

import org.practicadao.conexion.FactoriaConexion;
import org.practicadao.entidades.Cuadrilla;
import org.practicadao.servicios.CuadrillaDao;
import org.practicadao.servicios.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuadrillaDaoImpl implements CuadrillaDao {

    // Atributo para la conexion
    private Connection connection;

    // Queries
    private static final String SAVE_QUERY = "INSERT INTO cuadrilla (nombre, supervisor_id) VALUES (?, ?)";
    private static final String FIND_ONE_QUERY = "SELECT * FROM cuadrilla WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM cuadrilla";
    private static final String FIND_BY_TRABAJADOR = "SELECT c.id, c.nombre, c.supervisor_id FROM cuadrilla c " +
            "INNER JOIN cuadrilla_trabajador ct ON c.id = ct.cuadrilla_id " +
            "WHERE ct.trabajador_id = ?";
    private static final String FIND_BY_SUPERVISOR = "SELECT c.id, c.nombre, c.supervisor_id FROM cuadrilla c " +
            "WHERE c.supervisor_id = ? ";
    private static final String FIND_BY_OLIVAR = "SELECT c.id, c.nombre, c.supervisor_id FROM cuadrilla c " +
            "INNER JOIN cuadrilla_olivar co ON c.id = co.cuadrilla_id "  +
            "WHERE co.olivar_id = ?;";
    private static final String UPDATE_QUERY = "UPDATE cuadrilla SET nombre = ?, supervisor_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM cuadrilla WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM cuadrilla";

    // Constructor
    public CuadrillaDaoImpl() {
        connection = FactoriaConexion.getConnection();
    }

    /**
     * Método que guarda una cuadrilla en la bases de datos aceituna_db, en la tabla cuadrilla.
     * @param cuadrilla el objeto cuadrilla para guardar
     * @return la cuadrilla guardada con id incluido (autoincrement)
     * @throws DaoException
     */
    @Override
    public Cuadrilla save(Cuadrilla cuadrilla) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, cuadrilla.getNombre());
            statement.setInt(2,cuadrilla.getSupervisor_id());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La inserción no tuvo éxito, no se creó ninguna cuadrilla.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cuadrilla.setId(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("No se obtuvo el ID generado para la cuadrilla.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al guardar la cuadrilla.", e);
        }
        return cuadrilla;
    }

    /**
     * Método que busca una cuadrilla en la base de datos por el id
     * @param id el identificador de la cuadrilla a buscar
     * @return la cuadrilla encontrada
     * @throws DaoException
     */
    @Override
    public Cuadrilla findById(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ONE_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCuadrilla(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al obtener la cuadrilla por el ID.", e);
        }
        return null;
    }

    /**
     * Método que busca todas las cuadrillas que existen en la base de datos
     * @return una lista de objetos cuadrilla
     * @throws DaoException
     */
    @Override
    public List<Cuadrilla> findAll() throws DaoException {
        List<Cuadrilla> cuadrillas = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                cuadrillas.add(mapResultSetToCuadrilla(resultSet));
            }

        } catch (SQLException e) {
            throw new DaoException("Error al obtener todos las cuadrillas.", e);
        }
        return cuadrillas;
    }

    /**
     * Método para obtener de la base de datos las cuadrillas en las que trabaja un trabajador
     * @param idTrabajador identificador del trabajador
     * @return una lista de cuadrillas en las que ha trabajado
     * @throws DaoException
     */
    @Override
    public List<Cuadrilla> findByTrabajador(int idTrabajador) throws DaoException {
        List<Cuadrilla> cuadrillas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(FIND_BY_TRABAJADOR)) {
            stmt.setInt(1, idTrabajador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cuadrillas.add(mapResultSetToCuadrilla(rs));
            }

        } catch (SQLException e) {
            throw new DaoException("Error al obtener todas las cuadrillas de un trabajador.", e);
        }
        return cuadrillas;
    }

    /**
     * Método para obtener las cuadrillas de la base de datos que han trabajado en un olivar concreto
     * @param idOlivar identificador del olivar
     * @return una lista de cuadrillas
     * @throws DaoException
     */
    @Override
    public List<Cuadrilla> findByOlivar(int idOlivar) throws DaoException {
        List<Cuadrilla> cuadrillas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(FIND_BY_OLIVAR)) {
            stmt.setInt(1, idOlivar);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cuadrillas.add(mapResultSetToCuadrilla(rs));
            }

        } catch (SQLException e) {
            throw new DaoException("Error al obtener todas las cuadrillas de un olivar.", e);
        }
        return cuadrillas;
    }

    /**
     * Método para obtener una lista de cuadrillas que supervisa un trabajador.
     * @param idSupervisor identificador del supervisor
     * @return lista de cuadrillas
     * @throws DaoException
     */
    public List<Cuadrilla> findBySupervisor(int idSupervisor) throws DaoException {
        List<Cuadrilla> cuadrillas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(FIND_BY_SUPERVISOR)) {
            stmt.setInt(1, idSupervisor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cuadrillas.add(mapResultSetToCuadrilla(rs));
            }

        } catch (SQLException e) {
            throw new DaoException("Error al obtener todas las cuadrillas de un olivar.", e);
        }
        return cuadrillas;
    }


    /**
     * Método para actualizar los datos de una cuadrilla en la base de datos
     *
     * @param cuadrilla la cuadrilla que se quiere actualizar
     * @throws DaoException
     */
    @Override
    public void update(Cuadrilla cuadrilla) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, cuadrilla.getNombre());
            statement.setInt(2, cuadrilla.getSupervisor_id());
            statement.setInt(3, cuadrilla.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La actualización no tuvo éxito, no se encontró la cuadrilla.");
            }

        } catch (SQLException e) {
            throw new DaoException("Error al actualizar la cuadrilla.", e);
        }
    }

    /**
     * Método para eliminar una cuadrilla de la base de datos
     * @param id identificador de la cuadrilla que se desea borrar
     * @throws DaoException
     */
    @Override
    public void delete(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La eliminación no tuvo éxito, no se encontró la cuadrilla.");
            }
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar la cuadrilla.", e);
        }
    }

    /**
     * Método para contar todas las cuadrillas que existen en la base de datos
     * @return número de cuadrillas
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
            throw new DaoException("Error al contar las cuadrillas.", e);
        }
        return 0;
    }

    /**
     * Permite asociar una cuadrilla con un trabajador.
     * @param idCuadrilla  identificador de la cuadrilla
     * @param idTrabajador identificador del trabajador
     */
    @Override
    public void asociarCuadrillaConTrabajador(int idCuadrilla, int idTrabajador) {
        // Comprueba si el trabajador ya está asociado a la cuadrilla
        if (!isTrabajadorAsignado(idCuadrilla, idTrabajador)) {
            String query = "INSERT INTO cuadrilla_trabajador (cuadrilla_id, trabajador_id) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, idCuadrilla);
                stmt.setInt(2, idTrabajador);
                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new DaoException("Error al asociar el trabajador a la cuadrilla.", e);
            }
        } else {
            System.out.println("El trabajador con ID " + idTrabajador + " ya está asociado a la cuadrilla con ID " + idCuadrilla);
        }
    }

    /**
     * Permite asociar una cuadrilla con un olivar
     * @param idCuadrilla identificador de la cuadrilla
     * @param idOlivar    identificador del olivar
     */
    @Override
    public void asociarCuadrillaConOlivar(int idCuadrilla, int idOlivar) {
        // Comprobar que el olivar no esté asociado ya con la cuadrilla
        if (!isOlivarAsignado(idCuadrilla, idOlivar)) {
            String query = "INSERT INTO cuadrilla_olivar (cuadrilla_id, olivar_id) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, idCuadrilla);
                stmt.setInt(2, idOlivar);
                stmt.executeUpdate();
                } catch (SQLException e) {
                throw new DaoException("Error al asociar el olivo a la cuadrilla.", e);
            }

        } else {
            System.out.println("El trabajador con ID " + idOlivar + " ya está asociado a la cuadrilla con ID " + idCuadrilla);
        }
    }

    /**
     * Método para pasar de un conjunto de resultados de SQL a un objeto Cuadrilla
     * @param resultSet datos de la base de datos
     * @return devolver cuadrilla
     * @throws SQLException
     */
    private Cuadrilla mapResultSetToCuadrilla(ResultSet resultSet) throws SQLException {
        Cuadrilla cuadrilla = new Cuadrilla();
        cuadrilla.setId(resultSet.getInt("id"));
        cuadrilla.setNombre(resultSet.getString("nombre"));
        cuadrilla.setSupervisor_id(resultSet.getInt("supervisor_id"));

        return cuadrilla;
    }

    /**
     * Método para comprobar si un trabajador ha sido asignado ya a una cuadrilla.
     * Se utiliza a la hora de cargar los datos
     * @param cuadrillaId Identificador de la cuadrilla
     * @param trabajadorId Identificador del trabajador
     * @return true si el trabajador ya ha sido asignado; false si no.
     */
    private boolean isTrabajadorAsignado(int cuadrillaId, int trabajadorId) {
        String query = "SELECT 1 FROM cuadrilla_trabajador WHERE cuadrilla_id = ? AND trabajador_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cuadrillaId);
            stmt.setInt(2, trabajadorId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Si existe una fila, significa que el trabajador ya está asignado
            }
        } catch (SQLException e) {
            throw new DaoException("Error al verificar la asignación del trabajador.", e);
        }
    }

    /**
     * Método para comprobar si un olivar ha sido asignado ya a una cuadrilla.
     * Se utiliza a la hora de cargar los datos
     * @param cuadrillaId Identificador de la cuadrilla
     * @param olivarId Identificador del olivar
     * @return true si el olivar ya ha sido asignado; false si no.
     */
    private boolean isOlivarAsignado(int cuadrillaId, int olivarId) {
        String query = "SELECT 1 FROM cuadrilla_olivar WHERE cuadrilla_id = ? AND olivar_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cuadrillaId);
            stmt.setInt(2, olivarId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Si existe una fila, significa que el olivar ya está asignado
            }
        } catch (SQLException e) {
            throw new DaoException("Error al verificar la asignación del olivar.", e);
        }
    }
}

