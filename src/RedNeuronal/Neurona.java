/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RedNeuronal;

/**
 *
 * @author edson
 */
import java.util.Random;

public class Neurona {

    private int numEntradas;
    private double[] pesos;
    private double bias;
    private double tasaAprendizaje;
    private double[] entradas;
    private double salida;
    private double error;

    public Neurona(int numEntradas, double tasaAprendizaje) {
        this.numEntradas = numEntradas;
        this.tasaAprendizaje = tasaAprendizaje;
        this.pesos = new double[numEntradas];
        this.bias = new Random().nextDouble(); // Inicializar bias aleatorio

        // Inicializar pesos aleatorios
        for (int i = 0; i < numEntradas; i++) {
            pesos[i] = new Random().nextDouble();
        }
    }

    public double calcularSalida(double[] entradas) {
        this.entradas = entradas;

        // Calcular la suma ponderada
        double sumaPonderada = 0;
        for (int i = 0; i < numEntradas; i++) {
            sumaPonderada += pesos[i] * entradas[i];
        }

        // Aplicar función de activación (por ejemplo, sigmoide)
        salida = 1 / (1 + Math.exp(-sumaPonderada + bias));

        return salida;
    }

    public void actualizarPesos() {
        for (int i = 0; i < numEntradas; i++) {
            double nuevoPeso = pesos[i] + tasaAprendizaje * error * entradas[i];
            setPeso(i, nuevoPeso);
        }
        bias += tasaAprendizaje * error; // Actualizar el bias
    }

    public void setPeso(int j, double nuevoPeso) {
        pesos[j] = nuevoPeso;
    }

    public void setBias(double nuevoBias) {
        bias = nuevoBias;
    }

    public double getSalida() {
        return salida;
    }


    public double calcularError(double[] errorSiguienteCapa) {
        double sumaError = 0;

        for (int i = 0; i < errorSiguienteCapa.length; i++) {
            sumaError += errorSiguienteCapa[i] * pesos[i];
        }

        error = sumaError * derivadaFuncionActivacion(salida);

        return error;
    }

    public void ajustarError(double[] entrada, double tasaAprendizaje) {
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] += tasaAprendizaje * error * entrada[i];
        }
        bias += tasaAprendizaje * error;
    }

    public double getError() {
        return error;
    }

    private double funcionActivacion(double x) {
        // Utilizando la función de activación sigmoide
        return 1 / (1 + Math.exp(-x));
    }

    private double derivadaFuncionActivacion(double x) {
        // Derivada de la función de activación sigmoide
        return x * (1 - x);
    }

    public double getPeso(int j) {
        return pesos[j];
    }
    
    public double getEntradas(int x){
        return entradas[x];
    }
}
