/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmo.a;

/**
 *
 * @author edson
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.List;

// Definición de la clase "Estado1" que representa el estado del problema en un momento dado
class Estado1 {
    int misionerosIzq; // Misioneros en el lado izquierdo
    int canibalesIzq; // Caníbales en el lado izquierdo
    int misionerosDer; // Misioneros en el lado derecho
    int canibalesDer; // Caníbales en el lado derecho
    boolean barcoIzq; // Posición del barco (true si está en el lado izquierdo, false si está en el lado derecho)
    Estado1 previo; // Estado1 previo (padre) en el camino hacia la solución

    // Constructor de la clase "Estado1" para crear instancias con valores iniciales
    public Estado1(int misionerosIzq, int canibalesIzq, int misionerosDer, int canibalesDer, boolean barcoIzq, Estado1 previo) {
        this.misionerosIzq = misionerosIzq;
        this.canibalesIzq = canibalesIzq;
        this.misionerosDer = misionerosDer;
        this.canibalesDer = canibalesDer;
        this.barcoIzq = barcoIzq;
        this.previo = previo;
    }

    // Método para verificar si el estado actual es válido según las restricciones del problema
    public boolean esValido() {
        return (misionerosIzq >= 0 && canibalesIzq >= 0 && misionerosDer >= 0 && canibalesDer >= 0
                && (misionerosIzq == 0 || misionerosIzq >= canibalesIzq)
                && (misionerosDer == 0 || misionerosDer >= canibalesDer));
    }

    // Método para verificar si el estado actual es el estado objetivo del problema
    public boolean esObjetivo() {
        return misionerosIzq == 0 && canibalesIzq == 0 && !barcoIzq;
    }
}

// Clase principal "ResolucionAcertijo"
public class ResolucionAcertijo {

     // Método para buscar la solución al problema utilizando búsqueda por amplitud
    public static boolean buscarSolucion(Estado1 estadoInicial) {
        Queue<Estado1> cola = new LinkedList<>(); // Crear una cola para la búsqueda
        cola.add(estadoInicial); // Agregar el estado inicial a la cola
        HashSet<Estado1> visitados = new HashSet<>(); // Conjunto para mantener estados visitados
        int movimientos = 0; // Variable para contar los movimientos

        // Bucle principal de búsqueda
        while (!cola.isEmpty()) {
            Estado1 estadoActual = cola.poll(); // Obtener y remover el primer estado de la cola

            // Verificar si el estado actual es el estado objetivo
            if (estadoActual.esObjetivo()) {
                mostrarCamino(estadoActual); // Mostrar el camino hacia la solución
                return true; // Se encontró una solución
            }

            visitados.add(estadoActual); // Agregar el estado actual a la lista de visitados
            movimientos++; // Incrementar el contador de movimientos

            expandirEstado1(estadoActual, cola, visitados, movimientos); // Expandir y agregar estados sucesores a la cola
        }

        return false; // No se encontró una solución
    }

    // Método para expandir el estado actual y agregar estados sucesores válidos a la cola
    public static void expandirEstado1(Estado1 estado, Queue<Estado1> cola, HashSet<Estado1> visitados, int movimientos) {
        int[] movimientosArray = { 1, 1, 2, 0, 0, 1, 0, 2, 1, 0 };
        int direccion = estado.barcoIzq ? 1 : -1; // Dirección del barco

        // Bucle para generar y verificar estados sucesores
        for (int i = 0; i < movimientosArray.length; i += 2) {
            int deltaMisioneros = movimientosArray[i] * direccion;
            int deltaCanibales = movimientosArray[i + 1] * direccion;

            // Calcular los valores de los nuevos estados sucesores
            int nuevoMisionerosIzq = estado.misionerosIzq - deltaMisioneros;
            int nuevoCanibalesIzq = estado.canibalesIzq - deltaCanibales;
            int nuevoMisionerosDer = estado.misionerosDer + deltaMisioneros;
            int nuevoCanibalesDer = estado.canibalesDer + deltaCanibales;
            boolean nuevoBarcoIzq = !estado.barcoIzq;

            // Crear el nuevo estado sucesor
            Estado1 nuevoEstado1 = new Estado1(nuevoMisionerosIzq, nuevoCanibalesIzq, nuevoMisionerosDer, nuevoCanibalesDer, nuevoBarcoIzq, estado);

            // Verificar si el nuevo estado es válido y si no ha sido visitado previamente
            if (nuevoEstado1.esValido() && !visitados.contains(nuevoEstado1)) {
                cola.add(nuevoEstado1); // Agregar el nuevo estado a la cola
            }
        }
    }

    // Método para mostrar el camino desde el estado objetivo hasta el estado inicial
    public static void mostrarCamino(Estado1 estado) {
        System.out.println("Camino hacia la solución:");
        List<Estado1> camino = new LinkedList<>();

        // Construir el camino retrocediendo desde el estado objetivo hasta el inicial
        while (estado != null) {
            camino.add(estado);
            estado = estado.previo;
        }

        // Imprimir el camino en orden inverso para mostrar la secuencia de estados
        for (int i = camino.size() - 1; i >= 0; i--) {
            Estado1 estadoActual = camino.get(i);
            System.out.println("*Movimiento [" + (camino.size() - 1 - i) + "]"); // Imprimir el número de movimiento
            System.out.println("Orilla izquierda: [" + estadoActual.misionerosDer + "] misioneros, [" + estadoActual.canibalesDer + "] caníbales");
            System.out.println("Orilla derecha: [" + estadoActual.misionerosIzq + "] misioneros, [" + estadoActual.canibalesIzq + "] caníbales");
            System.out.println("Barco en el lado: " + (estadoActual.barcoIzq ? "Derecho" : "Izquierdo") + "      |_BARCO/");
            System.out.print("<<= = = = = = = = = = = = = = = = = = = = = = = = =>>");
            System.out.println();
        }
    }
    
    // Método principal "main" donde se establecen las condiciones iniciales y se inicia la resolución del problema
    public static void main(String[] args) {
        int misionerosInicio = 3;
        int canibalesInicio = 3;
        int misionerosObjetivo = 0;
        int canibalesObjetivo = 0;

        // Crear el estado inicial con las condiciones iniciales
        Estado1 estadoInicial = new Estado1(misionerosInicio, canibalesInicio, misionerosObjetivo, canibalesObjetivo, true, null);

        // Llamar al método "buscarSolucion" para resolver el problema
        boolean solucionEncontrada = buscarSolucion(estadoInicial);

        // Mostrar un mensaje en caso de que no se encuentre una solución
        if (!solucionEncontrada) {
            System.out.println("No se encontró una solución");
        }
    }
}
