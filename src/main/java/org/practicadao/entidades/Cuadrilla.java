package org.practicadao.entidades;

import java.util.List;

public class Cuadrilla {
    // Atributos
    private int id;
    private String nombre;
    private int supervisor_id;
    private List<Trabajador> trabajadores;
    private List<Olivar> olivares;

    // Constructores
    public Cuadrilla() {
    }

    //Constructor sin listas sin id
    public Cuadrilla(String nombre, int supervisor_id) {
        this.nombre = nombre;
        this.supervisor_id = supervisor_id;
    }

    public Cuadrilla(int id, String nombre, int supervisor_id) {
        this.id=id;
        this.nombre = nombre;
        this.supervisor_id = supervisor_id;
    }

    public Cuadrilla(int id, String nombre, int supervisor_id, List<Trabajador> trabajadores, List<Olivar> olivares) {
        this.id = id;
        this.nombre = nombre;
        this.supervisor_id = supervisor_id;
        this.trabajadores = trabajadores;
        this.olivares = olivares;
    }

    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(int supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    public List<Trabajador> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(List<Trabajador> trabajadores) {
        this.trabajadores = trabajadores;
    }

    public List<Olivar> getOlivares() {
        return olivares;
    }

    public void setOlivares(List<Olivar> olivares) {
        this.olivares = olivares;
    }
}
