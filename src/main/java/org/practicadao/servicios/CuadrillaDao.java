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
    List<Cuadrilla> findByTrabajador(int idTrabajador) throws DaoException;
    List<Cuadrilla> findByOlivar(int idOlivar) throws DaoException;
    void update(Cuadrilla cuadrilla) throws DaoException;
    void delete(int id) throws DaoException;
    long count() throws DaoException;
    void asociarCuadrillaConTrabajador(int idCuadrilla, int idTrabajador);
    void asociarCuadrillaConOlivar(int idCuadrilla, int idOlivar);
}
