package org.practicadao.entidades;

import java.time.LocalDate;

public class Produccion {
    // Atributos
    private int id;
    private int olivar_id;
    private int almazara_id;
    private int cuadrilla_id;
    private LocalDate fecha;
    private double cantidadRecolectada;

    // Constructores
    public Produccion() {
    }

    public Produccion(int id, int olivar_id, int almazara_id, int cuadrilla_id, LocalDate fecha, double cantidadRecolectada) {
        this.id = id;
        this.olivar_id = olivar_id;
        this.almazara_id = almazara_id;
        this.cuadrilla_id = cuadrilla_id;
        this.fecha = fecha;
        this.cantidadRecolectada = cantidadRecolectada;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOlivar_id() {
        return olivar_id;
    }

    public void setOlivar_id(int olivar_id) {
        this.olivar_id = olivar_id;
    }

    public int getAlmazara_id() {
        return almazara_id;
    }

    public void setAlmazara_id(int almazara_id) {
        this.almazara_id = almazara_id;
    }

    public int getCuadrilla_id() {
        return cuadrilla_id;
    }

    public void setCuadrilla_id(int cuadrilla_id) {
        this.cuadrilla_id = cuadrilla_id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getCantidadRecolectada() {
        return cantidadRecolectada;
    }

    public void setCantidadRecolectada(double cantidadRecolectada) {
        this.cantidadRecolectada = cantidadRecolectada;
    }
}
