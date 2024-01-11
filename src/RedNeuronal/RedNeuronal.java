/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedNeuronal;

/**
 *
 * @author edson
 */
import java.util.ArrayList;
import java.util.List;

public class RedNeuronal {

    private List<Capa> capas;

    public RedNeuronal(int tamanoEntrada, int tamanoCapaOculta1, int tamanoCapaOculta2, int tamanoSalida) {
        capas = new ArrayList<>();
        capas.add(new Capa(tamanoEntrada, tamanoCapaOculta1));
        capas.add(new Capa(tamanoCapaOculta1, tamanoCapaOculta2));
        capas.add(new Capa(tamanoCapaOculta2, tamanoSalida));
    }

    public void entrenar(List<double[]> entradas, List<double[]> objetivos, int iteraciones, double tasaAprendizaje) {
        for (int i = 0; i < iteraciones; i++) {
            for (int j = 0; j < entradas.size(); j++) {
                double[] entrada = entradas.get(j);
                double[] objetivo = objetivos.get(j);

                propagacionAdelante(entrada);
                retropropagacion(objetivo,tasaAprendizaje);
                actualizarPesos();
            }
        }
    }

    private double[] propagacionAdelante(double[] entrada) {
        double[] salida = entrada;

        for (Capa capa : capas) {
            salida = capa.calcularSalida(salida);
        }

        return salida;
    }

    private void retropropagacion(double[] objetivo, double tasaAprendizaje) {
        double[] error = objetivo;

        for (int i = capas.size() - 1; i >= 0; i--) {
            error = capas.get(i).calcularError(error);
            capas.get(i).ajustarError((i > 0) ? capas.get(i - 1).getErrores() : null, tasaAprendizaje);
        }
    }

    private double[] calcularErrorCapaAnterior(Capa capa, double[] errorCapaActual) {
        double[] errorCapaAnterior = new double[capa.getEntradas().length];

        for (int i = 0; i < capa.getEntradas().length; i++) {
            double suma = 0;

            for (int j = 0; j < capa.getNumNeuronas(); j++) {
                suma += errorCapaActual[j] * capa.getPesos()[j][i];
            }

            errorCapaAnterior[i] = suma;
        }

        return errorCapaAnterior;
    }

    private void actualizarPesos() {
        for (Capa capa : capas) {
            capa.actualizarPesos();
        }
    }

    public double[] predecir(double[] entrada) {
        propagacionAdelante(entrada);
        return capas.get(capas.size() - 1).getSalida();
    }
}
