package org.practicadao.servicios;

import org.practicadao.entidades.Almazara;

import java.util.List;
import java.util.Optional;

public interface AlmazaraDao {
    Almazara save(Almazara almazara) throws DaoException;
    Optional<Almazara> findById(int id) throws DaoException;
    List<Almazara> findAll() throws DaoException;
    void update(Almazara almazara) throws DaoException;
    void delete(int id) throws DaoException;
    long count() throws DaoException;
}
