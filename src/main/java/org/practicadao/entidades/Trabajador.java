package org.practicadao.entidades;

import java.util.List;

public class Trabajador {

    // Atributos
    private int id;
    private String nombre;
    private int edad;
    private String puesto;
    private double salario;
    private List<Cuadrilla> cuadrillas;

    // Constructores
    public Trabajador() {

    }

    // Constructor sin lista

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
}
