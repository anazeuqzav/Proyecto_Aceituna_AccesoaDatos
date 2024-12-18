package org.practicadao.entidades;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Trabajador {

    // Atributos
    @XmlAttribute(name = "id")
    private int id;

    @XmlElement(name = "nombre")
    private String nombre;

    @XmlElement(name = "edad")
    private int edad;

    @XmlElement(name = "puesto")
    private String puesto;

    @XmlElement(name = "salario")
    private double salario;

    @XmlElementWrapper(name = "cuadrillas")
    @XmlElement(name = "cuadrilla")
    private List<Cuadrilla> cuadrillas;

    // Constructores
    public Trabajador() {

    }

    // Constructor sin lista sin id
    public Trabajador(String nombre, int edad, String puesto, double salario) {
        this.nombre = nombre;
        this.edad = edad;
        this.puesto = puesto;
        this.salario = salario;
    }
    public Trabajador(int id, String nombre, int edad, String puesto, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.puesto = puesto;
        this.salario = salario;
    }

    public Trabajador(int id, String nombre, int edad, String puesto, double salario, List<Cuadrilla> cuadrillas) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.puesto = puesto;
        this.salario = salario;
        this.cuadrillas = cuadrillas;
    }
    // Getters y Setters
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<Cuadrilla> getCuadrillas() {
        return cuadrillas;
    }

    public void setCuadrillas(List<Cuadrilla> cuadrillas) {
        this.cuadrillas = cuadrillas;
    }

    public void agregarCuadrilla(Cuadrilla cuadrilla) {
        if (!this.cuadrillas.contains(cuadrilla)) {
            this.cuadrillas.add(cuadrilla);
        }
    }

    @Override
    public String toString() {
        return "---------------------------------------------\n" +
                "Trabajador  " + "\n" +
                "ID:         " + id + "\n" +
                "Nombre:     " + nombre + "\n" +
                "Edad:       " + edad + " años\n" +
                "Puesto:     " + puesto + "\n" +
                "Salario:    " + salario + "\n" +
                "Cuadrillas: " + (cuadrillas == null || cuadrillas.isEmpty() ? "No tiene" : cuadrillas) + "\n";
    }
}
