/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmo.a;

/**
 *
 * @author edson
 */import java.util.*;

public class RiosFamilia {

    static class Estado {
        Set<String> orillaIzquierda;
        Set<String> orillaDerecha;
        boolean barcaEnOrillaIzquierda;
        Estado padre;

        // Constructor del Estado
        Estado(Set<String> orillaIzquierda, Set<String> orillaDerecha, boolean barcaEnOrillaIzquierda, Estado padre) {
            this.orillaIzquierda = new HashSet<>(orillaIzquierda);
            this.orillaDerecha = new HashSet<>(orillaDerecha);
            this.barcaEnOrillaIzquierda = barcaEnOrillaIzquierda;
            this.padre = padre;
        }

        // Comprueba si el estado actual es válido
        boolean esValido() {
            // Verifica las restricciones para un estado válido
            // En este caso, se verifica si hay alguna combinación no permitida en las orillas
           if (orillaIzquierda.contains("thief") && orillaIzquierda.size() > 1 && !orillaIzquierda.contains("policeman")) {
                return false;
            }
            if (orillaDerecha.contains("thief") && orillaDerecha.size() > 1 && !orillaDerecha.contains("policeman")) {
                return false;
            }
            if (orillaIzquierda.contains("mother") && (orillaIzquierda.contains("son1") || orillaIzquierda.contains("son2")) && !orillaIzquierda.contains("father")) {
                return false;
            }
            if (orillaDerecha.contains("mother") && (orillaDerecha.contains("son1") || orillaDerecha.contains("son2")) && !orillaDerecha.contains("father")) {
                return false;
            }
            if (orillaIzquierda.contains("father") && (orillaIzquierda.contains("daughter1") || orillaIzquierda.contains("daughter2")) && !orillaIzquierda.contains("mother")) {
                return false;
            }
            if (orillaDerecha.contains("father") && (orillaDerecha.contains("daughter1") || orillaDerecha.contains("daughter2")) && !orillaDerecha.contains("mother")) {
                return false;
            }
            return true;// Devuelve true si el estado es válido
        }

        // Comprueba si el estado actual es el estado objetivo (todos en la orilla izquierda)
        boolean esEstadoFinal() {
            return orillaIzquierda.isEmpty(); // Devuelve true si la orilla izquierda está vacía
        }

        // Obtiene los sucesores del estado actual (movimientos posibles)
        List<Estado> getSucesores() {
            List<Estado> sucesores = new ArrayList<>();
            String[] posiblesConductores = {"padre", "madre", "policía"};

            // Determina las orillas actual y opuesta a la posición de la barca
            Set<String> orillaActual = barcaEnOrillaIzquierda ? orillaIzquierda : orillaDerecha;
            Set<String> otraOrilla = barcaEnOrillaIzquierda ? orillaDerecha : orillaIzquierda;

            for (String conductor : posiblesConductores) {
                if (!orillaActual.contains(conductor)) continue;

                // Intenta mover al conductor solo
                agregarSucesor(sucesores, orillaActual, otraOrilla, conductor, null);

                // Intenta mover al conductor con un pasajero (si hay uno disponible)
                for (String pasajero : orillaActual) {
                    if (!pasajero.equals(conductor)) {
                        agregarSucesor(sucesores, orillaActual, otraOrilla, conductor, pasajero);
                    }
                }
            }
            return sucesores;
        }

        // Método auxiliar para agregar un estado sucesor si es válido
        private void agregarSucesor(List<Estado> sucesores, Set<String> orillaActual, Set<String> otraOrilla, String conductor, String pasajero) {
            Set<String> siguienteOrillaIzquierda = new HashSet<>(orillaIzquierda);
            Set<String> siguienteOrillaDerecha = new HashSet<>(orillaDerecha);

            // Mueve a los personajes dependiendo de la posición de la barca
            if (barcaEnOrillaIzquierda) {
                siguienteOrillaIzquierda.remove(conductor);
                if (pasajero != null) siguienteOrillaIzquierda.remove(pasajero);
                siguienteOrillaDerecha.add(conductor);
                if (pasajero != null) siguienteOrillaDerecha.add(pasajero);
            } else {
                siguienteOrillaDerecha.remove(conductor);
                if (pasajero != null) siguienteOrillaDerecha.remove(pasajero);
                siguienteOrillaIzquierda.add(conductor);
                if (pasajero != null) siguienteOrillaIzquierda.add(pasajero);
            }

            Estado siguienteEstado = new Estado(siguienteOrillaIzquierda, siguienteOrillaDerecha, !barcaEnOrillaIzquierda, this);

            if (siguienteEstado.esValido()) {
                sucesores.add(siguienteEstado);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Estado estado = (Estado) o;
            return barcaEnOrillaIzquierda == estado.barcaEnOrillaIzquierda &&
                    Objects.equals(orillaIzquierda, estado.orillaIzquierda) &&
                    Objects.equals(orillaDerecha, estado.orillaDerecha);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orillaIzquierda, orillaDerecha, barcaEnOrillaIzquierda);
        }
    }

    // Método para imprimir la solución recursivamente
    private static void imprimirSolucion(Estado solucion) {
        if (solucion == null) return;
        imprimirSolucion(solucion.padre);
        System.out.println("Orilla Izquierda: " + solucion.orillaIzquierda + ", Orilla Derecha: " + solucion.orillaDerecha +
                ", Barca en orilla izquierda: " + solucion.barcaEnOrillaIzquierda);
    }

    // Método principal para resolver el problema del cruce del río
    public static void resolver() {
        Set<String> orillaIzquierdaInicial = new HashSet<>(Arrays.asList("madre", "padre", "hijo1", "hijo2", "hija1", "hija2", "policía", "ladrón"));
        Set<String> orillaDerechaInicial = new HashSet<>();

        Estado estadoInicial = new Estado(orillaIzquierdaInicial, orillaDerechaInicial, true, null);

        Stack<Estado> pila = new Stack<>();
        Set<Estado> visitados = new HashSet<>();
        pila.push(estadoInicial);

        while (!pila.isEmpty()) {
            Estado estadoActual = pila.pop();

            // Imprime el estado actual
            System.out.println("Visitando Estado:");
            System.out.println("Orilla Izquierda: " + estadoActual.orillaIzquierda + ",\nOrilla Derecha: " + estadoActual.orillaDerecha +
                    ",\nBarca en orilla izquierda: " + estadoActual.barcaEnOrillaIzquierda);
            System.out.println("--------------------------------------------");

            if (!estadoActual.esValido() || visitados.contains(estadoActual)) {
                continue;
            }

            if (estadoActual.esEstadoFinal()) {
                System.out.println("Solución encontrada:");
                //imprimirSolucion(estadoActual);
                return;
            }

            visitados.add(estadoActual);
            pila.addAll(estadoActual.getSucesores());
        }

        System.out.println("No se encontró solución.");
    }

    public static void main(String[] args) {
        resolver();
    }
}

