package org.practicadao.entidades;

import org.practicadao.serviciosimpl.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "aceituna")
@XmlAccessorType(XmlAccessType.FIELD)
public class Aceituna {

    // Clases envoltorios
    @XmlElementWrapper(name = "almazaras")
    @XmlElement(name = "almazara")
    List<Almazara> almazaras;

    @XmlElementWrapper(name = "cuadrillas")
    @XmlElement(name = "cuadrilla")
    List<Cuadrilla> cuadrillas;

    @XmlElementWrapper(name = "olivares")
    @XmlElement(name = "olivar")
    List<Olivar> olivares;

    @XmlElementWrapper(name = "trabajadores")
    @XmlElement(name = "trabajador")
    List<Trabajador> trabajadores;

    @XmlElementWrapper(name = "producciones")
    @XmlElement(name = "produccion")
    List<Produccion> produciones;

    // Constructor
    public Aceituna() {

    }

    public Aceituna(List<Almazara> almazaras, List<Cuadrilla> cuadrillas, List<Olivar> olivares, List<Trabajador> trabajadores, List<Produccion> produciones) {
        this.almazaras = almazaras;
        this.cuadrillas = cuadrillas;
        this.olivares = olivares;
        this.trabajadores = trabajadores;
        this.produciones = produciones;
    }

    // Getters y setters
    public List<Almazara> getAlmazaras() {
        return almazaras;
    }

    public void setAlmazaras(List<Almazara> almazaras) {
        this.almazaras = almazaras;
    }

    public List<Cuadrilla> getCuadrillas() {
        return cuadrillas;
    }

    public void setCuadrillas(List<Cuadrilla> cuadrillas) {
        this.cuadrillas = cuadrillas;
    }

    public List<Olivar> getOlivares() {
        return olivares;
    }

    public void setOlivares(List<Olivar> olivares) {
        this.olivares = olivares;
    }

    public List<Trabajador> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(List<Trabajador> trabajadores) {
        this.trabajadores = trabajadores;
    }

    public List<Produccion> getProduciones() {
        return produciones;
    }

    public void setProduciones(List<Produccion> produciones) {
        this.produciones = produciones;
    }
}
