package org.practicadao.entidades;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Cuadrilla {
    // Atributos
    @XmlAttribute(name = "id")
    private int id;

    @XmlElement(name = "nombre")
    private String nombre;

    @XmlElement(name = "supervisor_id")
    private int supervisor_id;

    @XmlTransient()
    private List<Trabajador> trabajadores;

    @XmlTransient()
    private List<Olivar> olivares;

    // Constructores
    public Cuadrilla() {
    }

    // Constructor solo nombre;
    public Cuadrilla(String nombre) {
        this.nombre = nombre;
    }

    // Constructor sin listas sin id
    public Cuadrilla(String nombre, int supervisor_id) {
        this.nombre = nombre;
        this.supervisor_id = supervisor_id;
    }

    // Constructor sin listas
    public Cuadrilla(int id, String nombre, int supervisor_id) {
        this.id=id;
        this.nombre = nombre;
        this.supervisor_id = supervisor_id;
    }

    // Constructor completo
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

    public void agregarTrabajador(Trabajador trabajador) {
        if (!this.trabajadores.contains(trabajador)) {
            this.trabajadores.add(trabajador);
        }
    }

    /**
     * Método toString. Para no entrar en el bucle infinito de llamar trabajadores y cuadrillas y
     * olivares y cuadrillas, este toString no muestra las listas de Trabajadores ni de Olivares.
     * Pero las clases Trabajador y Olivar si muestran las cuadrillas.
     * @return
     */
    @Override
    public String toString() {
        return "\n • ID: " + id + "| Nombre: " + nombre + "| Supervisor ID: " + supervisor_id;

    }
}
