package RedN2;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.apache.commons.lang3.ArrayUtils;

public class Capa {

    private List<Integer> capas;
    private List<List<List<Double>>> pesos;
    private List<List<Double>> sesgos;
    private List<List<Double>> activaciones;

    private int iteracionActual;
    private DefaultCategoryDataset dataset;

    public Capa(List<Integer> capas) {
        this.capas = capas;
        this.pesos = inicializarPesos(capas);
        this.sesgos = inicializarSesgos(capas);
        this.activaciones = new ArrayList<>();
        for (int i = 0; i < capas.size(); i++) {
            this.activaciones.add(new ArrayList<>());
        }
        dataset = new DefaultCategoryDataset();
        iteracionActual = 1;
    }

    private List<List<List<Double>>> inicializarPesos(List<Integer> capas) {
        Random random = new Random();
        List<List<List<Double>>> pesos = new ArrayList<>();

        for (int i = 1; i < capas.size(); i++) {
            int filas = capas.get(i);
            int columnas = capas.get(i - 1);

            List<List<Double>> capaPesos = new ArrayList<>();
            for (int j = 0; j
                    < filas; j++) {
                List<Double> filaPesos = new ArrayList<>();
                for (int k = 0;
                        k < columnas; k++) {
                    filaPesos.add(random.nextDouble());
                }
                capaPesos.add(filaPesos);
            }
            pesos.add(capaPesos);
        }

        return pesos;
    }

    private List<List<Double>> inicializarSesgos(List<Integer> capas) {
        Random random = new Random();
        List<List<Double>> sesgos = new ArrayList<>();

        for (int i = 1; i < capas.size(); i++) {
            int tamano = capas.get(i);

            List<Double> capaSesgos = new ArrayList<>();
            for (int j = 0; j < tamano; j++) {
                capaSesgos.add(random.nextDouble());
            }
            sesgos.add(capaSesgos);
        }

        return sesgos;
    }

