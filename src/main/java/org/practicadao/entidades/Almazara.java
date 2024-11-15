package org.practicadao.entidades;

public class Almazara {
    // Atributos
    private int id;
    private String nombre;
    private String ubicacion;
    private double capacidad;

    // Constructores
    public Almazara() {
    }

    // Constructor sin id
    public Almazara(String nombre, String ubicacion, double capacidad) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
    }

    // Constructor completo
    public Almazara(int id, String nombre, String ubicacion, double capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
    }

    // Getters y setters
    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "---------------------------------------------\n" +
                "Almazara: " + "\n" +
                "ID:              " + id + "\n" +
                "Nombre:          " + nombre + "\n" +
                "Ubicacion:       " + ubicacion + "\n" +
                "Capacidad:       " + capacidad + "\n";
    }
}
