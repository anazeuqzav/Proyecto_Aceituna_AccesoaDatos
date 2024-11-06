package org.practicadao.servicios;

import org.practicadao.entidades.Produccion;

import java.util.List;
import java.util.Optional;

public interface ProduccionDao {
    Produccion save(Produccion produccion) throws DaoException;
    Optional<Produccion> findById(int id) throws DaoException;
    List<Produccion> findAll() throws DaoException;
    void update(Produccion produccion) throws DaoException;
    void delete(int id) throws DaoException;
    long count() throws DaoException;
}
