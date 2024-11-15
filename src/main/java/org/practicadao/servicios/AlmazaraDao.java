package org.practicadao.servicios;

import org.practicadao.entidades.Almazara;

import java.util.List;

public interface AlmazaraDao {
    Almazara save(Almazara almazara) throws DaoException;
    Almazara findById(int id) throws DaoException;
    List<Almazara> findAll() throws DaoException;
    void update(Almazara almazara) throws DaoException;
    void delete(int id) throws DaoException;
    long count() throws DaoException;
}
