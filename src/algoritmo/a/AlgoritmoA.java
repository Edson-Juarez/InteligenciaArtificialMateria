/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package algoritmo.a;

/**
 *
 * @author edson
 */
import java.util.*;

class Nodo {

    int x, y; // Coordenadas del nodo en la matriz
    int costoG; // Costo acumulado desde el inicio hasta este nodo
    int costoH; // Heurística (estimación del costo restante)
    int costoF; // Costo total: costoG + costoH
    Nodo padre; // Nodo padre en el camino

    public Nodo(int x, int y) {
        this.x = x;
        this.y = y;
        this.costoG = 0;
        this.costoH = 0;
        this.costoF = 0;
        this.padre = null;
    }
}

public class AlgoritmoA {

    public static int encontrarCamino(int[][] matriz, Nodo inicio, Nodo destino) {
        int n = matriz.length;
        boolean[][] visitado = new boolean[n][n];

        PriorityQueue<Nodo> colaAbierta = new PriorityQueue<>(new Comparator<Nodo>() {
            @Override
            public int compare(Nodo nodo1, Nodo nodo2) {
                return Integer.compare(nodo1.costoF, nodo2.costoF);
            }
        });

        inicio.costoG = 0;
        inicio.costoH = calcularHeuristica(inicio, destino);
        inicio.costoF = inicio.costoG + inicio.costoH;
        colaAbierta.add(inicio);

        while (!colaAbierta.isEmpty()) {
            Nodo actual = colaAbierta.poll();

            if (actual.x == destino.x && actual.y == destino.y) {
                // Llegamos al destino
                reconstruirCamino(actual);
                return actual.costoG;
            }

            visitado[actual.x][actual.y] = true;

            // Movimientos posibles (arriba, abajo, izquierda, derecha)
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};

            for (int i = 0; i < 4; i++) {
                int nuevoX = actual.x + dx[i];
                int nuevoY = actual.y + dy[i];

                if (esValido(nuevoX, nuevoY, n) && !visitado[nuevoX][nuevoY] && matriz[nuevoX][nuevoY] != 1) {
                    Nodo sucesor = new Nodo(nuevoX, nuevoY);
                    sucesor.costoG = actual.costoG + 1;
                    sucesor.costoH = calcularHeuristica(sucesor, destino);
                    sucesor.costoF = sucesor.costoG + sucesor.costoH;
                    sucesor.padre = actual;

                    colaAbierta.add(sucesor);
                }
            }
        }

        // No se encontró un camino válido
        return -1;
    }

    private static boolean esValido(int x, int y, int n) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }

    private static int calcularHeuristica(Nodo nodo, Nodo destino) {
        // Heurística utilizada: distancia Manhattan
        return Math.abs(nodo.x - destino.x) + Math.abs(nodo.y - destino.y);
    }

    private static void reconstruirCamino(Nodo nodo) {
        Stack<Nodo> camino = new Stack<>();
        while (nodo != null) {
            camino.push(nodo);
            nodo = nodo.padre;
        }

        System.out.println("Camino encontrado:");
        while (!camino.isEmpty()) {
            Nodo paso = camino.pop();
            System.out.println("(" + paso.x + ", " + paso.y + ")");
        }
    }

    public static void main(String[] args) {
        int[][] matriz = {
            {0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0},
            {1, 1, 1, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1}
            
        };
        
        System.out.println(matriz.length);

        Nodo inicio = new Nodo(5,6);
        Nodo destino = new Nodo(4,7);

        int costo = encontrarCamino(matriz, inicio, destino);

        if (costo != -1) {
            System.out.println("Costo del camino: " + costo);
        } else {
            System.out.println("No se encontró un camino válido.");
        }
    }
}
