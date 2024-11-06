package org.practicadao.servicios;

import org.practicadao.entidades.Cuadrilla;
import org.practicadao.entidades.Trabajador;

import java.util.List;
import java.util.Optional;

public interface TrabajadorDao {
    Trabajador save(Trabajador trabajador) throws DaoException;
    Optional<Trabajador> findById(int id) throws DaoException;
    List<Trabajador> findAll() throws DaoException;
    List<Trabajador> findByCuadrilla(int idCuadrilla) throws DaoException;
    void update(Trabajador trabajador) throws DaoException;
    void delete(int id) throws DaoException;
    long count() throws DaoException;
}
