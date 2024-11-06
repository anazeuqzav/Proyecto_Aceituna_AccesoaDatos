package org.practicadao.servicios;

import org.practicadao.entidades.Cuadrilla;
import org.practicadao.entidades.Olivar;
import org.practicadao.entidades.Trabajador;

import java.util.List;
import java.util.Optional;

public interface CuadrillaDao {
    Cuadrilla save(Cuadrilla cuadrilla) throws DaoException;
    Optional<Cuadrilla> findById(int id) throws DaoException;
    List<Cuadrilla> findAll() throws DaoException;
    List<Cuadrilla> findByTrabajador(Trabajador trabajador) throws DaoException;
    List<Cuadrilla> findByOlivar(Olivar olivar) throws DaoException;
    void update(Cuadrilla cuadrilla) throws DaoException;
    void delete(int id) throws DaoException;
    long count() throws DaoException;
    void asociarCuadrillaConTrabajador(Cuadrilla cuadrilla, Trabajador trabajador);
    void asociarCuadrillaConOlivar(Cuadrilla cuadrilla, Olivar olivar);
}
