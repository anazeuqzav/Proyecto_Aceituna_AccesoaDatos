package org.practicadao.servicios;

import org.practicadao.entidades.Cuadrilla;

import java.util.List;

public interface CuadrillaDao {
    Cuadrilla save(Cuadrilla cuadrilla) throws DaoException;
    Cuadrilla findById(int id) throws DaoException;
    List<Cuadrilla> findAll() throws DaoException;
    List<Cuadrilla> findByTrabajador(int idTrabajador) throws DaoException;
    List<Cuadrilla> findByOlivar(int idOlivar) throws DaoException;
    void update(Cuadrilla cuadrilla) throws DaoException;
    void delete(int id) throws DaoException;
    long count() throws DaoException;
    void asociarCuadrillaConTrabajador(int idCuadrilla, int idTrabajador);
    void asociarCuadrillaConOlivar(int idCuadrilla, int idOlivar);
}
