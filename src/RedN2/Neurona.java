/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedN2;

/**
 *
 * @author edson
 */
import java.util.Random;

public class Neurona {

    double[] pesos;
    private double sesgo;

    public Neurona(int nEntradas) {
        Random rand = new Random();
        pesos = new double[nEntradas];
        System.out.println("Entradas "+nEntradas);
        for (int i = 0; i < nEntradas; i++) {
            pesos[i] = Math.random();
        }
        sesgo = Math.random();
    }

    public double funcionActivacion(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public double derivadaFuncionActivacion(double x) {
        return funcionActivacion(x) * (1 - funcionActivacion(x));
    }

    public double calcularSalida(double[] entradas) {
        double sumaPonderada = sesgo;
        for (int i = 0; i < pesos.length; i++) {
            sumaPonderada += pesos[i] * entradas[i];
        }
        System.out.println("Entradas tamanio "+entradas.length+" \nPesos tamanio "+pesos.length);
        if (entradas.length != pesos.length) {
            throw new IllegalArgumentException("El tamaño de las entradas debe ser igual al tamaño de los pesos");
        }
        return funcionActivacion(sumaPonderada);
    }

    public void actualizarPesos(double[] entradas, double error, double tasaAprendizaje) {
        if (entradas.length != pesos.length) {
            throw new IllegalArgumentException("El tamaño de las entradas debe ser igual al tamaño de los pesos");
        }
        double derivada = derivadaFuncionActivacion(calcularSalida(entradas));
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] -= tasaAprendizaje * error * derivada * entradas[i];
        }
        sesgo -= tasaAprendizaje * error * derivada;
    }

}
