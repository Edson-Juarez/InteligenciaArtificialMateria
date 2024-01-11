/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedNeuronal;

/**
 *
 * @author edson
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainRedNeuronal {

    public static void main(String[] args) {
        int tamanoEntrada = 4;
        int tamanoCapaOculta1 = 6;
        int tamanoCapaOculta2 = 4;
        int tamanoSalida = 3;

        RedNeuronal redNeuronal = new RedNeuronal(tamanoEntrada, tamanoCapaOculta1, tamanoCapaOculta2, tamanoSalida);

        List<double[]> entradas = new ArrayList<>();
        List<double[]> objetivos = new ArrayList<>();

        cargarDatosDesdeCSV("C:\\Users\\edson\\OneDrive\\Documentos\\NetBeansProjects\\Algoritmo a\\src\\RedNeuronal\\Iris.csv", entradas, objetivos, tamanoSalida);

        int iteraciones = 20000;
        double tasaAprendizaje = 0.0005;

        redNeuronal.entrenar(entradas, objetivos, iteraciones, tasaAprendizaje);
        

        evaluarRed(redNeuronal, entradas, objetivos);
    }

    private static void cargarDatosDesdeCSV(String archivoCSV, List<double[]> entradas, List<double[]> objetivos, int tamanoSalida) {
        String linea = "";
        String separadorCSV = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue; // Saltar la primera línea (encabezado)
                }

                String[] valores = linea.split(separadorCSV);

                double[] entrada = Arrays.stream(valores, 1, 5)
                        .mapToDouble(Double::parseDouble)
                        .toArray();

                double[] objetivo = new double[tamanoSalida];
                objetivo[Arrays.asList("Iris-setosa", "Iris-versicolor", "Iris-virginica").indexOf(valores[5])] = 1;

                entradas.add(entrada);
                objetivos.add(objetivo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void evaluarRed(RedNeuronal redNeuronal, List<double[]> entradas, List<double[]> objetivos) {
        int prediccionesCorrectas = 0;

        for (int i = (int) (entradas.size() * 0.7); i < entradas.size(); i++) {
            double[] entrada = entradas.get(i);
            double[] objetivo = objetivos.get(i);

            double[] prediccion = redNeuronal.predecir(entrada);

            int indicePredicho = encontrarIndiceMaximo(prediccion);
            int indiceObjetivo = encontrarIndiceMaximo(objetivo);

            if (indicePredicho == indiceObjetivo) {
                prediccionesCorrectas++;
            }
        }

        double precision = (double) prediccionesCorrectas / (entradas.size() - (int) (entradas.size() * 0.7));
        System.out.println("Precision: " + precision);

        // PRUEBA DE SABER QUE FLOR ES
        double[] nuevaEntrada = {6.7, 3.1, 5.6, 2.4};
        double[] nuevaPrediccion = redNeuronal.predecir(nuevaEntrada);

        // Mostrar la predicción
        System.out.println("Nueva Predicción:");
        mostrarPrediccion(nuevaPrediccion);
    }

    private static int encontrarIndiceMaximo(double[] array) {
        int indiceMaximo = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[indiceMaximo]) {
                indiceMaximo = i;
            }
        }
        return indiceMaximo;
    }

    private static void mostrarPrediccion(double[] prediccion) {
        for (double valor : prediccion) {
            System.out.print(valor + " ");
        }
        System.out.println();
    }
}
