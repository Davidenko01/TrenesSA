/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dominio;

import conjuntistas.Diccionario;
import grafos.GrafoEtiquetado;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import lineales.Lista;
import java.util.Scanner;

public class Main {

    private static final Diccionario estaciones = new Diccionario();
    private static final Diccionario trenes = new Diccionario();
    private static final GrafoEtiquetado conexiones = new GrafoEtiquetado();
    private static final HashMap<String, Lista> lineas = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in, "ISO_8859_1");
    private static final FileWriter salida;

    static {
        try {
            salida = new FileWriter("‪cargalog.txt");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) throws IOException {
        //MENU DE SELECCION DE OPCIONES DE TRENES SA
        int opcion;
        estaciones.toString();
        trenes.toString();
        cargaInicial();
        do {
            menu();
            System.out.println("Ingrese la opción a realizar: ");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    ABMtrenes();
                    break;
                case 2:
                    ABMestaciones();
                    break;
                case 3:
                    ABMlineas();
                    break;
                case 4:
                    ABMrieles();
                    break;

                case 5:
                    consultasTrenes();
                    break;

                case 6:
                    consultasEstaciones();
                    break;
                case 7:
                    consultasViajes();
                    break;
                case 8:
                    mostrarSistema();
                    break;

                case 0:
                    escribir("Fin del Programa TrenesSA");
                    break;

                default:
                    System.out.println("Ingrese una opcion valida");
                    break;
            }
        } while (opcion != 0);
        mostrarSistema();
        salida.close();
    }

    //MENUS VISUALES
    public static void menu() {
        System.out.println("-----------------------------------------------");
        System.out.println("1.ABM de trenes");
        System.out.println("2.ABM de estaciones");
        System.out.println("3.ABM de lineas");
        System.out.println("4.AVM de rieles");
        System.out.println("5.Consultar sobre trenes");
        System.out.println("6.Consultar sobre estaciones");
        System.out.println("7.Consultar sobre viajes.");
        System.out.println("8.Mostrar sistema");
        System.out.println("0.Salir");
        System.out.println("-----------------------------------------------");
    }

    public static void menuABMtrenes() {
        System.out.println("-----------------------------------------------");
        System.out.println("1.Eliminar tren");
        System.out.println("2.Agregar un tren");
        System.out.println("3.Editar un tren");
        System.out.println("4.Volver atras");
        System.out.println("-----------------------------------------------");
    }

    public static void menuABMestaciones() {
        System.out.println("-----------------------------------------------");
        System.out.println("1.Eliminar estacion");
        System.out.println("2.Agregar una estacion");
        System.out.println("3.Editar una estacion");
        System.out.println("4.Volver atras");
        System.out.println("-----------------------------------------------");
    }

    public static void menuABMlineas() {
        System.out.println("-----------------------------------------------");
        System.out.println("1.Eliminar una linea");
        System.out.println("2.Agregar una linea");
        System.out.println("3.Insertar una estacion existente a la linea");
        System.out.println("4.Eliminar una estacion existente de una linea");
        System.out.println("5.Volver atras");
        System.out.println("-----------------------------------------------");
    }

    public static void menuABMrieles() {
        System.out.println("-----------------------------------------------");
        System.out.println("1.Agregar riel que une dos estaciones");
        System.out.println("2.Eliminar riel que une dos estaciones");
        System.out.println("3.Volver atras");
        System.out.println("-----------------------------------------------");
    }

    public static void menuConsultasEstaciones() {
        System.out.println("-----------------------------------------------");
        System.out.println("1.Dado un nombre de estación, mostrar toda su información");
        System.out.println("2.Dada una cadena, devolver todas las estaciones cuyo nombre comienza con dicha subcadena");
        System.out.println("3.Volver atras");
        System.out.println("-----------------------------------------------");
    }

    public static void menuConsultasTrenes() {
        System.out.println("-----------------------------------------------");
        System.out.println("1.Dado un código de tren mostrar toda la información del mismo");
        System.out.println("2.Dado un código de tren, verificar si está destinado a alguna línea y mostrar las ciudades que visitaría");
        System.out.println("3.Volver atras");
        System.out.println("-----------------------------------------------");
    }

    public static void menuEditarTrenes() {
        System.out.println("-----------------------------------------------");
        System.out.println("1.Asignar tren a otra linea");
        System.out.println("2.Cambiar el tipo de propulsión");
        System.out.println("3.Cambiar cantidad de vagones de pasajeros");
        System.out.println("4.Cambiar cantidad de vagones de carga");
        System.out.println("5.Volver atras");
        System.out.println("-----------------------------------------------");
    }

    public static void menuEditarEstaciones() {
        System.out.println("-----------------------------------------------");
        System.out.println("1.Modificar direccion");
        System.out.println("2.Cambiar cantidad de vias");
        System.out.println("3.Cambiar cantidad de plataformas");
        System.out.println("4.Volver atras");
        System.out.println("-----------------------------------------------");
    }

    public static void menuConsultasViajes() {
        System.out.println("-----------------------------------------------");
        System.out.println("1.Obtener el camino que llegue de A a B que pase por menos estaciones");
        System.out.println("2.Obtener el camino que llegue de A a B de menor distancia en kilómetros");
        System.out.println("3.Volver atras");
        System.out.println("-----------------------------------------------");
    }

    //SE HACE LA CARGA INICIAL DEL SISTEMA
    public static void cargaInicial() throws IOException {
        try {
            FileReader fr = new FileReader("C:\\Users\\Usuario\\Desktop\\Escuela\\UNComa\\TPF EDAT\\TP FINAL TrenesSA EDAT Kissner Davor FAI-3185\\CargaInicial.txt");
            BufferedReader br = new BufferedReader(fr);
            String datos;
            String[] arrayData;
            boolean exito;
            while ((datos = br.readLine()) != null) { //LEO EL TXT Y CONVIERTO UNA LINEA EN UN ARREGLO CON SPLIT
                arrayData = datos.split(";");
                switch (arrayData[0]) {
                    //CARGA SI ES ESTACION
                    case "E":
                        //SOLO ENTRA SI ESTAN LOS 8 DATOS
                        if (arrayData.length == 8) {
                            exito = cargarEstacion(arrayData[1], arrayData[2], Integer.parseInt(arrayData[3]), arrayData[4], arrayData[5], Integer.parseInt(arrayData[6]), Integer.parseInt(arrayData[7]));
                            if (exito) {
                                escribir("Se cargo la estacion: " + arrayData[1]);
                            }
                        }
                        break;
                    case "T":
                        //CARGA SI ES TREN
                        if (arrayData.length == 6) { //SOLO ENTRA SI ESTAN LOS 6 DATOS
                            exito = cargarTren(Integer.parseInt(arrayData[1]), arrayData[2], Integer.parseInt(arrayData[3]), Integer.parseInt(arrayData[4]), arrayData[5]);
                            if (exito) {
                                escribir("Se cargo el tren: " + arrayData[1]);

                            }
                        }
                        break;
                    case "R":
                        //CARGA SI ES RIEL
                        if (arrayData.length == 4) { //SOLO ENTRA SI ESTAN LOS 4 DATOS
                            exito = cargarRiel(arrayData[1], arrayData[2], Integer.parseInt(arrayData[3]));
                            if (exito) {
                                escribir("Se cargo el riel que conecta: " + arrayData[1] + " con " + arrayData[2]);
                            }
                        }
                        break;
                    case "L":
                        //CARGA SI ES LINEA
                        String linea = arrayData[1];
                        if (agregarLinea(linea)) { //AGREGO LA LINEA, SI SE AGREGÓ, LE COLOCO LAS ESTACIONES
                            cargarEstacionesALineaInicial(linea, arrayData, arrayData.length);
                            escribir("Se cargo la linea: " + linea + lineas.get(linea));
                        }
                }
            }

        } catch (IOException ex) {
        }
    }

    //METODOS DE CARGA
    public static boolean cargarEstacion(String nombre, String calle, int numero, String ciudad, String codPostal, int vias, int plataformas) {
        //INSERTO LA ESTACION AL DICC
        boolean exito = false;
        if (estaciones.insertar(nombre, new Estacion(nombre, calle, numero, ciudad, codPostal, vias, plataformas))) {
            exito = conexiones.insertarVertice(nombre);
        }
        return exito;
    }

    public static boolean cargarTren(int identificador, String propulsion, int vagonesPasajeros, int vagonesCarga, String linea) {
        //INSERTO EL TREN AL DICC
        return trenes.insertar(identificador, new Tren(identificador, propulsion, vagonesPasajeros, vagonesCarga, linea));
    }

    public static boolean cargarRiel(String origen, String fin, int distancia) {
        //AGREGO EL RIEL PARA CONECTAR 2 ESTACIONES Y SU DISTANCIA
        return conexiones.insertarArco(origen, fin, distancia);
    }

    public static boolean agregarLinea(String nombre) {
        //AGREGO LA LINEA AL HASHMAP
        return lineas.putIfAbsent(nombre, new Lista()) == null;
    }

    public static void cargarEstacionesALineaInicial(String linea, String[] estaciones, int longitud) {
        //SI LA LINEA SE CARGÓ USO ESTE METODO PARA IR CARGANDO TODA LA LISTA DE ESTACIONES A LA LINEA
        Lista lista = (Lista) lineas.get(linea);
        if (lista != null) {
            for (int i = 2; i < longitud; i++) {
                if (lista.localizar(estaciones[i]) == -1) {
                    lista.insertar(estaciones[i], lista.longitud() + 1);
                }
            }
        }
    }

    public static boolean agregarEstacionALinea(String estacion, String linea) {
        //USO ESTE METODO PARA INSERTAR UNA ESTACION X A UNA LINEA ELEGIDA
        boolean exito = false;
        Lista lista = (Lista) lineas.get(linea);
        if (lista != null) {
            if (lista.localizar(estacion) == -1) {
                lista.insertar(estacion, lista.longitud() + 1);
                exito = true;
            }
        }
        return exito;
    }

    //METODOS DE ELIMINACIÓN
    public static boolean eliminarRiel(String origen, String fin) {
        //ELIMINO EL RIEL DEL GRAFO
        return conexiones.eliminarArco(origen, fin);
    }

    public static boolean eliminarTren(int identificador) {
        //ELIMINO TREN DEL DICC
        return trenes.eliminar(identificador);
    }

    public static boolean eliminarLinea(String linea) {
        //ELIMINO LA LINEA
        return lineas.remove(linea) != null;
    }

    public static boolean eliminarEstacion(String nombre) {
        boolean exito;
        exito = estaciones.eliminar(nombre);
        if (exito) {
            exito = conexiones.eliminarVertice(nombre);
            lineas.forEach((key, list) -> {
                int i = ((Lista) list).localizar(nombre);
                if (i != -1) {
                    ((Lista) list).eliminar(i);
                }
            });
        }
        return exito;
    }

    public static boolean eliminarEstacionDeLinea(String estacion, String linea) {
        //ELIMINO LAS ESTACIONES DE LA LINEA
        boolean exito = false;
        int i;
        Lista lista = (Lista) lineas.get(linea);
        if (lista != null) {
            i = lista.localizar(estacion);
            if (i != -1) {
                lista.eliminar(i);
                exito = true;
            }
        }
        return exito;
    }

    //METODOS DE OBTENCION (OBTENER) PARA LAS CONSULTAS
    public static Tren obtenerTren(int identificador) {
        //DEVUELVE UN TREN SEGUN SU CLAVE EN EL DICCIONARIO
        return (Tren) trenes.obtenerDato(identificador);
    }

    public static Estacion obtenerEstacion(String nombre) {
        //DEVUELVE UNA ESTACION SEGUN SU CLAVE EN EL DICC
        return (Estacion) estaciones.obtenerDato(nombre);
    }

    //METODOS DE EDICION
    //METODO EDICION TREN
    public static boolean modificarTren(int identificador) {
        //MODULO PARA MODIFICAR LOS ATRIBUTOS DE UN TREN 
        boolean exito = false;
        Tren trensito = (Tren) trenes.obtenerDato(identificador);
        if (trensito != null) {
            int opcion;
            do {
                menuEditarTrenes();
                System.out.println("Eliga la opción a realizar");
                opcion = sc.nextInt();
                sc.nextLine();
                switch (opcion) {
                    case 1:
                        modificarLineaTren(trensito);
                        exito = true;
                        break;

                    case 2:
                        modificarPropulsionTren(trensito);
                        exito = true;
                        break;

                    case 3:
                        modificarCantVagPasajeros(trensito);
                        exito = true;
                        break;

                    case 4:
                        modificarCantVagCarga(trensito);
                        exito = true;
                        break;

                    case 5:
                        break;

                    default:
                        System.out.println("OPCION NO VALIDA, INGRESE OPCION VALIDA");
                        break;
                }

            } while (opcion != 5);
        }
        return exito;
    }

    public static void modificarLineaTren(Tren trensito) {
        //LE ASIGNO UNA NUEVA LINEA A UN TREN O QUEDA SIN ASIGNAR
        String linea;
        System.out.println("Ingrese la nueva linea del tren o digite no-asignado: ");
        linea = sc.nextLine();
        if (linea.compareTo("no-asignado") == 0 || lineas.containsKey(linea)) {
            trensito.setLinea(linea);
        } else {
            trensito.setLinea("no-asignado");
        }
    }

    public static void modificarPropulsionTren(Tren trensito) {
        //CAMBIO EL TIPO DE PROPULSION AL TREN
        String propulsion;
        System.out.println("Ingrese el nuevo tipo de propulsion: ");
        propulsion = sc.nextLine();
        trensito.setPropulsion(propulsion);
    }

    public static void modificarCantVagPasajeros(Tren trensito) {
        //CAMBIO LA CANTIDAD DE VAGONES DE PASAJEROS
        int cantVagPasajeros;
        System.out.println("Ingrese la nueva cantidad de vagones de pasajeros: ");
        cantVagPasajeros = sc.nextInt();
        trensito.setVagonesPasajaeros(cantVagPasajeros);
    }

    public static void modificarCantVagCarga(Tren trensito) {
        //CAMBIO LA CANTIDAD DE VAGONES DE CARGA
        int cantVagCarga;
        System.out.println("Ingrese la nueva cantidad de vagones de carga: ");
        cantVagCarga = sc.nextInt();
        trensito.setVagonesCarga(cantVagCarga);
    }

    //METODO EDITAR ESTACION
    public static boolean modificarEstacion(String nombre) {
        //MODULO PARA MODIFICAR LOS ATRIBUTOS DE UNA ESTACION
        boolean exito = false;
        Estacion estacion = (Estacion) estaciones.obtenerDato(nombre);
        if (estacion != null) {
            int opcion;
            do {
                menuEditarEstaciones();
                System.out.println("Eliga la opcion a realizar");
                opcion = sc.nextInt();
                sc.nextLine();
                switch (opcion) {
                    case 1:
                        editarDireccion(estacion);
                        exito = true;
                        break;
                    case 2:
                        editarCantVias(estacion);
                        exito = true;
                        break;

                    case 3:
                        editarCantPlataformas(estacion);
                        exito = true;
                        break;

                    case 4:
                        break;

                    default:
                        System.out.println("OPCION NO VALIDA - INGRESE UNA OPCION VALIDA");
                        break;

                }
            } while (opcion != 4);
        }
        return exito;
    }

    public static void editarDireccion(Estacion estacion) {
        //SE CAMBIA TODOS LOS ATRIBUTOS QUE FORMARIAN LA DIRECCION DE LA ESTACION
        String calle, codPostal, ciudad;
        int numCalle;
        System.out.println("Ingrese la nueva direccion de la estacion: ");
        calle = sc.nextLine();
        estacion.setCalle(calle);
        System.out.println("Ingrese la altura de calle (numero de la casa): ");
        numCalle = sc.nextInt();
        sc.nextLine();
        estacion.setNumero(numCalle);
        System.out.println("Ingrese la ciudad donde se encuentra la estacion: ");
        ciudad = sc.nextLine();
        estacion.setCiudad(ciudad);
        System.out.println("Ingrese el codigo postal de la ciudad");
        codPostal = sc.nextLine();
        estacion.setCodPostal(codPostal);
    }

    public static void editarCantVias(Estacion estacion) {
        //CAMBIO LA CANTIDAD DE VIAS DE LA ESTACION
        int cantVias;
        System.out.println("Ingrese la cantidad de vias: ");
        cantVias = sc.nextInt();
        estacion.setCantVias(cantVias);
    }

    public static void editarCantPlataformas(Estacion estacion) {
        //CAMBIO LA CANTIDAD DE PLATAFORMAS DE LA ESTACION
        int cantPlataformas;
        System.out.println("Ingrese la cantidad de plataformas: ");
        cantPlataformas = sc.nextInt();
        estacion.setCantPlataformas(cantPlataformas);
    }

    //MODULOS DE ABM
    //ABM DE TRENES
    public static void ABMtrenes() throws IOException {
        int opcion;
        do {
            menuABMtrenes();
            System.out.println("Ingrese la opcion a realizar");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    //ELIMINAR TREN
                    eliminarTrenABM();
                    break;
                case 2:
                    //INSERTAR TREN
                    insertarTren();
                    break;
                case 3:
                    //MODIFICAR TREN
                    editarTrenABM();
                    break;

                case 4:
                    break;

                default:
                    System.out.println("OPCION NO VALIDA, INGRESE OPCION VALIDA");
                    break;
            }
        } while (opcion != 4);
    }

    public static void eliminarTrenABM() throws IOException {
        int identificador;
        System.out.println("Ingrese la ID del tren: ");
        identificador = sc.nextInt();
        //USO EL MODULO ELIMINAR TREN PREVIAMENTE CREADO
        if (eliminarTren(identificador)) {
            escribir("Tren con ID " + identificador + " eliminado con exito");
        } else {
            escribir("El tren con identificador " + identificador + " no existe");
        }
    }

    public static void insertarTren() throws IOException {
        //PIDO LOS DATOS DEL TREN AL USUARIO PARA CREAR UN NUEVO TREN
        int identificador;
        System.out.println("Ingrese la ID del tren: ");
        identificador = sc.nextInt();
        sc.nextLine();
        if (!trenes.existeClave(identificador)) {
            String propulsion, linea;
            int cantVagCarga, cantVagPasajeros;
            System.out.println("Ingrese tipo de propulsion: ");
            propulsion = sc.nextLine();
            System.out.println("Ingrese la cantidad de vagones para pasajeros: ");
            cantVagPasajeros = sc.nextInt();
            System.out.println("Ingrese la cantidad de vagones de carga: ");
            cantVagCarga = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese la linea asignada para el tren, si no tiene una linea asignada digite 'no-asignado': ");
            linea = sc.nextLine();
            if (linea.compareTo("no-asignado") == 0 || lineas.containsKey(linea)) {
                if (cargarTren(identificador, propulsion, cantVagPasajeros, cantVagCarga, linea)) {
                    escribir("Se ingresó el tren con ID: " + identificador);
                } else {
                    escribir("No se pudo ingresar el tren con ID: " + identificador);
                }
            } else {
                linea = "no-asignado";
                if (cargarTren(identificador, propulsion, cantVagPasajeros, cantVagCarga, linea)) {
                    escribir("Se ingresó el tren con ID: " + identificador + ", linea no existe, no se le asigna linea");
                } else {
                    escribir("No se pudo ingresar el tren con ID: " + identificador);
                }

            }
        } else {
            escribir("El tren con id " + identificador + " ya existe, no se ha podido realizar la carga");
        }
    }

    public static void editarTrenABM() throws IOException {
        int identificador;
        boolean exito;
        System.out.println("Ingrese la id del tren a modificar: ");
        identificador = sc.nextInt();
        //USO EL MODULO MODIFICAR TREN PREVIAMENTE CREADO
        if (trenes.existeClave(identificador)) {
            exito = modificarTren(identificador);
            if (exito) {

                escribir("Tren con id " + identificador + " ha sido modificado con exito");
            } else {
                escribir("No se pudo modificar el tren con ID " + identificador);
            }
        } else {
            escribir("El tren con ID " + identificador + " no existe, no se puede modificar");
        }
    }

    //ABM DE ESTACIONES
    public static void ABMestaciones() throws IOException {
        int opcion;
        do {
            menuABMestaciones();
            System.out.println("Ingrese la opcion a realizar");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    //ELIMINAR ESTACION
                    eliminarEstacionABM();
                    break;
                case 2:
                    //INSERTO UNA ESTACION
                    insertarEstacion();
                    break;
                case 3:
                    //MODIFICAR LA ESTACION, USO EL MODULO ELIMINAR ESTACION PREVIAMENTE CREADO
                    modificarEstacionABM();
                    break;
                case 4:
                    break;

                default:
                    System.out.println("OPCION NO VALIDA");
                    break;
            }
        } while (opcion != 4);
    }

    public static void eliminarEstacionABM() throws IOException {
        String estacion;
        System.out.println("Ingrese el nombre de la estacion: ");
        estacion = sc.nextLine();
        if (eliminarEstacion(estacion)) { //USO EL MODULO ELIMINARESTACION PREVIAMENTE CREADO
            escribir("Estacion " + estacion + " eliminada con exito");
        } else {
            escribir("La estacion " + estacion + " no existe");
        }
    }

    public static void insertarEstacion() throws IOException {
        //PIDO LOS DATOS AL USUARIO PARA CREAR UNA ESTACION NUEVA
        String estacion;
        System.out.println("Ingrese el nombre de la estacion: ");
        estacion = sc.nextLine();
        if (!estaciones.existeClave(estacion)) {
            String calle, ciudad, codPostal;
            int cantVias, cantPlataformas, numCalle;
            System.out.println("Ingrese la calle de la estación: ");
            calle = sc.nextLine();
            System.out.println("Ingrese el numero de la calle (altura): ");
            numCalle = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese la ciudad donde se encuentra la estacion: ");
            ciudad = sc.nextLine();
            System.out.println("Ingrese el codigo postal de la ciudad: ");
            codPostal = sc.nextLine();
            System.out.println("Ingrese la cantidad de vias de la estación: ");
            cantVias = sc.nextInt();
            System.out.println("Ingrese la cantidad de plataformas de la estacion: ");
            cantPlataformas = sc.nextInt();
            sc.nextLine();
            if (cargarEstacion(estacion, calle, numCalle, ciudad, codPostal, cantVias, cantPlataformas)) {
                escribir("Se ingresó la estación " + estacion);
            } else {
                escribir("No se pudo ingresar la estacion " + estacion);
            }
        } else {
            escribir("La estacion " + estacion + " ya existe, no se puede insertar");
        }
    }

    public static void modificarEstacionABM() throws IOException {
        String estacion;
        boolean exito;
        System.out.println("Ingrese el nombre de la estación a modificar: ");
        estacion = sc.nextLine();
        if (estaciones.existeClave(estacion)) {
            exito = modificarEstacion(estacion);
            if (exito) {
                escribir("La estacion " + estacion + " se modificó con exito");
            } else {
                escribir("No se pudo llevar a cabo la modificacion a la estacion " + estacion);
            }
        } else {
            escribir("La estacion " + estacion + " no existe, no se puede modificar");
        }
    }

    //ABM LINEAS
    public static void ABMlineas() throws IOException {
        int opcion;
        do {
            menuABMlineas();
            System.out.println("Ingrese la Opcion a realizar: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    //ELIMINO UNA LINEA EXISTENTE
                    eliminarLineaABM();
                    break;
                //AGREGO UNA NUEVA LINEA
                case 2:
                    agregarLineaABM();
                    break;

                case 3:
                    //AGREGO ESTACIONES A UNA LINEA ELEGIDA
                    agregarEstacionALineaABM();
                    break;

                case 4:
                    //ELIMINO UNA ESTACION QUE EXISTE EN UNA LINEA
                    eliminarEstacionDeLineaABM();
                    break;

                case 5:
                    break;

                default:
                    System.out.println("Opcion invalida, ingrese una opcion valida");
                    break;
            }
        } while (opcion != 5);
    }

    public static void eliminarLineaABM() throws IOException {
        String linea;
        System.out.println("Ingrese el nombre de la linea a eliminar: ");
        linea = sc.nextLine();
        if (eliminarLinea(linea)) { //USO EL MODULO ELIMINAR LINEA PREVIAMENTE CREADO
            escribir("Se eliminó la linea: " + linea);
        } else {
            escribir("No se pudo eliminar la linea " + linea + " ya que dicha linea no existe");
        }
    }

    public static void agregarLineaABM() throws IOException {
        String linea;
        System.out.println("Ingrese el nombre de la linea a agregar: ");
        linea = sc.nextLine();
        if (agregarLinea(linea)) { //USO EL METODO PREVIAMENTE CREADO AGREGARLINEA
            escribir("La linea " + linea + " se ha insertado exitosamente");
        } else {
            escribir("La linea " + linea + " ya existe, no se puede insertar");
        }
    }

    public static void agregarEstacionALineaABM() throws IOException {
        String estacion, linea;
        System.out.println("Ingrese el nombre de la estacion a agregar: ");
        estacion = sc.nextLine();
        if (estaciones.existeClave(estacion)) {
            System.out.println("Ingrese el nombre de la linea: ");
            linea = sc.nextLine();
            if (agregarEstacionALinea(estacion, linea)) { //USO ESTE METODO PREVIAMENTE CREADO PARA AGREGAR LA ESTACION
                escribir("La estación " + estacion + " se agregó a la linea " + linea);
            } else {
                escribir("No se pudo agregar la estacion " + estacion + " a la linea elegida");
            }

        } else {
            escribir("La estacion " + estacion + " no existe");
        }
    }

    public static void eliminarEstacionDeLineaABM() throws IOException {
        String estacion, linea;
        System.out.println("Ingres el nombre de la estación a eliminar: ");
        estacion = sc.nextLine();
        if (estaciones.existeClave(estacion)) {
            System.out.println("Ingrese el nombre de la linea: ");
            linea = sc.nextLine();
            if (eliminarEstacionDeLinea(estacion, linea)) { //MODULO ELIMINAR ESTACION DE UNA LINEA PREV. CREADO
                escribir("La estacion " + estacion + " ha sido eliminada de la linea " + linea);
            } else {
                escribir("No se pudo eliminar la estacion " + estacion + " a la linea elegida");
            }
        } else {
            escribir("La estación " + estacion + " no existe");
        }
    }

    public static void ABMrieles() throws IOException {
        int opcion;
        do {
            menuABMrieles();
            System.out.println("Ingrese la opcion a realizar: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    //CREO UN NUEVO RIEL QUE UNE 2 ESTACIONES
                    agregarRielABM();
                    break;

                case 2:
                    //ELIMINO UN RIEL YA EXISTENTE ENTRE 2 ESTACIONES
                    eliminarRielABM();
                    break;

                case 3:
                    break;

                default:
                    System.err.println("Ingrese una opcion valida");
                    break;
            }
        } while (opcion != 3);
    }

    public static void agregarRielABM() throws IOException {
        String estacion1, estacion2;
        int distancia;
        System.out.println("Ingrese la estacion de origen: ");
        estacion1 = sc.nextLine();
        System.out.println("Ingrese la estacion de fin: ");
        estacion2 = sc.nextLine();
        System.out.println("Ingrese la distancia en kilometros");
        distancia = sc.nextInt();
        if (cargarRiel(estacion1, estacion2, distancia)) { //USO METODO CARGAR RIEL PREVIAMENTE CREADO
            escribir("Se agrego el riel que une a " + estacion1 + " con " + estacion2);
        } else {
            escribir("No se pudo agregar el riel que conecta " + estacion1 + " con " + estacion2);
        }
    }

    public static void eliminarRielABM() throws IOException {
        String estacion1, estacion2;
        System.out.println("Ingrese la estacion de origen: ");
        estacion1 = sc.nextLine();
        System.out.println("Ingrese la estacion de fin: ");
        estacion2 = sc.nextLine();
        if (eliminarRiel(estacion1, estacion2)) { //MODULO ELIMINAR RIEL PREV. CREADO
            escribir("Se eliminó el riel que une las estaciones " + estacion1 + ", " + estacion2);
        } else {
            escribir("No se pudo eliminar el riel que conecta " + estacion1 + " con " + estacion2);
        }
    }

    public static void consultasTrenes() throws IOException {
        int opcion;
        do {
            menuConsultasTrenes();
            System.out.println("Ingrese una opcion: ");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    consultasTrenes1();
                    break;
                case 2:
                    consultasTrenes2();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        } while (opcion != 3);
    }

    public static void consultasTrenes1() throws IOException {
        //DEVUELVO UN STRING CON TODOS LOS DATOS DE UN TREN SELECCIONADO POR EL USUARIO
        Tren tren;
        int identificador;
        System.out.println("Ingrese el codigo de tren que desea buscar informacion: ");
        identificador = sc.nextInt();
        tren = obtenerTren(identificador);
        if (tren != null) {
            escribir(tren.toStringCompleto());
        } else {
            escribir("El tren " + identificador + " no existe");
        }
    }

    public static void consultasTrenes2() throws IOException {
        //DEVUELVO UNA LISTA DE LAS ESTACIONES POR LAS QUE PASARIA UN TREN
        int identificador;
        Tren tren;
        System.out.println("Ingrese el codigo del Tren: ");
        identificador = sc.nextInt();
        tren = obtenerTren(identificador);
        if (tren != null) {
            if (tren.getLinea().compareTo("no-asignado") != 0) {
                Lista lisEstaciones = (Lista) lineas.get(tren.getLinea());
                if (!lisEstaciones.esVacia()) {
                    escribir(lisEstaciones.toString());
                }
            } else {
                escribir("Tren " + identificador + " no esta asignado a ninguna linea");
            }
        } else {
            escribir("No existe un tren con ID: " + identificador);
        }
    }

    public static void consultasEstaciones() throws IOException {
        int opcion;
        do {
            menuConsultasEstaciones();
            System.out.println("Ingrese la opcion a realizar: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    consultasEstaciones1();
                    break;
                case 2:
                    consultasEstaciones2();
                    break;
                case 3:
                    break;

                default:
                    System.out.println("Opcion no valida, ingrese opcion valida");
                    break;
            }

        } while (opcion != 3);

    }

    public static void consultasEstaciones1() throws IOException {
        //DADO EL NOMBRE DE UNA ESTACION DEVUELVO TODA SU INFORMACION
        String nombre;
        Estacion estacion;
        System.out.println("Ingrese el nombre de la estación a buscar: ");
        nombre = sc.nextLine();
        estacion = obtenerEstacion(nombre);
        if (estacion != null) {
            System.out.println(estacion.toStringCompleto());
        } else {
            escribir("La estacion " + nombre + " no existe, no se puede buscar informacion");
        }
    }

    public static void consultasEstaciones2() throws IOException {
        //DADA UNA SUBCADENA INGRESADA X EL USAURIO DEVUELVO TODAS LAS ESTACIONES QUE EMPIEZAN POR DICHA SUBCADENA
        System.out.println("Inserte la subcadena a comparar");
        String cad = sc.nextLine();
        String ret = "[ ";
        Lista lista = estaciones.listarClaves();
        int longitud = lista.longitud();
        int i = 1;
        while (i <= longitud) {
            if (((String) lista.recuperar(i)).startsWith(cad)) {
                ret += "|" + ((String) lista.recuperar(i)) + "| ";
            }
            i++;
        }
        ret += "]";
        escribir("Estaciones que contienen la subcadena: " + cad);
        escribir(ret);
    }

    public static void consultasViajes() throws IOException {
        int opcion;
        do {
            menuConsultasViajes();
            System.out.println("Ingrese la opcion a realizar: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    consultasViajes1();
                    break;
                case 2:
                    consultasViajes2();
                    break;
                case 3:
                    break;

                default:
                    System.out.println("Opcion no valida, ingrese opcion valida");
                    break;
            }

        } while (opcion != 3);
    }

    public static void consultasViajes1() throws IOException {
        //DADO UNA ESTACION A Y UNA B DEVUELVO EL CAMINO CON MENOS ESTACIONES DE POR MEDIO
        String origen, fin;
        Object estacion1, estacion2;
        System.out.println("Ingrese la estacion de origen: ");
        origen = sc.nextLine();
        System.out.println("Ingrese la estacion destino: ");
        fin = sc.nextLine();
        estacion1 = estaciones.obtenerDato(origen);
        estacion2 = estaciones.obtenerDato(fin);
        if (estacion1 != null && estacion2 != null) {
            Lista l1 = conexiones.caminoMasCorto(origen, fin);
            escribir("El camino que pasa por menos estaciones desde " + origen + " hasta " + fin + " es: ");
            escribir(l1.toString());
        } else {
            escribir("Usted ha ingresado una estacion que no existe, ingrese solo estaciones existentes");
        }
    }

    public static void consultasViajes2() throws IOException {
        //DEVUELVO EL CAMINO CON MENOR DISTANCIA
        String origen, fin;
        Object estacion1, estacion2;
        System.out.println("Ingrese la estacion de origen: ");
        origen = sc.nextLine();
        System.out.println("Ingrese la estacion destino: ");
        fin = sc.nextLine();
        estacion1 = estaciones.obtenerDato(origen);
        estacion2 = estaciones.obtenerDato(fin);
        if (estacion1 != null && estacion2 != null) {
            String cadenaRapida = conexiones.listaCaminoMasRapido(origen, fin).toString();
            escribir("El camino mas corto desde " + origen + " hasta " + fin + " es de: " + cadenaRapida);

        } else {
            escribir("Usted ha ingresado una estacion que no existe, ingrese solo estaciones existentes");
        }
    }

    public static void escribir(String cadena) throws IOException {
        System.out.println(cadena);
        salida.write(cadena + "\n");
    }

    public static void mostrarSistema() throws IOException {
        //MOSTRAR SISTEMA DICC, HASH Y GRAFO
        System.out.println("Estaciones: ");
        escribir(estaciones.toString());
        System.out.println("-----------------------------------------------");
        System.out.println("Trenes: ");
        escribir(trenes.toString());
        System.out.println("-----------------------------------------------");
        System.out.println("Rieles: ");
        escribir(conexiones.toString());
        System.out.println("-----------------------------------------------");
        System.out.println("Lineas: ");
        escribir(lineas.toString());
    }

}
