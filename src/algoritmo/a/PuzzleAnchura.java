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

public class PuzzleAnchura {
    private int[][] solucion = {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 0}
    };

    private class Estado {
        int[][] tablero;
        List<String> pasos;

        Estado(int[][] tablero, List<String> pasos) {
            this.tablero = tablero;
            this.pasos = pasos;
        }
    }

    public void resolver(int[][] tableroInicial) {
        Queue<Estado> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();

        cola.add(new Estado(tableroInicial, new ArrayList<>()));
        visitados.add(Arrays.deepToString(tableroInicial));

        while (!cola.isEmpty()) {
            Estado estadoActual = cola.poll();

            if (Arrays.deepEquals(estadoActual.tablero, solucion)) {
                System.out.println("¡Solución encontrada!");
                System.out.println("Pasos: " + String.join(", ", estadoActual.pasos));
                return;
            }

            for (Estado vecino : obtenerVecinos(estadoActual)) {
                String representacion = Arrays.deepToString(vecino.tablero);
                if (!visitados.contains(representacion)) {
                    cola.add(vecino);
                    visitados.add(representacion);
                }
            }
        }

        System.out.println("No se encontró una solución.");
    }

    private List<Estado> obtenerVecinos(Estado estado) {
    List<Estado> vecinos = new ArrayList<>();
    // Encuentra la posición del 0 (espacio vacío)
    int x = -1, y = -1;
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            if (estado.tablero[i][j] == 0) {
                x = i;
                y = j;
                break;
            }
        }
        if (x != -1) break;
    }

    // Genera los vecinos moviendo el espacio vacío en todas las direcciones posibles
    int[] dx = {-1, 0, 1, 0};
    int[] dy = {0, 1, 0, -1};
    String[] movimientos = {"arriba", "derecha", "abajo", "izquierda"};
    for (int i = 0; i < 4; i++) {
        int nx = x + dx[i], ny = y + dy[i];
        if (nx >= 0 && nx < 4 && ny >= 0 && ny < 4) {
            int[][] nuevoTablero = new int[4][4];
            for (int j = 0; j < 4; j++) {
                nuevoTablero[j] = estado.tablero[j].clone();
            }
            nuevoTablero[x][y] = nuevoTablero[nx][ny];
            nuevoTablero[nx][ny] = 0;

            List<String> nuevosPasos = new ArrayList<>(estado.pasos);
            nuevosPasos.add(movimientos[i]);

            vecinos.add(new Estado(nuevoTablero, nuevosPasos));
        }
    }

    return vecinos;
}
    public static void main(String[] args) {
        int[][] tableroInicial = {
            {5, 6, 7, 8},
            {9, 1, 2, 10},
            {11, 3, 4, 12},
            {13, 0, 14, 15}
        };

        new PuzzleAnchura().resolver(tableroInicial);
    }
}
