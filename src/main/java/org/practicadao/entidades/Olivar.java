package org.practicadao.entidades;

import java.util.List;

public class Olivar {
    // Atributos
    private int id;
    private String ubicacion;
    private double hectareas;
    private double produccionAnual;
    private List<Cuadrilla> cuadrillas;

    // Constructores
    public Olivar() {

    }
    //Constructor sin id y sin listas
    public Olivar(String ubicacion, double hectareas, double produccionAnual) {
        this.ubicacion = ubicacion;
        this.hectareas = hectareas;
        this.produccionAnual = produccionAnual;
    }

    //Constructor sin listas
    public Olivar(int id, String ubicacion, double hectareas, double produccionAnual) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.hectareas = hectareas;
        this.produccionAnual = produccionAnual;
    }


    //Constructor completo
    public Olivar(double hectareas, int id, double produccionAnual, String ubicacion, List<Cuadrilla> cuadrillas) {
        this.hectareas = hectareas;
        this.id = id;
        this.produccionAnual = produccionAnual;
        this.ubicacion = ubicacion;
        this.cuadrillas = cuadrillas;
    }
    // Getters y Setters

    public double getHectareas() {
        return hectareas;
    }

    public void setHectareas(double hectareas) {
        this.hectareas = hectareas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getProduccionAnual() {
        return produccionAnual;
    }

    public void setProduccionAnual(double produccionAnual) {
        this.produccionAnual = produccionAnual;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Cuadrilla> getCuadrillas() {
        return cuadrillas;
    }

    public void setCuadrillas(List<Cuadrilla> cuadrillas) {
        this.cuadrillas = cuadrillas;
    }

    @Override
    public String toString() {
        return "---------------------------------------------\n" +
                "Olivar                " + "\n" +
                "ID:                   " + id + "\n" +
                "Ubicacion:            " + ubicacion + "\n" +
                "Hectáreas:            " + hectareas + "\n" +
                "Producción anual:     " + produccionAnual + "\n" +
                "Cuadrillas            " + cuadrillas + "\n";
    }


}
