package org.practicadao.marshalling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.practicadao.entidades.*;
import org.practicadao.servicios.DaoException;
import org.practicadao.serviciosimpl.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MarshallingJSON {


    public static void saveJSON(Aceituna aceituna){
        String ficheroJSON = "aceituna.json";

        try (PrintWriter printWriter = new PrintWriter(new File(ficheroJSON)) ){
            Gson gson = new GsonBuilder().
                    registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter()).
                    registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).
                    registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).
                    setPrettyPrinting().
                    create();
            printWriter.write(gson.toJson(aceituna));
            System.out.println("Objeto aceituna serializado JSON en aceituna.json");
        } catch (FileNotFoundException fnfe) {
            System.out.println("Archivo no encontrado: "+ ficheroJSON);
        }

    }

    public static void loadGson(String filename) {
        // configuramos Gson
        Gson gson = new GsonBuilder().
                registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter()).
                registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).
                setPrettyPrinting().
                create();
        // leemos

        try (Reader reader = new FileReader(filename)) {
            Aceituna aceituna = gson.fromJson(reader, Aceituna.class);

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

        } catch (FileNotFoundException fne) {
            System.out.println("Archivo no encontrado.");
        } catch (IOException ioe){
            System.out.println("Error de entrada salida.");
        } catch (DaoException de) {
            System.out.println("Error al ejecutar las consultas: "+de.getMessage());
        }
    }

    private static void printline(){
        System.out.println( "\n ····································\n");
    }



    /**
     * Método que deserializa el objeto Casetas contenidos en un fichero JSON
     * @param ficheroJSON nombre o ruta del fichero JSON que contiene el objeto Casetas
     */
    /*
    public static void getFromJSON(File ficheroJSON) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Aceituna usuarioFromXml = objectMapper.readValue(ficheroJSON, Aceituna.class);

            System.out.println("Objeto Aceituna después de deserializar:");
            System.out.println(usuarioFromXml.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
