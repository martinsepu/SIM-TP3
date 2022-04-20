package com.tp.tp3.Graficador.Modelos;

public class Uniforme implements IDistribucion{
    @Override
    public double calcularFrecuenciaEsperada(double lambda, double desviacion, double media, double a, double b, int n, int cantidadIntervalos) {
        return n/cantidadIntervalos;
    }
}