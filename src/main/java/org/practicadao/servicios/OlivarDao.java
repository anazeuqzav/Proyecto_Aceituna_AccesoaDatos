package org.practicadao.servicios;

import org.practicadao.entidades.Olivar;

import java.util.List;

public interface OlivarDao {
    Olivar save(Olivar trabajador) throws DaoException;
    Olivar findById(int id) throws DaoException;
    List<Olivar> findAll() throws DaoException;
    List<Olivar> findByCuadrilla(int idCuadrilla) throws DaoException;
    void update(Olivar olivar) throws DaoException;
    void delete(int id) throws DaoException;
    long count() throws DaoException;
}
