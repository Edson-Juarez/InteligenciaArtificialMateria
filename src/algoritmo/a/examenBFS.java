/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmo.a;

import java.util.*;

public class examenBFS {

    static class State {
        Set<String> orillaIzquierda;
        Set<String> orillaDerecha;
        boolean boteOrillaIzq;
        examenBFS.State parent;

        public State(Set<String> orillaIzquierda, Set<String> orillaDerecha, boolean boteOrillaIzq, examenBFS.State parent) {
            this.orillaIzquierda = orillaIzquierda;
            this.orillaDerecha = orillaDerecha;
            this.boteOrillaIzq = boteOrillaIzq;
            this.parent = parent;
        }

        /*boolean isValid() {
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
            return true;
        }*/
        boolean isValid() {
            if (orillaIzquierda.contains("Lobo Feroz") && orillaIzquierda.contains("Cabra") && !orillaIzquierda.contains("El Barquero")) {
                return false;
            }
            if (orillaDerecha.contains("Lobo Feroz") && orillaDerecha.contains("Cabra") && !orillaDerecha.contains("El Barquero")) {
                return false;
            }
            if (orillaIzquierda.contains("Cabra") && orillaIzquierda.contains("Col") && !orillaIzquierda.contains("El Barquero")) {
                return false;
            }
            if (orillaDerecha.contains("Cabra") && orillaDerecha.contains("Col") && !orillaDerecha.contains("El Barquero")) {
                return false;
            }

            return true;
        }

        boolean isGoal() {
            return orillaIzquierda.isEmpty();
        }

        List<examenBFS.State> getSiguientes() {
            List<examenBFS.State> siguientes = new ArrayList<>();
            // El bote sólo puede ser conducido por barquero
            String[] possibleDrivers = {"El Barquero"};

            Set<String> currentBank = boteOrillaIzq ? orillaIzquierda : orillaDerecha;
            Set<String> otherBank = boteOrillaIzq ? orillaDerecha : orillaIzquierda;

            for (String driver : possibleDrivers) {
                if (!currentBank.contains(driver)) continue;

                // Trata de mover sólo al conductor
                addSiguiente(siguientes, currentBank, otherBank, driver, null);

                for (String passenger : currentBank) {
                    if (!passenger.equals(driver)) {
                        addSiguiente(siguientes, currentBank, otherBank, driver, passenger);
                    }
                }
            }
            return siguientes;
        }

        // Método de ayuda para agregar un sucesor al estado (en caso de ser válido)
        private void addSiguiente(List<examenBFS.State> siguientes, Set<String> currentBank, Set<String> otherBank, String driver, String passenger) {
            Set<String> nextLeftBank = new HashSet<>(orillaIzquierda);
            Set<String> nextRightBank = new HashSet<>(orillaDerecha);

            // Moviendo a los personajes
            if (boteOrillaIzq) {
                nextLeftBank.remove(driver);
                if (passenger != null) nextLeftBank.remove(passenger);
                nextRightBank.add(driver);
                if (passenger != null) nextRightBank.add(passenger);
            } else {
                nextRightBank.remove(driver);
                if (passenger != null) nextRightBank.remove(passenger);
                nextLeftBank.add(driver);
                if (passenger != null) nextLeftBank.add(passenger);
            }

            examenBFS.State nextState = new examenBFS.State(nextLeftBank, nextRightBank, !boteOrillaIzq, this);

            if (nextState.isValid()) {
                siguientes.add(nextState);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            examenBFS.State state = (examenBFS.State) o;
            return boteOrillaIzq == state.boteOrillaIzq &&
                    Objects.equals(orillaIzquierda, state.orillaIzquierda) &&
                    Objects.equals(orillaDerecha, state.orillaDerecha);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orillaIzquierda, orillaDerecha, boteOrillaIzq);
        }
    }

    // Imprimir solución
    private static void printSolution(examenBFS.State solution) {
        if (solution == null) return;
        printSolution(solution.parent);
        System.out.println("Isla Izquierda: " + solution.orillaIzquierda + ",\nIsla Derecha: " + solution.orillaDerecha +
                ",\nBote en la izquierda: " + (solution.boteOrillaIzq? "Cierto" : "Falso") );
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }

    // BFS algoritmo
    public static void solve(Set<String> initialLeftBank, Set<String> initialRightBank, boolean initialBoatOnLeftBank) {
        State startState = new State(initialLeftBank, initialRightBank, initialBoatOnLeftBank, null);

        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        queue.offer(startState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (!currentState.isValid() || visited.contains(currentState)) {
                continue;
            }

            if (currentState.isGoal()) {
                printSolution(currentState);
                return;
            }

            visited.add(currentState);
            for (examenBFS.State siguiente : currentState.getSiguientes()) {
                if (!visited.contains(siguiente)) {
                    queue.offer(siguiente);
                }
            }
        }

        System.out.println("No se encontró solución.");
    }

    public static void main(String[] args) {
        Set<String> orillaIzquierda = new HashSet<>(Arrays.asList("El Barquero", "Cabra", "Lobo Feroz", "Col"));
        Set<String> orillaDerecha = new HashSet<>(Arrays.asList());
        boolean boteOrillaIzq = true; // El bote inicia en la izquierda

        solve(orillaIzquierda, orillaDerecha, boteOrillaIzq);}
}
