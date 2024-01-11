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

public class RiosFamilia2 {

    static class Estado {

        Set<String> orillaIzquierda;
        Set<String> orillaDerecha;
        boolean barcoEnOrillaIzquierda;
        Estado padre;

        public Estado(Set<String> orillaIzquierda, Set<String> orillaDerecha, boolean barcoEnOrillaIzquierda, Estado padre) {
            this.orillaIzquierda = orillaIzquierda;
            this.orillaDerecha = orillaDerecha;
            this.barcoEnOrillaIzquierda = barcoEnOrillaIzquierda;
            this.padre = padre;
        }

        // Verifica si el estado actual es válido considerando las restricciones del problema
        boolean esValido() {
            // Lógica de validación para un estado válido
            if (orillaIzquierda.contains("mamá") && (orillaIzquierda.contains("chamaco1") || orillaIzquierda.contains("chamaco2")) && !orillaIzquierda.contains("padre")) {
                return false;
            }
            if (orillaDerecha.contains("mamá") && (orillaDerecha.contains("chamaco1") || orillaDerecha.contains("chamaco2")) && !orillaDerecha.contains("padre")) {
                return false;
            }
            if (orillaIzquierda.contains("padre") && (orillaIzquierda.contains("niña1") || orillaIzquierda.contains("niña2")) && !orillaIzquierda.contains("mamá")) {
                return false;
            }
            if (orillaDerecha.contains("padre") && (orillaDerecha.contains("niña1") || orillaDerecha.contains("niña2")) && !orillaDerecha.contains("mamá")) {
                return false;
            }
            if (orillaIzquierda.contains("ratero") && orillaIzquierda.size() > 1 && !orillaIzquierda.contains("policía")) {
                return false;
            }
            if (orillaDerecha.contains("ratero") && orillaDerecha.size() > 1 && !orillaDerecha.contains("policía")) {
                return false;
            }
            return true;
        }

        // Verifica si el estado actual es la meta del problema (orilla izquierda vacía)
        boolean esMeta() {
            return orillaIzquierda.isEmpty();
        }

        // Genera y devuelve los posibles estados sucesores
        List<Estado> getSucesores() {
            List<Estado> sucesores = new ArrayList<>();
            String[] conductoresPosibles = {"padre", "mamá", "policía"};

            Set<String> orillaActual = barcoEnOrillaIzquierda ? orillaIzquierda : orillaDerecha;
            Set<String> otraOrilla = barcoEnOrillaIzquierda ? orillaDerecha : orillaIzquierda;

            for (String conductor : conductoresPosibles) {
                if (!orillaActual.contains(conductor)) {
                    continue;
                }

                agregarSucesor(sucesores, orillaActual, otraOrilla, conductor, null);

                for (String pasajero : orillaActual) {
                    if (!pasajero.equals(conductor)) {
                        agregarSucesor(sucesores, orillaActual, otraOrilla, conductor, pasajero);
                    }
                }
            }
            return sucesores;
        }

        // Método auxiliar para agregar un sucesor si es válido
        private void agregarSucesor(List<Estado> sucesores, Set<String> orillaActual, Set<String> otraOrilla, String conductor, String pasajero) {
            Set<String> siguienteOrillaIzquierda = new HashSet<>(orillaIzquierda);
            Set<String> siguienteOrillaDerecha = new HashSet<>(orillaDerecha);

            // Mueve a los personajes dependiendo de la posición de la barco
            if (barcoEnOrillaIzquierda) {
                siguienteOrillaIzquierda.remove(conductor);
                if (pasajero != null) {
                    siguienteOrillaIzquierda.remove(pasajero);
                }
                siguienteOrillaDerecha.add(conductor);
                if (pasajero != null) {
                    siguienteOrillaDerecha.add(pasajero);
                }
            } else {
                siguienteOrillaDerecha.remove(conductor);
                if (pasajero != null) {
                    siguienteOrillaDerecha.remove(pasajero);
                }
                siguienteOrillaIzquierda.add(conductor);
                if (pasajero != null) {
                    siguienteOrillaIzquierda.add(pasajero);
                }
            }

            Estado siguienteEstado = new Estado(siguienteOrillaIzquierda, siguienteOrillaDerecha, !barcoEnOrillaIzquierda, this);

            if (siguienteEstado.esValido()) {
                sucesores.add(siguienteEstado);
            }
        }

        // Comparación de igualdad entre estados para la gestión de visitados
        @Override
        public boolean equals(Object o) {
            // Lógica de comparación de igualdad entre estados
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Estado estado = (Estado) o;
            return barcoEnOrillaIzquierda == estado.barcoEnOrillaIzquierda
                    && Objects.equals(orillaIzquierda, estado.orillaIzquierda)
                    && Objects.equals(orillaDerecha, estado.orillaDerecha);

        }

        // Generación de un código hash para la gestión de visitados
        @Override
        public int hashCode() {
            // Lógica para generar el código hash del estado
            return Objects.hash(orillaIzquierda, orillaDerecha, barcoEnOrillaIzquierda);

        }
    }

    // Algoritmo para resolver el problema del cruce del río
    public static void resolver(Set<String> orillaIzquierdaInicial, Set<String> orillaDerechaInicial, boolean barcoEnOrillaIzquierdaInicial) {
        Estado estadoInicial = new Estado(orillaIzquierdaInicial, orillaDerechaInicial, barcoEnOrillaIzquierdaInicial, null);

        Stack<Estado> pila = new Stack<>();
        Set<Estado> visitados = new HashSet<>();
        pila.push(estadoInicial);

        while (!pila.isEmpty()) {
            Estado estadoActual = pila.pop();

            if (!estadoActual.esValido() || visitados.contains(estadoActual)) {
                continue;
            }

            if (estadoActual.esMeta()) {
                imprimirSolucion(estadoActual);
                return;
            }

            visitados.add(estadoActual);
            pila.addAll(estadoActual.getSucesores());
        }

        System.out.println("No se encontró solución.");
    }

    // Método para imprimir la solución encontrada recursivamente
    private static void imprimirSolucion(Estado solucion) {
        if (solucion == null) {
            return;
        }
        imprimirSolucion(solucion.padre);
        System.out.println("Orilla Izquierda: " + solucion.orillaIzquierda);
        System.out.println("Barco en orilla: " + (solucion.barcoEnOrillaIzquierda ? "Izquierda" : "Derecha"));
        System.out.println("Orilla Derecha: " + solucion.orillaDerecha);
        System.out.println("|| = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = ||");
    }

    public static void main(String[] args) {
        // Configuración inicial de las orillas y la posición de la barco
        Set<String> orillaDerecha = new HashSet<>(Arrays.asList("padre", "chamaco1", "chamaco2"));
        Set<String> orillaIzquierda = new HashSet<>(Arrays.asList("policía", "ratero", "mamá", "niña1", "niña2"));
        boolean barcoEnOrillaIzquierda = false;
        resolver(orillaIzquierda, orillaDerecha, barcoEnOrillaIzquierda);
    }
}
