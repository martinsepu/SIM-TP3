package com.tp.tp3.Graficador.Modelos;

public class Exponencial implements IDistribucion {

    @Override
    public double calcularFrecuenciaEsperada(double lambda, double desviacion, double media, double a, double b,int n,int cantidadIntervalos) {
        double probabilidad=0.0, frecEsp;
        probabilidad=(1-Math.exp(-lambda*b))-(1-Math.exp(-lambda*a));
        frecEsp=probabilidad*n;
        return  frecEsp;
    }
}