    private double sigmoidal(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private void propagacionAdelante(double[] entrada) {
        activaciones.get(0).clear();
        //activaciones.get(0).addAll(Arrays.asList(ArrayUtils.toObject(entrada)));

        for (int i = 0; i < capas.size() - 1; i++) {
            List<Double> salidaCapa = new ArrayList<>();
            for (int j = 0; j < capas.get(i + 1); j++) {
                double suma = 0;
                for (int k = 0; k < capas.get(i); k++) {
                    suma += activaciones.get(i).get(k) * pesos.get(i).get(j).get(k);
                }
                suma += sesgos.get(i).get(j);
                salidaCapa.add(sigmoidal(suma));
            }
            activaciones.get(i + 1).clear();
            activaciones.get(i + 1).addAll(salidaCapa);
        }
    }

    private void retropropagacion(double[] objetivo, double tasaAprendizaje) {
        List<List<Double>> deltas = new ArrayList<>();

        // Calcular deltas de la capa de salida
        List<Double> erroresSalida = new ArrayList<>();
        List<Double> deltasSalida = new ArrayList<>();
        for (int j = 0; j < capas.get(capas.size() - 1); j++) {
            double error = objetivo[j] - activaciones.get(activaciones.size() - 1).get(j);
            erroresSalida.add(error);
            deltasSalida.add(error * (activaciones.get(activaciones.size() - 1).get(j) * (1 - activaciones.get(activaciones.size() - 1).get(j))));
        }
        deltas.add(deltasSalida);

        // Calcular deltas de las capas ocultas
        for (int i = capas.size() - 2; i > 0; i--) {
            List<Double> erroresCapa = new ArrayList<>();
            List<Double> deltasCapa = new ArrayList<>();
            for (int j = 0; j < capas.get(i); j++) {
                double error = 0;
                for (int k = 0; k < capas.get(i + 1); k++) {
                    error += deltas.get(deltas.size() - 1).get(k) * pesos.get(i).get(k).get(j);
                }
                erroresCapa.add(error);
                deltasCapa.add(error * (activaciones.get(i).get(j) * (1 - activaciones.get(i).get(j))));
            }
            deltas.add(deltasCapa);
        }

        // Actualizar sesgos
        for (int i = 0; i < sesgos.size(); i++) {
            for (int j = 0; j < sesgos.get(i).size(); j++) {
                sesgos.get(i).set(j, sesgos.get(i).get(j) + tasaAprendizaje * deltas.get(deltas.size() - 1 - i).get(j));
            }
        }

        // Actualizar pesos
        for (int i = 0; i < pesos.size(); i++) {
            for (int j = 0; j < pesos.get(i).size(); j++) {
                for (int k = 0; k < pesos.get(i).get(j).size(); k++) {
                    pesos.get(i).get(j).set(k, pesos.get(i).get(j).get(k) + tasaAprendizaje * deltas.get(deltas.size() - 1 - i).get(j) * activaciones.get(i).get(k));
                }
            }
        }
    }

    private void entrenar(List<double[]> entradas, List<double[]> objetivos, int iteraciones, double tasaAprendizaje) {
        crearGrafico();
        for (int epoca = 0; epoca < iteraciones; epoca++) {
            double errorTotal = 0;

            for (int i = 0; i < entradas.size(); i++) {
                double[] entrada = entradas.get(i);
                double[] objetivo = objetivos.get(i);

                propagacionAdelante(entrada);
                retropropagacion(objetivo, tasaAprendizaje);

                // Calcular error total
                for (int j = 0; j < objetivo.length; j++) {
                    errorTotal += Math.pow(objetivo[j] - activaciones.get(activaciones.size() - 1).get(j), 2);
                }
            }

            // Agregar datos al conjunto de datos y actualizar la gráfica
            actualizarGrafico(errorTotal);
            // Imprimir el error total para cada iteración
            System.out.println("Iteracion " + (epoca + 1) + " - Error Total: " + errorTotal);
        }
    }

    public static void main(String[] args) {
        List<Integer> capas = Arrays.asList(4, 5, 4, 3);

        Capa redNeuronal = new Capa(capas);

        List<double[]> entradas = new ArrayList<>();
        List<double[]> objetivos = new ArrayList<>();

        // Cargar datos desde CSV
        String archivoCSV = "C:\\Users\\edson\\OneDrive\\Documentos\\NetBeansProjects\\Algoritmo a\\src\\RedNeuronal\\Iris.csv";

        String linea = "";
        String separadorCSV = ",";

        try ( BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
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

                double[] objetivo = new double[capas.get(capas.size() - 1)];
                objetivo[Arrays.asList("Iris-setosa", "Iris-versicolor", "Iris-virginica").indexOf(valores[5])] = 1;

                entradas.add(entrada);
                objetivos.add(objetivo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Entrenar la red neuronal
        int iteraciones = 50000;
        double tasaAprendizaje = 0.1;

        redNeuronal.entrenar(entradas, objetivos, iteraciones, tasaAprendizaje);

        // Probar la red entrenada
        int prediccionesCorrectas = 0;

        for (int i = (int) (entradas.size() * 0.7); i < entradas.size(); i++) {
            double[] entrada = entradas.get(i);
            double[] objetivo
                    = objetivos.get(i);

            redNeuronal.propagacionAdelante(entrada);

            List<Double> activacionesCapaSalida
                    = redNeuronal.activaciones.get(redNeuronal.activaciones.size() - 1);
            double[] activacionesArray = new double[activacionesCapaSalida.size()];

            for (int j = 0; j < activacionesCapaSalida.size(); j++) {
                activacionesArray[j] = activacionesCapaSalida.get(j);
            }

            int indicePredicho
                    = redNeuronal.encontrarIndiceMaximo(activacionesArray);
            int indiceObjetivo = redNeuronal.encontrarIndiceMaximo(objetivo);

            if (indicePredicho == indiceObjetivo) {
                prediccionesCorrectas++;
            }
        }

//PRUEBA DE SABER QUÉ FLOR ES
        double[] nuevaEntrada = {6.7, 3.1, 5.6, 2.4};

        redNeuronal.propagacionAdelante(nuevaEntrada);

        List<Double> activacionesCapaSalida = redNeuronal.activaciones.get(redNeuronal.activaciones.size() - 1);
        double[] activacionesArray = new double[activacionesCapaSalida.size()];

        for (int j = 0; j < activacionesCapaSalida.size(); j++) {
            activacionesArray[j] = activacionesCapaSalida.get(j);
        }

// Mostrar la predicción
        System.out.println("Nueva Predicción:");
        redNeuronal.mostrarPrediccion(activacionesArray);

        double precision = (double) prediccionesCorrectas / (entradas.size() - (int) (entradas.size() * 0.7));
        System.out.println("Precisión: " + precision);

    }

    private void crearGrafico() {
        // Crear el gráfico
        JFreeChart chart = ChartFactory.createLineChart(
                "Error Total por Iteración",
                "Iteración",
                "Error Total",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        // Crear el panel de gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 800));

        // Agregar el panel de gráfico al JFrame
        JFrame frame = new JFrame("Gráfico de Error Total");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void actualizarGrafico(double errorTotal) {
        SwingUtilities.invokeLater(() -> {
            dataset.addValue(errorTotal, "Error", String.valueOf(iteracionActual));
            iteracionActual++;
        });
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
        String[] clases = {"Iris-setosa", "Iris-versicolor", "Iris-virginica"};

        int indicePredicho = encontrarIndiceMaximo(prediccion);
        System.out.println("Clase Predicha: " + clases[indicePredicho]);
    }
}
