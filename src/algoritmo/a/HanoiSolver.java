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

public class HanoiSolver {

    public static void main(String[] args) {
        int[][] tableroInicial = {
            {1, 0, 0},
            {2, 0, 0},
            {3, 0, 0}
        };

        int[][] tableroObjetivo = {
            {0, 0, 1},
            {0, 0, 2},
            {0, 0, 3}
        };

        solveHanoi(tableroInicial, tableroObjetivo);
    }

    public static void solveHanoi(int[][] initialBoard, int[][] goalBoard) {
        int n = initialBoard.length;
        List<int[][]> visited = new ArrayList<>();
        Queue<int[][]> queue = new LinkedList<>();

        queue.add(initialBoard);
        visited.add(initialBoard);

        while (!queue.isEmpty()) {
            int[][] currentBoard = queue.poll();
            if (Arrays.deepEquals(currentBoard, goalBoard)) {
                System.out.println("Estado objetivo alcanzado:");
                printBoard(currentBoard);
                break;
            }

            for (int from = 0; from < n; from++) {
                for (int to = 0; to < n; to++) {
                    if (canMove(currentBoard, from, to)) {
                        int[][] newBoard = copyBoard(currentBoard);
                        move(newBoard, from, to);

                        if (!visited.contains(newBoard)) {
                            visited.add(newBoard);
                            queue.add(newBoard);
                        }
                    }
                }
            }
        }
    }

    public static boolean canMove(int[][] board, int from, int to) {
        int n = board.length;
        if (from == to || board[from][0] == 0 || (board[to][0] != 0 && board[from][0] > board[to][0])) {
            return false;
        }
        return true;
    }

    public static void move(int[][] board, int from, int to) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][from] != 0) {
                for (int j = board.length - 1; j >= 0; j--) {
                    if (board[j][to] == 0) {
                        board[j][to] = board[i][from];
                        board[i][from] = 0;
                        break;
                    }
                }
                break;
            }
        }
    }

    public static int[][] copyBoard(int[][] board) {
        int n = board.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    public static void printBoard(int[][] board) {
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
