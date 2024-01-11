/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedNeuronal;

import java.util.Random;

/**
 *
 * @author edson
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Capa {

     private List<Neurona> neuronas;
    private double[] salidasCapa;
    private double[] entradasCapa;
    private double[] erroresCapa;
    private double tasaAprendizaje;

    public Capa(int numNeuronas, int numEntradas, double tasaAprendizaje) {
        this.tasaAprendizaje = tasaAprendizaje;
        this.neuronas = new ArrayList<>();
        this.salidasCapa = new double[numNeuronas];
        this.entradasCapa = new double[numEntradas];
        this.erroresCapa = new double[numNeuronas];

        // Crear neuronas
        for (int i = 0; i < numNeuronas; i++) {
            neuronas.add(new Neurona(numEntradas, tasaAprendizaje));
        }
    }

    public double[] calcularSalidas(double[] entradas) {
        this.entradasCapa = entradas;

        for (int i = 0; i < neuronas.size(); i++) {
            salidasCapa[i] = neuronas.get(i).calcularSalida(entradas);
        }

        return salidasCapa;
    }

    public void calcularErrores(double[] errores) {
        this.erroresCapa = errores;

        for (int i = 0; i < neuronas.size(); i++) {
            double error = 0;
            for (int j = 0; j < errores.length; j++) {
                error += errores[j] * neuronas.get(i).getPeso(j);
            }
            erroresCapa[i] = error;
        }
    }

    public void actualizarPesos() {
        for (int i = 0; i < neuronas.size(); i++) {
            neuronas.get(i).actualizarPesos();
        }
    }

    public double[] getSalidasCapa() {
        return salidasCapa;
    }

    public void ajustarError(double[] entrada, double tasaAprendizaje) {
        for (Neurona neurona : neuronas) {
            neurona.ajustarError(entrada, tasaAprendizaje);
        }
    }

    public double[] getErrores() {
        double[] errores = new double[neuronas.size()];
        for (int i = 0; i < neuronas.size(); i++) {
            errores[i] = neuronas.get(i).getError();
        }
        return errores;
    }

    public Object getEntradas() {
        List<double[]> entradasNeuronas = new ArrayList<>();
        for (Neurona neurona : neuronas) {
            entradasNeuronas.add(neurona.getEntradas());
        }
        return entradasNeuronas.toArray();
    }

    public int getNumNeuronas() {
        return neuronas.size();
    }

    public double[][] getPesos() {
        int numNeuronas = neuronas.size();
        int numEntradas = neuronas.get(0).getPeso().length();

        double[][] pesos = new double[numNeuronas][numEntradas];

        for (int i = 0; i < numNeuronas; i++) {
            pesos[i] = neuronas.get(i).getPeso();
        }

        return pesos;
    }

    public double[] getSalida() {
        double[] salidaCapa = new double[neuronas.size()];
        for (int i = 0; i < neuronas.size(); i++) {
            salidaCapa[i] = neuronas.get(i).getSalida();
        }
        return salidaCapa;
    }
}
