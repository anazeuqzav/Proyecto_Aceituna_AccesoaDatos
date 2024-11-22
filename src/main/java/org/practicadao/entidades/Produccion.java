package org.practicadao.entidades;

import org.practicadao.marshalling.LocalDateAdapterXML;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Produccion {
    // Atributos
    @XmlAttribute(name = "id")
    private int id;

    @XmlElement(name = "cuadrillas_id")
    private int cuadrilla_id;

    @XmlElement(name = "olivar_id")
    private int olivar_id;

    @XmlElement(name = "almazara_id")
    private int almazara_id;

    @XmlElement(name = "fecha")
    @XmlJavaTypeAdapter(LocalDateAdapterXML.class)
    private LocalDate fecha;

    @XmlElement(name = "cantidad_recolectada")
    private double cantidadRecolectada;

    // Constructores
    public Produccion() {
    }

    // Constructor sin id
    public Produccion(int cuadrilla_id, int olivar_id, int almazara_id, LocalDate fecha, double cantidadRecolectada) {
        this.cuadrilla_id = cuadrilla_id;
        this.olivar_id = olivar_id;
        this.almazara_id = almazara_id;
        this.fecha = fecha;
        this.cantidadRecolectada = cantidadRecolectada;
    }

    // Constructor completo
    public Produccion(int id, int cuadrilla_id, int olivar_id, int almazara_id, LocalDate fecha, double cantidadRecolectada) {
        this.id = id;
        this.cuadrilla_id = cuadrilla_id;
        this.olivar_id = olivar_id;
        this.almazara_id = almazara_id;
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

    @Override
    public String toString() {
      return "---------------------------------------------\n" +
              "Produccion " + "\n" +
              "ID:                   " + id + "\n" +
              "ID Cuadrilla:         " + cuadrilla_id + "\n" +
              "ID Olivar:            " + olivar_id + "\n" +
              "ID Almazara:          " + almazara_id + "\n" +
              "Fecha:                " + fecha + "\n" +
              "Cantidad recolectada: " + cantidadRecolectada + "\n";
    }

}
