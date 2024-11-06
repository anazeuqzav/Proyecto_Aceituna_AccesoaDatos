package org.practicadao.servicios;

import org.practicadao.entidades.Cuadrilla;
import org.practicadao.entidades.Olivar;
import org.practicadao.entidades.Trabajador;

import java.util.List;
import java.util.Optional;

public interface OlivarDao {
    Olivar save(Olivar trabajador) throws DaoException;
    Optional<Olivar> findById(int id) throws DaoException;
    List<Olivar> findAll() throws DaoException;
    List<Olivar> findByCuadrilla(Cuadrilla cuadrilla) throws DaoException;
    void update(Olivar olivar) throws DaoException;
    void delete(int id) throws DaoException;
    long count() throws DaoException;
}
