package org.practicadao.serviciosimpl;

import org.practicadao.conexion.FactoriaConexion;
import org.practicadao.entidades.Almazara;
import org.practicadao.servicios.AlmazaraDao;
import org.practicadao.servicios.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlmazaraDaoImpl implements AlmazaraDao {

    //Atributo para la conexion
    private Connection connection;

    // Atributos: queries
    private static final String SAVE_QUERY = "INSERT INTO almazara (nombre, ubicacion, capacidad) VALUES (?, ?, ?)";
    private static final String FIND_ONE_QUERY = "SELECT * FROM almazara WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM almazara";
    private static final String FIND_BY_CUADRILLA = "SELECT DISTINCT a.id, a.nombre, a.ubicacion, a.capacidad FROM almazara a " +
            "JOIN produccion p ON p.almazara_id = a.id " +
            "WHERE p.cuadrilla_id = ?;";
    private static final String UPDATE_QUERY = "UPDATE almazara SET nombre = ?, ubicacion = ?, capacidad = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM almazara WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM almazara";

    // Constructor
    public AlmazaraDaoImpl(){ connection = FactoriaConexion.getConnection();}

    /**
     * Método para guardar una almazara en la base de datos
     * @param almazara que se quiere guardar
     * @return la almazara que se ha guardado
     * @throws DaoException
     */
    @Override
    public Almazara save(Almazara almazara) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, almazara.getNombre());
            statement.setString(2, almazara.getUbicacion());
            statement.setDouble(3, almazara.getCapacidad());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La inserción no tuvo éxito, no se creó ningúna almazara.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    almazara.setId(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("No se obtuvo el ID generado para la almazara.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al guardar la almazara.", e);
        }

        return almazara;
    }

    /**
     * Método para buscar una almazara por id
     * @param id identificador de la almazara a buscar
     * @return almazara si se encuentra
     * @throws DaoException
     */
    @Override
    public Almazara findById(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ONE_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToAlmazara(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al obtener la almazara por el ID.", e);
        }

        return null;
    }

    /**
     * Método para listar todas las almazaras que se encuentran en la base de datos
     * @return lista de almazara
     * @throws DaoException
     */
    @Override
    public List<Almazara> findAll() throws DaoException {
        List<Almazara> almazaras = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                almazaras.add(mapResultSetToAlmazara(resultSet));
            }

        } catch (SQLException e) {
            throw new DaoException("Error al obtener todos las almazaras.", e);
        }
        return almazaras;
    }

    /**
     * Método para devolver una lista de almazaras donde han llevado la produccion una cuadrilla
     * concreta
     * @param idCuadrilla el Identificador de la cuadrilla que queremos mirar
     * @return lista de almazaras
     */
    public List<Almazara> findByCuadrilla(int idCuadrilla) {
        List<Almazara> almazaras = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(FIND_BY_CUADRILLA)) {
            stmt.setInt(1, idCuadrilla);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                almazaras.add(mapResultSetToAlmazara(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return almazaras;
    }

    /**
     * Método para actualizar los datos de una almazara que se encuentra en la base de datos
     * @param almazara para actualizar
     * @throws DaoException
     */
    @Override
    public void update(Almazara almazara) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, almazara.getNombre());
            statement.setString(2, almazara.getUbicacion());
            statement.setDouble(3, almazara.getCapacidad());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La actualización no tuvo éxito, no se encontró la almazara.");
            }

        } catch (SQLException e) {
            throw new DaoException("Error al actualizar la almazara.", e);
        }
    }

    /**
     * Método para eliminar una almazara de la base de datos
     * @param id identificador de la almazara a eliminar
     * @throws DaoException
     */
    @Override
    public void delete(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La eliminación no tuvo éxito, no se encontró la almazara.");
            }

        } catch (SQLException e) {
            throw new DaoException("Error al eliminar la almazara.", e);
        }
    }

    /**
     * Método para contar la cantidad de almazaras que existen en la base de datos
     * @return el número de almazaras
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
            throw new DaoException("Error al contar las almazaras.", e);
        }
        return 0;
    }

    /**
     * Método para pasar de un set de resultados de la base de datos a un objeto
     * @param resultSet datos desde la base de datos
     * @return objeto Almazara
     * @throws SQLException
     */
    private Almazara mapResultSetToAlmazara(ResultSet resultSet) throws SQLException {
        Almazara almazara = new Almazara();
        almazara.setId(resultSet.getInt("id"));
        almazara.setNombre(resultSet.getString("nombre"));
        almazara.setUbicacion(resultSet.getString("ubicacion"));
        almazara.setCapacidad(resultSet.getDouble("capacidad"));

        return almazara;
    }
}