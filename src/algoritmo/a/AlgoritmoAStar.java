/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmo.a;

/**
 *
 * @author edson
 */
import java.util.*;

public class AlgoritmoAStar {

    public static class Pais {
        String nombre;
        double latitud;
        double longitud;
        List<Conexion> conexiones;

        public Pais(String nombre, double latitud, double longitud) {
            this.nombre = nombre;
            this.latitud = latitud;
            this.longitud = longitud;
            this.conexiones = new ArrayList<>();
        }

        public void agregarConexion(Pais destino, double distancia) {
            this.conexiones.add(new Conexion(destino, distancia));
            destino.conexiones.add(new Conexion(this, distancia));
        }
    }

    public static class Conexion {
        Pais destino;
        double distancia;

        public Conexion(Pais destino, double distancia) {
            this.destino = destino;
            this.distancia = distancia;
        }
    }

    public static class Nodo {
        Pais pais;
        double g; // Costo acumulado desde el nodo inicial
        double h; // Heurística (distancia en línea recta al nodo objetivo)
        double f; // F = g + h
        Nodo nodoAnterior;

        public Nodo(Pais pais, double g, double h, Nodo nodoAnterior) {
            this.pais = pais;
            this.g = g;
            this.h = h;
            this.f = g + h;
            this.nodoAnterior = nodoAnterior;
        }
    }

    public static double calcularDistanciaHaversine(Pais pais1, Pais pais2) {
        double radioTierra = 6371.0;

        // Convertir latitud y longitud de grados a radianes
        double lat1 = Math.toRadians(pais1.latitud);
        double lon1 = Math.toRadians(pais1.longitud);
        double lat2 = Math.toRadians(pais2.latitud);
        double lon2 = Math.toRadians(pais2.longitud);

        // Diferencia de latitud y longitud
        double difLat = lat2 - lat1;
        double difLon = lon2 - lon1;

        // Fórmula de Haversine
        double a = Math.pow(Math.sin(difLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(difLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcular la distancia
        double distancia = radioTierra * c;

        return distancia;
    }

    public static List<Pais> encontrarRuta(List<Pais> paises, Pais inicio, Pais objetivo) {
        

        return null;
    }

    public static void main(String[] args) {
        // Crear una lista de países (nodos)
        List<Pais> paises = new ArrayList<>();
        Pais pais1 = new Pais("Abu Dhabi", 24.893732459037555, 54.89858537181394);
        Pais pais2 =new Pais("Sao Paulo", -23.701037620045756, -46.6975252507874);
        Pais pais3 =new Pais("Las Vegas", 36.11002772323055, -115.16159594998166);
        Pais pais4 =new Pais("Singapur", 1.291646420909432, 103.86507913837758);
        Pais pais5 =new Pais("CDMX", 19.405675471867752, -99.09215850876126);
        Pais pais6 =new Pais("Austria", 47.220903412420896, 14.762455921315366);
        Pais pais7 =new Pais("Suzuka", 34.845725549266106, 136.53929551872739);
        Pais pais8 =new Pais("Austin", 30.1324285764382, -97.63919125816821);
        Pais pais9 =new Pais("Qatar", 25.48867333132646, 51.45104766441345);
        Pais pais10 =new Pais("Zandvoort", 52.3893542048697, 4.542057833582422);
        Pais pais11 =new Pais("Belgica", 50.33434858360259, 6.945422022742407);
        Pais pais12 =new Pais("Hungría", 47.91418901181555, 20.134884821169678);
        Pais pais13 =new Pais("Silverstone", 52.06937703306624, -1.0214436819075534);
        Pais pais14 =new Pais("Canada", 45.50178402588114, -73.52724016966773);
        Pais pais15 =new Pais("Barcelona", 41.568724478432316, 2.2577303497205525);
        // ...

        // Agregar conexiones entre países
        pais15.agregarConexion(pais14, 5891.57); //Conexión y distancia
        pais14.agregarConexion(pais13, 5137.48); //Conexión y distancia
        pais13.agregarConexion(pais12,  1574.89); //Conexión y distancia
        pais12.agregarConexion(pais11, 995.28); //Conexión y distancia
        pais11.agregarConexion(pais10, 282.91); //Conexión y distancia
        pais10.agregarConexion(pais9, 4911.74); //Conexión y distancia
        pais9.agregarConexion(pais8, 13008.35); //Conexión y distancia
        pais8.agregarConexion(pais7, 10829.04); //Conexión y distancia
        pais6.agregarConexion(pais5, 10104.37); //Conexión y distancia
        pais5.agregarConexion(pais4, 16612.94); //Conexión y distancia
        pais4.agregarConexion(pais3, 14220.86); //Conexión y distancia
        pais3.agregarConexion(pais2, 9786.70); //Conexión y distancia
        pais2.agregarConexion(pais1, 12191.74); //Conexión y distancia
        pais1.agregarConexion(pais15, 5146.32); //Conexión y distancia
        
        
        // ...

        // Especificar el país de inicio y el país objetivo
        Pais inicio = pais1; // Cambia según el país de inicio deseado
        Pais objetivo = pais7; // Cambia según el país objetivo deseado

        // Encontrar la ruta utilizando el algoritmo A*
        List<Pais> ruta = encontrarRuta(paises, inicio, objetivo);

        if (ruta != null) {
            System.out.println("Ruta encontrada:");
            for (Pais pais : ruta) {
                System.out.println(pais.nombre);
            }
        } else {
            System.out.println("No se encontró una ruta.");
        }
    }

    
    // Métodos auxiliares para buscar países en la lista de nodos
    public static boolean contienePais(List<Nodo> listaNodos, Pais pais) {
        // Implementa aquí la búsqueda de países en la lista de nodos.
        return false;
    }

    public static Nodo encontrarNodo(List<Nodo> listaNodos, Pais pais) {
        // Implementa aquí la búsqueda de nodos por país en la lista de nodos.
        return null;
        
    }
}
