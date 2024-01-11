/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedN2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author edson
 */
public class Main {
    public static void main(String[] args) {
        // Definimos la topología de la red
        int[] topologia = {4, 8, 4, 3};
        Red red = new Red(topologia);

        // Definimos el número de iteraciones y la tasa de aprendizaje
        int nIteraciones = 10000;
        double tasaAprendizaje = 0.1;

        // Leemos los datos de entrada desde un archivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\edson\\OneDrive\\Documentos\\NetBeansProjects\\Algoritmo a\\src\\RedNeuronal\\Iris.csv"))) {
            // Ignoramos la primera línea del archivo CSV
            br.readLine();

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                double[] entradas = Arrays.stream(valores, 2, 5).mapToDouble(Double::parseDouble).toArray();
                double[] objetivos = new double[3];
                switch (valores[5]) {
                    case "Iris-setosa":
                        objetivos[0] = 1;
                        break;
                    case "Iris-versicolor":
                        objetivos[1] = 1;
                        break;
                    case "Iris-virginica":
                        objetivos[2] = 1;
                        break;
                }

                // Realizamos el número especificado de iteraciones
                for (int i = 0; i < nIteraciones; i++) {
                    // Propagamos las entradas a través de la red
                    double[] salidas = red.propagarAdelante(entradas);
                    System.out.println("Iteración " + (i + 1) + ", Salidas: " + Arrays.toString(salidas));

                    // Propagamos los errores a través de la red y actualizamos los pesos
                    red.propagarAtras(entradas, objetivos, tasaAprendizaje);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
