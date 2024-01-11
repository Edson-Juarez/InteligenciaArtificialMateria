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

public class HanoiProfundidad {
    Stack<Integer>[] torre;
    int movimientos;

    public HanoiProfundidad(int[][] tableroInicial) {
        torre = new Stack[3];
        for (int i = 0; i < 3; i++) {
            torre[i] = new Stack<Integer>();
        }
        for (int i = tableroInicial.length - 1; i >= 0; i--) {
            for (int j = 0; j < tableroInicial[i].length; j++) {
                if (tableroInicial[i][j] != 0) {
                    torre[j].push(tableroInicial[i][j]);
                }
            }
        }
    }

    public void mover(int n, int inicio, int medio, int fin) {
        if (n > 0) {
            mover(n - 1, inicio, fin, medio);
            if (!torre[inicio].isEmpty() && (torre[fin].isEmpty() || torre[inicio].peek() < torre[fin].peek())) {
                int mover = torre[inicio].pop();
                torre[fin].push(mover);
                movimientos++;
                System.out.println("Movimiento #" + movimientos);
                imprimir();
            }
            mover(n - 1, medio, inicio, fin);
        }
    }

    public void imprimir() {
        System.out.println("Torre 1: " + torre[0]);
        System.out.println("Torre 2: " + torre[1]);
        System.out.println("Torre 3: "
                + "" + torre[2]);
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] tableroInicial = {
            {6, 4, 0},
            {5, 3, 2},
            {1}
        };

        HanoiProfundidad torresDeHanoi = new HanoiProfundidad(tableroInicial);
        torresDeHanoi.imprimir();
        torresDeHanoi.mover(7, 0, 1, 2);
    }
}
