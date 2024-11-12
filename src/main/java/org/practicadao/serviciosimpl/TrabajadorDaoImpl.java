package org.practicadao.serviciosimpl;

import org.practicadao.conexion.FactoriaConexion;
import org.practicadao.entidades.Trabajador;
import org.practicadao.servicios.DaoException;
import org.practicadao.servicios.TrabajadorDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrabajadorDaoImpl implements TrabajadorDao {

    // Atributo para la conexion
    private Connection connection;

    // Atributos: Querys
    private static final String SAVE_QUERY = "INSERT INTO trabajador (nombre, edad, puesto, salario) VALUES (?, ?, ?, ?)";
    private static final String FIND_ONE_QUERY = "SELECT * FROM trabajador WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM trabajador";
    private static final String FIND_BY_CUADRILLA = "SELECT t.id, t.nombre, t.edad, t.puesto, t.salario FROM trabajador t " +
            "INNER JOIN cuadrilla_trabajador ct ON t.id = ct.trabajador_id" +
            "WHERE ct.cuadrilla_id = ?";
    private static final String UPDATE_QUERY = "UPDATE trabajador SET nombre = ?, edad = ?, puesto = ?, salario = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM trabajador WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM trabajador";

    // Constructor
    public TrabajadorDaoImpl(){ connection = FactoriaConexion.getConnection();}


    /**
     * Método para guardar un trabajador en la tabla trabajador de la bases de datos aceituna_db.
     *
     * @param trabajador el objeto Trabajador para guardar
     * @return el objeto Trabajador guardaro
     * @throws DaoException
     */
    @Override
    public Trabajador save(Trabajador trabajador) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, trabajador.getNombre());
            statement.setInt(2, trabajador.getEdad());
            statement.setString(3, trabajador.getPuesto());
            statement.setDouble(4, trabajador.getSalario());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La inserción no tuvo éxito, no se creó ningún trabajador.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    trabajador.setId(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("No se obtuvo el ID generado para el trabajador.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al guardar el trabajador.", e);
        }

        return trabajador;
    }

    /**
     * Método para buscar un trabajador por medio de un id en la tabla trabajador de la base de datos aceituna_db
     * @param id identificador del trabajador a buscar en la tabla
     * @return objeto Trabajador Encontrado
     * @throws DaoException
     */
    @Override
    public Optional<Trabajador> findById(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ONE_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToTrabajador(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al obtener el trabajador por el ID.", e);
        }

        return Optional.empty();
    }

    /**
     * Método para buscar todos los trabajadores
     *
     * @return Lista de trabajadores
     * @throws DaoException
     */
    @Override
    public List<Trabajador> findAll() throws DaoException {
        List<Trabajador> trabajadores = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                trabajadores.add(mapResultSetToTrabajador(resultSet));
            }

        } catch (SQLException e) {
            throw new DaoException("Error al obtener todos los clientes.", e);
        }
        return trabajadores;
    }

    /**
     * Método para buscar un trabajador por su cuadrilla
     *
     * @param idCuadrilla identificador de la cuadrilla
     * @return devuelve la lista de trabajadores que trabajan en esa cuadrilla
     * @throws DaoException
     */
    @Override
    public List<Trabajador> findByCuadrilla(int idCuadrilla) throws DaoException {
        List<Trabajador> trabajadores = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(FIND_BY_CUADRILLA)) {
            stmt.setInt(1, idCuadrilla);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Trabajador trabajador = new Trabajador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("puesto"),
                        rs.getDouble("salario")
                );
                trabajadores.add(trabajador);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al obtener todos los clientes.", e);
        }
        return trabajadores;
    }

    /**
     * Actualizar datos de un trabajador de la base de datos
     *
     * @param trabajador que se desea eliminar
     * @throws DaoException
     */
    @Override
    public void update(Trabajador trabajador) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, trabajador.getNombre());
            statement.setInt(2, trabajador.getEdad());
            statement.setString(3, trabajador.getPuesto());
            statement.setDouble(4, trabajador.getSalario());
            statement.setInt(5, trabajador.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La actualización no tuvo éxito, no se encontró el trabajador.");
            }

        } catch (SQLException e) {
            throw new DaoException("Error al actualizar el trabajador.", e);
        }
    }

    /**
     * Elimina un trabajador de la base de datos
     *
     * @param id identificador del trabajador a eliminar
     * @throws DaoException
     */
    @Override
    public void delete(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("La eliminación no tuvo éxito, no se encontró el trabajador.");
            }

        } catch (SQLException e) {
            throw new DaoException("Error al eliminar el trabajador.", e);
        }

    }

    /**
     * Contar número de trabajadores que hay en la tabla
     *
     * @return el número de trabajadores que hay en la tabla
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
            throw new DaoException("Error al contar los trabajadores.", e);
        }
        return 0;
    }

    private Trabajador mapResultSetToTrabajador(ResultSet resultSet) throws SQLException {
        Trabajador trabajador = new Trabajador();
        trabajador.setId(resultSet.getInt("id"));
        trabajador.setNombre(resultSet.getString("nombre"));
        trabajador.setEdad(resultSet.getInt("edad"));
        trabajador.setPuesto(resultSet.getString("puesto"));

        return trabajador;
    }
}
