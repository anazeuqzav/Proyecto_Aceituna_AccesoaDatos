package org.practicadao;

import org.practicadao.entidades.*;
import org.practicadao.serviciosimpl.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int input;
        String opcion;
        boolean validacion = false; // variable para validar los datos

        // Inicializar DAOs
        TrabajadorDaoImpl trabajadorDao = new TrabajadorDaoImpl();
        CuadrillaDaoImpl cuadrillaDao = new CuadrillaDaoImpl();
        OlivarDaoImpl olivarDao = new OlivarDaoImpl();
        AlmazaraDaoImpl almazaraDao = new AlmazaraDaoImpl();
        ProduccionDaoImpl produccionDao = new ProduccionDaoImpl();

        //cargarDatos(); // Comentar una vez cargados los datos para evitar duplicados si se va a volver a ejecutar la aplicación

        do {
            System.out.println("""
╔══════════════════════════════════════════════════════════╗
║         BIENVENIDO A LA CAMPAÑA DE ACEITUNA 2024         ║
╠══════════════════════════════════════════════════════════╣
║ Seleccione una opción de las siguientes:                 ║
║                                                          ║
║   1. Mostrar los trabajadores de una determinada         ║
║      cuadrilla.                                          ║
║   2. Mostrar las cuadrillas que supervisa un             ║
║      determinado trabajador.                             ║
║   3. Mostrar los olivares donde trabaja una              ║
║      determinada cuadrilla.                              ║
║   4. Mostrar las cuadrillas que trabajan en un           ║
║      determinado olivar.                                 ║
║   5. Mostrar las almazaras donde lleva aceituna          ║
║      una determinada cuadrilla.                          ║
║   6. Mostrar la producción en una fecha concreta, de     ║
║      una cuadrilla concreta en una almazara concreta.    ║
║   7. Mostrar la cantidad total recolectada hasta una     ║
║      determinada fecha, de una determinada almazara.     ║
║   8. Salir.                                              ║
╚══════════════════════════════════════════════════════════╝
""");
            opcion = sc.nextLine();

            switch (opcion) {
                // Muestra los trabajadores de una determinada cuadrilla
                case "1":
                    do {
                        System.out.println("Introduce el ID de la cuadrilla: ");
                        try {
                            input = sc.nextInt();
                            List<Trabajador> trabajadores = trabajadorDao.findByCuadrilla(input);

                            if (!trabajadores.isEmpty()) {
                                System.out.println(trabajadores);
                                validacion = true;

                            } else {
                                System.out.println("No se encontró la cuadrilla");
                            }
                        } catch (InputMismatchException e) {
                            System.err.println("No has introducido un número válido.");
                        }
                        sc.nextLine();
                    } while (!validacion);
                    break;

                // Muestra las cuadrillas que supervisa un supervisor
                case "2":
                    validacion = false;
                    do {
                        try {
                            System.out.println("Introduce el ID del trabajador: ");
                            input = sc.nextInt();
                            List<Cuadrilla> cuadrillas = cuadrillaDao.findBySupervisor(input);

                            if (!cuadrillas.isEmpty()) {
                                System.out.println(cuadrillas);
                                validacion = true;
                            } else {
                                System.out.println("El trabajador no es un Supervisor o no existe.");
                            }

                        } catch (InputMismatchException e) {
                            System.err.println("No has introducido un identificador válido.");
                        }
                        sc.nextLine();
                    } while (!validacion);
                    break;

                // Mostrar los olivares donde trabaja una determinada cuadrilla.
                case "3":
                    validacion = false;
                    do {
                        try {
                            System.out.println("Introduce el ID de la cuadrilla: ");
                            input = sc.nextInt();
                            List<Olivar> olivares = olivarDao.findByCuadrilla(input);

                            if (!olivares.isEmpty()) {
                                System.out.println(olivares);
                                validacion = true;
                            } else {
                                System.out.println("La cuadrilla introducida no existe");
                            }

                        } catch (InputMismatchException e) {
                            System.err.println("No has introducido un identificador válido.");
                        }
                        sc.nextLine();

                    } while (!validacion);
                    break;

                // Mostrar las cuadrillas que trabajan en un determinado olivar.
                case "4":
                    validacion = false;
                    do {
                        try {
                            System.out.println("Introduce el ID del olivar: ");
                            input = sc.nextInt();

                            List<Cuadrilla> cuadrillas = cuadrillaDao.findByOlivar(input);

                            if (!cuadrillas.isEmpty()) {
                                System.out.println(cuadrillas);
                                validacion = true;
                            } else {
                                System.out.println("El olivar no existe o no tiene cuadrillas");
                            }

                        } catch (InputMismatchException e) {
                            System.err.println("No has introducido un identificador válido.");
                        }
                        sc.nextLine();

                    } while (!validacion);
                    break;

                // Mostrar las almazaras donde lleva aceituna una determinada cuadrilla.
                case "5":
                    validacion = false;
                    do{
                        try {
                            System.out.println("Introduce el ID de la cuadrilla: ");
                            input = sc.nextInt();
                            List<Almazara> almazaras = almazaraDao.findByCuadrilla(input);

                            if (!almazaras.isEmpty()) {
                                System.out.println(almazaras);
                                validacion = true;
                            } else {
                                System.out.println("No existe la cuadrilla introducida");
                            }

                        } catch (InputMismatchException e) {
                            System.err.println("No has introducido un identificador válido.");
                        }
                        sc.nextLine();

                    } while (!validacion);
                    break;

                // Mostrar la producción en una fecha concreta, de una cuadrilla concreta en una almazara concreta.
                case "6":
                    validacion = false;
                    do {
                        try {
                            // Validar ID de la cuadrilla
                            boolean cuadrillaValida = false;
                            int idCuadrilla = -1;
                            while (!cuadrillaValida) {
                                try {
                                    System.out.println("Introduce el ID de la cuadrilla: ");
                                    idCuadrilla = sc.nextInt();
                                    sc.nextLine(); // Limpiar buffer
                                    if (cuadrillaDao.findById(idCuadrilla) != null) {
                                        cuadrillaValida = true;
                                    } else {
                                        System.out.println("La cuadrilla no existe. Inténtalo de nuevo.");
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("No has introducido un identificador válido. Inténtalo de nuevo.");
                                    sc.nextLine(); // Limpiar buffer
                                }
                            }

                            // Validar ID de la almazara
                            boolean almazaraValida = false;
                            int idAlmazara = -1;
                            while (!almazaraValida) {
                                try {
                                    System.out.println("Introduce el ID de la almazara: ");
                                    idAlmazara = sc.nextInt();
                                    sc.nextLine(); // Limpiar buffer
                                    if (almazaraDao.findById(idAlmazara) != null) {
                                        almazaraValida = true;
                                    } else {
                                        System.out.println("La almazara no existe. Inténtalo de nuevo.");
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("No has introducido un identificador válido. Inténtalo de nuevo.");
                                    sc.nextLine(); // Limpiar buffer
                                }
                            }

                            // Validar fecha
                            boolean fechaValida = false;
                            LocalDate fecha = null;
                            while (!fechaValida) {
                                try {
                                    System.out.println("Introduce la fecha: (aaaa-mm-dd): ");
                                    fecha = LocalDate.parse(sc.next());
                                    sc.nextLine(); // Limpiar buffer
                                    fechaValida = true;
                                } catch (DateTimeParseException e) {
                                    System.err.println("Formato de fecha no válido. Usa el formato aaaa-mm-dd.");
                                }
                            }

                            // Buscar la producción
                            Produccion produccion = produccionDao.findByCuadrillaAlmazaraYFecha(idCuadrilla, idAlmazara, fecha);

                            if (produccion != null) {
                                System.out.println(produccion);
                                validacion = true; // Salir del bucle
                            } else {
                                System.out.println("No se encontró producción para los datos especificados.");
                                validacion = true; // Sale del bucle si no hay ninguna produccion

                            }

                        } catch (Exception e) {
                            System.err.println("Ocurrió un error: " + e.getMessage());
                        }
                    } while (!validacion);
                    break;

                //Mostrar la producción hasta una determinada fecha, de una determinada almazara.
                case "7":
                    validacion = false;
                    do {
                        try {
                            // Validar ID de la almazara
                            boolean almazaraValida = false;
                            int idAlmazara = -1;
                            while (!almazaraValida) {
                                try {
                                    System.out.println("Introduce el ID de la almazara: ");
                                    idAlmazara = sc.nextInt();
                                    sc.nextLine(); // Limpiar buffer
                                    if (almazaraDao.findById(idAlmazara) != null) {
                                        almazaraValida = true;
                                    } else {
                                        System.out.println("La almazara introducida no existe. Inténtalo de nuevo.");

                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("No has introducido un identificador válido. Inténtalo de nuevo.");
                                    sc.nextLine(); // Limpiar buffer
                                }
                            }

                            // Validar fecha
                            boolean fechaValida = false;
                            LocalDate fecha = null;
                            while (!fechaValida) {
                                try {
                                    System.out.println("Introduce la fecha hasta la que desea obtener la producción (aaaa-mm-dd): ");
                                    fecha = LocalDate.parse(sc.next());
                                    sc.nextLine(); // Limpiar buffer
                                    fechaValida = true;
                                } catch (DateTimeParseException e) {
                                    System.err.println("Formato de fecha no válido. Usa el formato aaaa-mm-dd.");
                                }
                            }

                            // Obtener la producción total
                            double produccionTotal = produccionDao.findTotalProduccionByAlmazaraFecha(idAlmazara, fecha);

                            if (produccionTotal != 0) {
                                System.out.println("La producción total de la almazara " + idAlmazara + " hasta la fecha " + fecha + " es: " + produccionTotal);
                                validacion = true; // Salir del bucle principal
                            } else {
                                System.out.println("No existe producción para la almazara y fecha introducida.");
                                validacion = true; // Sale del bucle si no hay ninguna produccion
                            }

                        } catch (Exception e) {
                            System.err.println("Ocurrió un error: " + e.getMessage());
                        }
                    } while (!validacion);
                    break;
                default:
                    System.out.println("Introduce una opción válida");
                    break;
            }
        } while (!opcion.equals("8"));
    }

    /**
     * Método que crea datos ficticios para la base de datos
     */
    private static void cargarDatos() {
        // Inicializar DAOs
        TrabajadorDaoImpl trabajadorDao = new TrabajadorDaoImpl();
        CuadrillaDaoImpl cuadrillaDao = new CuadrillaDaoImpl();
        OlivarDaoImpl olivarDao = new OlivarDaoImpl();
        AlmazaraDaoImpl almazaraDao = new AlmazaraDaoImpl();
        ProduccionDaoImpl produccionDao = new ProduccionDaoImpl();

        // Listas de nombres y apellidos para trabajadores
        String[] nombres = {"Juan", "María", "José", "Ana", "Luis", "Carmen", "Antonio", "Marta", "Pedro", "Lucía", "Carlos", "Elena", "David", "Laura", "Francisco", "Isabel", "Manuel", "Paula", "Javier", "Raquel", "Fernando", "Sofía", "Miguel", "Pilar", "Diego"};
        String[] apellidos = {"Pérez", "García", "Sánchez", "Martínez", "López", "Gómez", "Hernández", "Ruiz", "Fernández", "Jiménez", "Rodríguez", "Álvarez", "Moreno", "Muñoz", "Romero", "Vázquez", "Díaz", "Jiménez", "González", "Torres", "Castro", "Molina", "Cordero", "Navarro", "Serrano"};

        // Lista de ciudades en la provincia de Jaén
        String[] ciudadesJaen = {"Jaén", "Úbeda", "Baeza", "Linares", "Andújar", "Martos", "Alcalá la Real", "Villacarrillo", "Jódar", "Bailén"};

        // Crear y guardar 25 trabajadores
        List<Trabajador> trabajadores = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            String nombreCompleto = nombres[i % nombres.length] + " " + apellidos[i % apellidos.length];
            int edad = 20 + (i % 30); // Edad entre 20 y 50
            String puesto = (i % 5 == 0) ? "Supervisor" : "Recolector"; // 1 de cada 5 trabajadores es supervisor
            double salario = puesto.equals("Supervisor") ? 2000.0 + i * 10 : 1500.0 + i * 5; // los supervisores tienen un sueldo mayor
            Trabajador trabajador = new Trabajador(nombreCompleto, edad, puesto, salario);
            trabajadorDao.save(trabajador);
            trabajadores.add(trabajador);
        }
        // Crear una lista de solo supervisores
        List<Trabajador> supervisores = new ArrayList<>();
        for (Trabajador t : trabajadores) {
            if (t.getPuesto().equals("Supervisor")) {
                supervisores.add(t);
            }
        }

        // Crear y guardar 5 cuadrillas, asignando un supervisor
        List<Cuadrilla> cuadrillas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Cuadrilla cuadrilla = new Cuadrilla("Cuadrilla " + i, supervisores.get(i).getId());
            cuadrillaDao.save(cuadrilla);
            cuadrillas.add(cuadrilla);
        }

        // Asociar trabajadores a cuadrillas usando `asociarCuadrillaConTrabajador`
        for (Cuadrilla cuadrilla : cuadrillas) {
            int numTrabajadores = 5 + (int) (Math.random() * 5); // Cada cuadrilla tiene entre 5 y 10 trabajadores
            for (int j = 0; j < numTrabajadores; j++) {
                Trabajador trabajador = trabajadores.get((int) (Math.random() * trabajadores.size()));
                cuadrillaDao.asociarCuadrillaConTrabajador(cuadrilla.getId(), trabajador.getId());
            }
        }

        // Crear y guardar 10 olivares con ciudades de Jaén
        List<Olivar> olivares = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String ubicacion = ciudadesJaen[i % ciudadesJaen.length];
            double hectareas = 5 + i * 1.5; // Tamaño entre 5 y 20 hectáreas
            double produccionAnual = 4000 + i * 500; // Producción anual entre 4000 y 8500
            Olivar olivar = new Olivar(ubicacion, hectareas, produccionAnual);
            olivarDao.save(olivar);
            olivares.add(olivar);
        }

        // Asociar olivares a cuadrillas usando `asociarCuadrillaConOlivar`
        for (Cuadrilla cuadrilla : cuadrillas) {
            int numOlivares = 2 + (int) (Math.random() * 3); // Cada cuadrilla gestiona entre 2 y 4 olivares
            for (int j = 0; j < numOlivares; j++) {
                Olivar olivar = olivares.get((int) (Math.random() * olivares.size()));
                cuadrillaDao.asociarCuadrillaConOlivar(cuadrilla.getId(), olivar.getId());
            }
        }

        // Crear y guardar 3 almazaras en diferentes ciudades de Jaén
        List<Almazara> almazaras = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String nombre = "Almazara " + i;
            String ubicacion = ciudadesJaen[i % ciudadesJaen.length];
            double capacidad = 10000 + i * 5000; // Capacidad entre 10000 y 20000
            Almazara almazara = new Almazara(nombre, ubicacion, capacidad);
            almazaraDao.save(almazara);
            almazaras.add(almazara);
        }

        // Crear y guardar producciones aleatorias
        for (int i = 0; i < 20; i++) { // Crear 20 registros de producción
            Cuadrilla cuadrilla = cuadrillas.get((int) (Math.random() * cuadrillas.size()));
            Olivar olivar = olivares.get((int) (Math.random() * olivares.size()));
            Almazara almazara = almazaras.get((int) (Math.random() * almazaras.size()));
            LocalDate fecha = LocalDate.of(2024, 10, 1 + (int) (Math.random() * 28)); // Fecha aleatoria en octubre
            double cantidadRecolectada = 800 + Math.random() * 1200; // Cantidad entre 800 y 2000
            Produccion produccion = new Produccion(cuadrilla.getId(), olivar.getId(), almazara.getId(), fecha, cantidadRecolectada);
            produccionDao.save(produccion);
        }
        System.out.println("Datos de prueba generados exitosamente.");
    }

}


