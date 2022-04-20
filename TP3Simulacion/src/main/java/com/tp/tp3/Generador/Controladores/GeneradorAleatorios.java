package com.tp.tp3.Generador.Controladores;

import com.tp.tp3.Generador.Modelos.*;

import java.util.ArrayList;
import java.util.HashMap;

public class  GeneradorAleatorios
{
    public HashMap<Integer,Double> generarExponencial(int n, double lambda)
    {
        HashMap<Integer,Double> lineas = new HashMap<Integer,Double>();
        for(var i=0;i<n;i++)
        {
            lineas.put(i,Exponencial.generarVariable(Math.random(),lambda));

        }
        return lineas;
    }
    public HashMap<Integer,Double> generarNormal(int n, double desviacion,double media)
    {
        HashMap<Integer,Double> lineas = new HashMap<Integer,Double>();
        for(var i=0;i<n;i+=2)
        {
            var RND1= Math.random();
            var RND2 = Math.random();

            lineas.put(i, Normal.generarN1(RND1,RND2,desviacion,media));
            lineas.put(i+1, Normal.generarN2(RND1,RND2,desviacion,media));

        }
        return lineas;
    }

    public HashMap<Integer,Double> generarNormalConvolucion(int n, double desviacion,double media)
    {
        HashMap<Integer,Double> lineas = new HashMap<Integer,Double>();
        for(var i=0;i<n;i++)
        {

            ArrayList<Double> randoms = new ArrayList<Double>();
            for (var j=0;j<12;j++)
            {
                randoms.add(Math.random());
            }
            lineas.put(i,Normal.generarRNDConvolucion(randoms,desviacion,media));

        }
        return lineas;
    }

    public HashMap<Integer,Integer> generarPoisson(int n, double lambda)
    {
        HashMap<Integer,Integer> lineas = new HashMap<Integer,Integer>();
        for(var i=0;i<n;i++)
        {
            lineas.put(i, (int) Poisson.generarRND(lambda));

        }
        return lineas;
    }

    public HashMap<Integer,Double> generarUniforme(int n,double desde, double hasta)
    {
        HashMap<Integer,Double> lineas = new HashMap<Integer,Double>();
        for(var i=0;i<n;i++)
        {
            lineas.put(i, Uniforme.generarRND(Math.random(),desde,hasta));

        }
        return lineas;
    }



}
