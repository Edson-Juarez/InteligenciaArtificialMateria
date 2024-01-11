/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmo.a;

/**
 *
 * @author edson
 */
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraDistancia {

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

    public static void main(String[] args) {
        // Crear una lista de lugares
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
}
