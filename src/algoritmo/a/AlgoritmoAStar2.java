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
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class AlgoritmoAStar2 {

    public static class Nodo {

        String nombre;
        double costoG; // Costo acumulado desde el nodo inicial hasta este nodo
        double costoH; // Heurística (costo estimado desde este nodo hasta el objetivo)
        double costoF; // F = G + H
        List<Arista> aristas;
        Nodo nodoPadre;

        public Nodo(String nombre, double costoH) {
            this.nombre = nombre;
            this.costoG = Double.MAX_VALUE; // Inicializamos el costo G como infinito
            this.costoH = costoH;
            this.costoF = costoG + costoH;
            this.aristas = new ArrayList<>();
            this.nodoPadre = null;
        }

        public void agregarArista(Nodo destino, double costo) {
            this.aristas.add(new Arista(destino, costo));
        }
    }

    public static class Lugar {

        String nombre;
        double latitud; // Usar double para representar latitud
        double longitud; // Usar double para representar longitud

        public Lugar(String nombre, double latitud, double longitud) {
            this.nombre = nombre;
            this.latitud = latitud;
            this.longitud = longitud;
        }
    }

    public static double calcularDistancia(Lugar lugar1, Lugar lugar2) {
        // Radio de la Tierra en kilómetros
        double radioTierra = 6371.0;

        // Convertir latitud y longitud de grados a radianes
        double lat1 = Math.toRadians(lugar1.latitud);
        double lon1 = Math.toRadians(lugar1.longitud);
        double lat2 = Math.toRadians(lugar2.latitud);
        double lon2 = Math.toRadians(lugar2.longitud);

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

    public static class Arista {

        Nodo destino;
        double costo;

        public Arista(Nodo destino, double costo) {
            this.destino = destino;
            this.costo = costo;
        }
    }

    public static List<Nodo> encontrarRuta(Nodo inicio, Nodo objetivo, Set<Nodo> abiertos, Set<Nodo> cerrados) {
        PriorityQueue<Nodo> abiertosQueue = new PriorityQueue<>(Comparator.comparingDouble(n -> n.costoF));

        inicio.costoG = 0.0;
        inicio.costoF = inicio.costoH;
        abiertosQueue.add(inicio);

        while (!abiertosQueue.isEmpty()) {
            Nodo nodoActual = abiertosQueue.poll();

            if (nodoActual == objetivo) {
                // Se ha encontrado la ruta, reconstruir y devolverla
                return reconstruirRuta(nodoActual);
            }

            abiertos.remove(nodoActual);
            cerrados.add(nodoActual);

            for (Arista arista : nodoActual.aristas) {
                Nodo nodoVecino = arista.destino;

                if (cerrados.contains(nodoVecino)) {
                    continue; // Ya se ha explorado este nodo
                }

                double nuevoCostoG = nodoActual.costoG + arista.costo;

                if (!abiertos.contains(nodoVecino) || nuevoCostoG < nodoVecino.costoG) {
                    // Se ha encontrado un camino mejor hacia este nodo
                    nodoVecino.costoG = nuevoCostoG;
                    nodoVecino.costoF = nuevoCostoG + nodoVecino.costoH;
                    nodoVecino.nodoPadre = nodoActual;

                    if (!abiertos.contains(nodoVecino)) {
                        abiertosQueue.add(nodoVecino);
                        abiertos.add(nodoVecino);
                    }
                }
            }
        }

        // No se encontró una ruta
        return null;
    }

    public static double calcularCostoTotal(List<Nodo> ruta) {
        double costoTotal = 0.0;
        for (int i = 0; i < ruta.size() - 1; i++) {
            Nodo nodoActual = ruta.get(i);
            Nodo nodoSiguiente = ruta.get(i + 1);

            // Buscar la arista que conecta el nodo actual con el siguiente
            for (Arista arista : nodoActual.aristas) {
                if (arista.destino == nodoSiguiente) {
                    costoTotal += arista.costo;
                    break;
                }
            }
        }
        return costoTotal;
    }

    public static void impresion() {
        List<Lugar> lugares = new ArrayList<>();
        lugares.add(new Lugar("Abu Dhabi", 24.893732459037555, 54.89858537181394));
        lugares.add(new Lugar("Sao Paulo", -23.701037620045756, -46.6975252507874));
        lugares.add(new Lugar("Las Vegas", 36.11002772323055, -115.16159594998166));
        lugares.add(new Lugar("Singapur", 1.291646420909432, 103.86507913837758));
        lugares.add(new Lugar("CDMX", 19.405675471867752, -99.09215850876126));
        lugares.add(new Lugar("Austria", 47.220903412420896, 14.762455921315366));
        lugares.add(new Lugar("Suzuka", 34.845725549266106, 136.53929551872739));
        lugares.add(new Lugar("Austin", 30.1324285764382, -97.63919125816821));
        lugares.add(new Lugar("Qatar", 25.48867333132646, 51.45104766441345));
        lugares.add(new Lugar("Zandvoort", 52.3893542048697, 4.542057833582422));
        lugares.add(new Lugar("Belgica", 50.33434858360259, 6.945422022742407));
        lugares.add(new Lugar("Hungría", 47.91418901181555, 20.134884821169678));
        lugares.add(new Lugar("Silverstone", 52.06937703306624, -1.0214436819075534));
        lugares.add(new Lugar("Canada", 45.50178402588114, -73.52724016966773));
        lugares.add(new Lugar("Barcelona", 41.568724478432316, 2.2577303497205525));
        // Agrega más lugares aquí...

        // Calcular y mostrar las distancias entre todos los pares de lugares
        for (int i = 0; i < lugares.size(); i++) {
            for (int j = i + 1; j < lugares.size(); j++) {
                Lugar lugar1 = lugares.get(i);
                Lugar lugar2 = lugares.get(j);
                double distancia = calcularDistancia(lugar1, lugar2);
                System.out.printf("Distancia entre %s y %s: %.2f km%n", lugar1.nombre, lugar2.nombre, distancia);
            }
        }

    }

    public static List<Nodo> reconstruirRuta(Nodo nodo) {
        List<Nodo> ruta = new ArrayList<>();
        while (nodo != null) {
            ruta.add(nodo);
            nodo = nodo.nodoPadre;
        }
        Collections.reverse(ruta);
        return ruta;
    }

    public static void imprimirSet(String nombreSet, Set<Nodo> set) {
        System.out.print(nombreSet + ": [");
        for (Nodo nodo : set) {
            System.out.print(nodo.nombre + ", ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        // Crear nodos
        Nodo A = new Nodo("Abu Dhabi", 1.0); // Nodo inicial
        Nodo B = new Nodo("Sao Paulo", 2.0);
        Nodo C = new Nodo("Las Vegas", 3.0);
        Nodo D = new Nodo("Singapur", 4.0); // Nodo objetivo
        Nodo E = new Nodo("CDMX", 5.0); // Nodo objetivo
        Nodo F = new Nodo("Austria", 6.0); // Nodo objetivo
        Nodo G = new Nodo("Suzuka", 7.0); // Nodo objetivo
        Nodo H = new Nodo("Austin", 8.0); // Nodo objetivo
        Nodo I = new Nodo("Qatar", 9.0); // Nodo objetivo
        Nodo J = new Nodo("Zandvoort", 10.0); // Nodo objetivo
        Nodo K = new Nodo("Belgica", 11.0); // Nodo objetivo
        Nodo L = new Nodo("Hungría", 12.0); // Nodo objetivo
        Nodo M = new Nodo("Silvertsone", 13.0); // Nodo objetivo
        Nodo N = new Nodo("Barcelona", 14.0); // Nodo objetivo
        Nodo Ñ = new Nodo("Canada", 15.0); // Nodo objetivo

        // Establecer conexiones entre nodos        
        A.agregarArista(B, 5891.57); //Abu Dhabi-Las Vegas
         B.agregarArista(A, 5891.57); //Abu Dhabi-Las Vegas
       
       
         //A.agregarArista(D, 5869.15); //Abu - Singapur
        D.agregarArista(A, 5869.15); //Abu - Singapur
        
        A.agregarArista(C, 13151.93); //Abu -  Sao Paulo
        C.agregarArista(A, 13151.93); //Abu -  Sao Paulo
        
        C.agregarArista(E, 16612.94); //LAs vegas - CDMX
        E.agregarArista(C, 16612.94); //LAs vegas - CDMX

        D.agregarArista(G, 5035.95); //Singapur - Suzuka
        G.agregarArista(D, 5035.95); //Singapur - Suzuka

        B.agregarArista(G, 18736.94); //Sao Paulo Suzuka
        G.agregarArista(B, 18736.94); //Sao Paulo Suzuka

        
        E.agregarArista(F, 10104.37); //CDMX - Austria
        F.agregarArista(E, 10104.37); //CDMX - Austria
        
        G.agregarArista(F, 4911.74); //Suzuka - Austria
        F.agregarArista(G, 4911.74); //Suzuka - Austria

        E.agregarArista(H, 1201.71); //CDMX - Austin
        H.agregarArista(E, 1201.71); //CDMX - Austin
         
        H.agregarArista(J, 8157.64); //Austin - Zandvoort
         J.agregarArista(H, 8157.64); //Austin - Zandvoort
        
          J.agregarArista(M, 380.49); //Zandvoort - Silverstone
          M.agregarArista(J, 380.49); //Zandvoort - Silverstone
         
         J.agregarArista(Ñ, 16612.94); //Zandvoort - Canada
         Ñ.agregarArista(J, 16612.94); //Zandvoort - Canada
         
         J.agregarArista(K, 282.91); //Zandvoort - Belgica
         K.agregarArista(J, 282.91); //Zandvoort - Belgica
        
        J.agregarArista(I, 4911.74); //ZAndvoort - Qatar
        I.agregarArista(J, 4911.74); //ZAndvoort - Qatar

        M.agregarArista(L, 1574.89); //Silverstone - Hungría
        L.agregarArista(M, 1574.89); //Silverstone - Hungría
        
        M.agregarArista(N, 1193.66); //Silverstone  - Barcelona
        N.agregarArista(M, 1193.66); //Silverstone  - Barcelona
        
        M.agregarArista(Ñ, 5891.32);//Silvesrtone - Canada
        Ñ.agregarArista(M, 5891.32);//Silvesrtone - Canada

        // Arreglo de heurísticas para cada nodo
        double[] heuristica = {5.0, 3.0, 2.0, 0.0}; // Reemplaza con las heurísticas adecuadas

        // Asignar heurísticas a los nodos
        A.costoH = heuristica[0];
        B.costoH = heuristica[1];
        C.costoH = heuristica[2];
        D.costoH = heuristica[3];

        Set<Nodo> abiertos = new HashSet<>();
        Set<Nodo> cerrados = new HashSet<>();

        // Encontrar la ruta óptima
       List<Nodo> rutaOptima = encontrarRuta(B, H, abiertos, cerrados);

    // Imprimir conjuntos "open set" y "closed set" antes de mostrar la respuesta final
    impresion();
    imprimirSet("Open Set", abiertos);
    imprimirSet("Closed Set", cerrados);

    if (rutaOptima != null) {
        System.out.println("Ruta óptima encontrada:");
        for (Nodo nodo : rutaOptima) {
            System.out.print(nodo.nombre + " -> ");
        }
        double costoTotal = calcularCostoTotal(rutaOptima);
        System.out.println("\nCosto total de la ruta: " + costoTotal + " km");
    } else {
        System.out.println("No se encontró una ruta.");
    }
    }
}
