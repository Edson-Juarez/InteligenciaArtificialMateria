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

public class puzzleIslas {

    public static void main(String[] args) {
        String[] isla1 = {"Mama", "Papa", "Hijo1", "Hijo2", "Hija1", "Hija2", "Policia", "Ladron"};
        String[] isla2 = {};

        Stack<Estado> pila = new Stack<>();
        Set<Estado> visitados = new HashSet<>();
        pila.push(new Estado(isla1, isla2, 0));

        while (!pila.isEmpty()) {
            Estado estadoActual = pila.pop();
            estadoActual.imprimirPaso();
            visitados.add(estadoActual);

            if (estadoActual.esSolucion()) {
                System.out.println("Solución encontrada!");
                break;
            }

            List<Estado> siguientesEstados = estadoActual.generarSiguientesEstados();
            for (Estado siguienteEstado : siguientesEstados) {
                if (!visitados.contains(siguienteEstado)) {
                    pila.push(siguienteEstado);
                }
            }
        }
    }

    static class Estado {
        String[] isla1;
        String[] isla2;
        int paso;

        public Estado(String[] isla1, String[] isla2, int paso) {
            this.isla1 = isla1.clone();
            this.isla2 = isla2.clone();
            this.paso = paso;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Estado estado = (Estado) obj;
            Arrays.sort(isla1);
            Arrays.sort(estado.isla1);
            Arrays.sort(isla2);
            Arrays.sort(estado.isla2);
            return Arrays.equals(isla1, estado.isla1) &&
                   Arrays.equals(isla2, estado.isla2);
        }

        @Override
        public int hashCode() {
            Arrays.sort(isla1);
            Arrays.sort(isla2);
            int result = Arrays.hashCode(isla1);
            result = 31 * result + Arrays.hashCode(isla2);
            return result;
        }

        public void imprimirPaso() {
            System.out.println("Paso " + paso + " " + Arrays.toString(isla1) + " ---- " + Arrays.toString(isla2));
        }

        public boolean esSolucion() {
            return isla1.length == 0 && isla2.length == 8;
        }

        public List<Estado> generarSiguientesEstados() {
            List<Estado> siguientesEstados = new ArrayList<>();

            // Movimiento de isla1 a isla2
            for (int i = 0; i < isla1.length; i++) {
                for (int j = i + 1; j < isla1.length; j++) {
                    String persona1 = isla1[i];
                    String persona2 = isla1[j];

                    // Verifica que solo personas autorizadas manejen el barco
                    if (esManejarBarco(persona1) || esManejarBarco(persona2)) {
                        String[] nuevaIsla1 = quitarPersonas(isla1, persona1, persona2);
                        String[] nuevaIsla2 = agregarPersonas(isla2, persona1, persona2);
                        if (esMovimientoValido(nuevaIsla1, nuevaIsla2)) {
                            siguientesEstados.add(new Estado(nuevaIsla1, nuevaIsla2, paso + 1));
                        }
                    }
                }
            }

            // Movimiento de isla2 a isla1
            for (int i = 0; i < isla2.length; i++) {
                for (int j = i + 1; j < isla2.length; j++) {
                    String persona1 = isla2[i];
                    String persona2 = isla2[j];

                    // Verifica que solo personas autorizadas manejen el barco
                    if (esManejarBarco(persona1) || esManejarBarco(persona2)) {
                        String[] nuevaIsla1 = agregarPersonas(isla1, persona1, persona2);
                        String[] nuevaIsla2 = quitarPersonas(isla2, persona1, persona2);
                        if (esMovimientoValido(nuevaIsla1, nuevaIsla2)) {
                            siguientesEstados.add(new Estado(nuevaIsla1, nuevaIsla2, paso + 1));
                        }
                    }
                }
            }

            return siguientesEstados;
        }

        private boolean esManejarBarco(String persona) {
            return persona.equals("Papa") || persona.equals("Mama") || persona.equals("Policia");
        }

        private String[] quitarPersonas(String[] isla, String... personas) {
            List<String> lista = new ArrayList<>(Arrays.asList(isla));
            for (String persona : personas) {
                lista.remove(persona);
            }
            return lista.toArray(new String[0]);
        }

        private String[] agregarPersonas(String[] isla, String... personas) {
            List<String> lista = new ArrayList<>(Arrays.asList(isla));
            lista.addAll(Arrays.asList(personas));
            return lista.toArray(new String[0]);
        }

        private boolean esMovimientoValido(String[] isla1, String[] isla2) {
            // Restricción 1: El ladrón no puede quedarse con algún miembro de la familia sin la presencia del policía en la misma isla
            if (contienePersona(isla2, "Ladron") && (contienePersona(isla2, "Papa") || contienePersona(isla2, "Mama") ||
                    contienePersona(isla2, "Hijo1") || contienePersona(isla2, "Hijo2") ||
                    contienePersona(isla2, "Hija1") || contienePersona(isla2, "Hija2")) &&
                    !contienePersona(isla2, "Policia")) {
                return false;
            }

            // Restricción 2: Las hijas no pueden estar con el padre, sin que la madre esté presente
            if (contienePersona(isla2, "Papa") && (contienePersona(isla2, "Hija1") || contienePersona(isla2, "Hija2")) &&
                    !contienePersona(isla2, "Mama")) {
                return false;
            }

            // Restricción 3: Los hijos no pueden estar con la madre sin que el padre esté presente
            if (contienePersona(isla2, "Mama") && (contienePersona(isla2, "Hijo1") || contienePersona(isla2, "Hijo2")) &&
                    !contienePersona(isla2, "Papa")) {
                return false;
            }

            return true; // Devuelve true si todas las restricciones se cumplen
        }

        private boolean contienePersona(String[] isla, String persona) {
            for (String individuo : isla) {
                if (individuo.equals(persona)) {
                    return true;
                }
            }
            return false;
        }
    }
}
