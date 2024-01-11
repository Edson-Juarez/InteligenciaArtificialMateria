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

public class examenProf {

    static class State {
        Set<String> orillaIzquierda;
        Set<String> orillaDerecha;
        boolean balsaLadoIzquierod;
        State parent;

        public State(Set<String> orillaIzquierda, Set<String> orillaDerecha, boolean balsaLadoIzquierod, State parent) {
            this.orillaIzquierda = orillaIzquierda;
            this.orillaDerecha = orillaDerecha;
            this.balsaLadoIzquierod = balsaLadoIzquierod;
            this.parent = parent;
        }

        boolean isValid() {
            if (orillaIzquierda.contains("Lobo Feroz") && orillaIzquierda.contains("Cabra") && !orillaIzquierda.contains("El Barquero")) {
                return false;
            }
            if (orillaDerecha.contains("Lobo Feroz") && orillaDerecha.contains("Cabra") && !orillaDerecha.contains("El Barquero")) {
                return false;
            }
            if (orillaIzquierda.contains("Cabra") && orillaIzquierda.contains("La col") && !orillaIzquierda.contains("El Barquero")) {
                return false;
            }
            if (orillaDerecha.contains("Cabra") && orillaDerecha.contains("La col") && !orillaDerecha.contains("El Barquero")) {
                return false;
            }

            return true;
        }

        boolean isGoal() {
            return orillaIzquierda.isEmpty();
        }

        List<examenProf.State> getSiguientes() {
            List<examenProf.State> siguientes = new ArrayList<>();
            // El bote sólo puede ser conducido por barquero
            String[] possibleDrivers = {"El Barquero"};

            Set<String> currentBank = balsaLadoIzquierod ? orillaIzquierda : orillaDerecha;
            Set<String> otherBank = balsaLadoIzquierod ? orillaDerecha : orillaIzquierda;

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
        private void addSiguiente(List<examenProf.State> siguientes, Set<String> currentBank, Set<String> otherBank, String driver, String passenger) {
            Set<String> nextLeftBank = new HashSet<>(orillaIzquierda);
            Set<String> nextRightBank = new HashSet<>(orillaDerecha);

            // Moviendo a los personajes
            if (balsaLadoIzquierod) {
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

            examenProf.State nextState = new examenProf.State(nextLeftBank, nextRightBank, !balsaLadoIzquierod, this);

            if (nextState.isValid()) {
                siguientes.add(nextState);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            examenProf.State state = (examenProf.State) o;
            return balsaLadoIzquierod == state.balsaLadoIzquierod &&
                    Objects.equals(orillaIzquierda, state.orillaIzquierda) &&
                    Objects.equals(orillaDerecha, state.orillaDerecha);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orillaIzquierda, orillaDerecha, balsaLadoIzquierod);
        }
    }

    public static void solve(Set<String> initialLeftBank, Set<String> initialRightBank, boolean initialBoatOnLeftBank) {
        State startState = new State(initialLeftBank, initialRightBank, initialBoatOnLeftBank, null);

        Stack<State> stack = new Stack<>();
        Set<State> visited = new HashSet<>();
        stack.push(startState);

        while (!stack.isEmpty()) {
            State currentState = stack.pop();

            // Print the current state (for debugging)
            // System.out.println(currentState);

            if (!currentState.isValid() || visited.contains(currentState)) {
                continue;
            }

            if (currentState.isGoal()) {
                printSolution(currentState);
                return;
            }

            visited.add(currentState);
            stack.addAll(currentState.getSiguientes());
        }

        System.out.println("No se encontró solución.");
    }

    private static void printSolution(examenProf.State solution) {
        if (solution == null) return;
        printSolution(solution.parent);
        System.out.println("Isla Izquierda: " + solution.orillaIzquierda + ",\nIsla Derecha: " + solution.orillaDerecha +
                ",\nBote en la izquierda: " + (solution.balsaLadoIzquierod? "Derecho" : "Izquierdo") );
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }

    public static void main(String[] args) {
        Set<String> orillaIzquierda = new HashSet<>(Arrays.asList("El Barquero", "Cabra", "Lobo Feroz", "La col"));
        Set<String> orillaDerecha = new HashSet<>(Arrays.asList());
        boolean balsaLadoIzquierod = true; // El bote al inicio está en el lado derecho

        solve(orillaIzquierda, orillaDerecha, balsaLadoIzquierod);
    }
}