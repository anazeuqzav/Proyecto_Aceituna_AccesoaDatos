package org.practicadao.marshalling;

import org.practicadao.entidades.*;
import org.practicadao.serviciosimpl.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class MarshallingXML {

    public static void saveXML(Aceituna aceituna) {
        String ficheroXML = "aceituna.xml";

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Aceituna.class);

            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(aceituna, new File(ficheroXML));

            System.out.println("Objeto aceituna serializado a XML en: " + ficheroXML);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void loadXml(String filename){
        try {
            // Crear el contexto JAXB para la clase Aceituna
            JAXBContext jaxbContext = JAXBContext.newInstance(Aceituna.class);

            // Crear un Unmarshaller
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Deserializar el XML a un objeto Aceituna
            Aceituna aceituna = (Aceituna) unmarshaller.unmarshal(new File(filename));

            // Obtener la lista de Almazaras
            List<Almazara> almazaras = aceituna.getAlmazaras();
            AlmazaraDaoImpl almDao = new AlmazaraDaoImpl();
            printline();
            for (Almazara a : almazaras) {
                System.out.println("Guardando almazaras: " + a.toString());
                almDao.save(a);
            }
            printline();

            // Obtener la lista de Cuadrillas
            List<Cuadrilla> cuadrillas = aceituna.getCuadrillas();
            CuadrillaDaoImpl cuaDao = new CuadrillaDaoImpl();
            printline();
            for (Cuadrilla c : cuadrillas) {
                System.out.println("Guardando cuadrillas: " + c.toString());
                cuaDao.save(c);
            }
            printline();

            // Obtener la lista de Trabajadores
            List<Trabajador> trabajadores = aceituna.getTrabajadores();
            TrabajadorDaoImpl traDao = new TrabajadorDaoImpl();
            printline();
            for (Trabajador t : trabajadores) {
                System.out.println("Guardando trabajadores: " + t.toString());
                traDao.save(t);
            }
            printline();

            // Obtener la lista de olivares
            List<Olivar> olivares = aceituna.getOlivares();
            OlivarDaoImpl oliDao = new OlivarDaoImpl();
            printline();
            for (Olivar o : olivares) {
                System.out.println("Guardando trabajadores: " + o.toString());
                oliDao.save(o);
            }
            printline();

            // Obtener la lista de producciones
            List<Produccion> producciones = aceituna.getProduciones();
            ProduccionDaoImpl proDao = new ProduccionDaoImpl();
            printline();
            for (Produccion p : producciones) {
                System.out.println("Guardando producciones: " + p.toString());
                proDao.save(p);
            }
            printline();

        } catch (JAXBException e) {
            System.out.println("Error al deserializar el archivo XML: " + e.getMessage());
        }
    }

    private static void printline(){
        System.out.println( "\n ····································\n");
    }

}


