package org.practicadao;

import org.practicadao.entidades.Cuadrilla;
import org.practicadao.entidades.Trabajador;
import org.practicadao.serviciosimpl.CuadrillaDaoImpl;
import org.practicadao.serviciosimpl.TrabajadorDaoImpl;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        TrabajadorDaoImpl trabajadorDao = new TrabajadorDaoImpl();
        CuadrillaDaoImpl cuadrillaDao = new CuadrillaDaoImpl();

        Trabajador t1 = new Trabajador("Ana", 29, "asdf", 1000.00);
        Cuadrilla c1 = new Cuadrilla("fasdf", 14);

        trabajadorDao.save(t1);
        cuadrillaDao.save(c1);

        cuadrillaDao.asociarCuadrillaConTrabajador(c1.getId(), t1.getId());

    }
}