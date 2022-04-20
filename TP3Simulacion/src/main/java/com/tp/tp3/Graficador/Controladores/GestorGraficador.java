package com.tp.tp3.Graficador.Controladores;


import com.tp.tp3.Generador.Controladores.GeneradorAleatorios;
import com.tp.tp3.Generador.SingletonGenerador;
import com.tp.tp3.Graficador.Modelos.*;
import com.tp.tp3.Graficador.Pantallas.PantallaGraficador;

import java.util.ArrayList;
import java.util.HashMap;

public class GestorGraficador {
    private ArrayList<Intervalo> intervalos = new ArrayList<Intervalo>();
    private ArrayList<Intervalo> intervalosAgrupados = new ArrayList<Intervalo>();
    public void graficarExponencial(int n,int intervalos, double lambda, PantallaGraficador pantalla)
    {
        Exponencial exponencial = new Exponencial();
        this.generarIntervalos(n,intervalos, exponencial ,lambda,0,1,0,0);
        var numeros = SingletonGenerador
                .getSingletonGenerador(new GeneradorAleatorios())
                .getGeneradorAleatorios()
                .generarExponencial(n,lambda);
        this.contarFrecuenciaIntervalo(numeros);
        this.calcularChiCuadrado();

    }
    public void graficarPoisson(int n, int intervalos, double lambda, PantallaGraficador pantalla){
        Poisson poisson = new Poisson();
        var numeros = SingletonGenerador
                .getSingletonGenerador(new GeneradorAleatorios())
                .getGeneradorAleatorios()
                .generarPoisson(n,lambda);
        this.generarIntervalos(n,intervalos,poisson,lambda,0,1,0,0);
        this.calcularChiCuadrado();
    }
    public void graficarNormal(int n,int intervalos, double media,double desviacion, PantallaGraficador pantalla){
        Normal normal = new Normal();
        this.generarIntervalos(n,intervalos, normal ,-1,desviacion,media,0,0);
        var numeros = SingletonGenerador
                .getSingletonGenerador(new GeneradorAleatorios())
                .getGeneradorAleatorios()
                .generarNormal(n,desviacion,media);
        this.contarFrecuenciaIntervalo(numeros);
        this.calcularChiCuadrado();
    }
    public void graficarUniforme(int n,int intervalos, double desde, double hasta, PantallaGraficador pantalla){
        Uniforme uniforme = new Uniforme();
        this.generarIntervalos(n,intervalos, uniforme ,-1,0,1,desde,hasta);
        var numeros = SingletonGenerador
                .getSingletonGenerador(new GeneradorAleatorios())
                .getGeneradorAleatorios()
                .generarUniforme(n, desde, hasta);
        this.contarFrecuenciaIntervalo(numeros);
        this.calcularChiCuadrado();
    }

    private void agrupar(){

        ArrayList<Intervalo> paraAgrupar = new ArrayList<>();
        double sumaFrecEs=0,sumaFrecOb=0, desde=0,hasta=0;
        int ultimo=0, cant=0;
        //comienza a rrecorrer todos los intervalos
        for(int i=0;i<this.intervalos.size();i++){
            if (this.intervalos.get(i).getFrecuenciaEsperada()<5){
                //si es mnor a 5 y no tinee ninguno para agrupar lo agrega al arreglo axiliar (paraAgrupar)
                if(paraAgrupar.isEmpty()){
                    paraAgrupar.add(this.intervalos.get(i));
                    sumaFrecEs = sumaFrecEs + this.intervalos.get(i).getFrecuenciaEsperada();
                    sumaFrecOb = sumaFrecOb + this.intervalos.get(i).getFrecuenciaObservada();
                    desde=this.intervalos.get(i).getDesde();
                    ultimo=i-1;
                }
                else {
                    sumaFrecEs = sumaFrecEs + this.intervalos.get(i).getFrecuenciaEsperada();
                    sumaFrecOb = sumaFrecOb + this.intervalos.get(i).getFrecuenciaObservada();
                    //si tiene otros para argrupar y la suma total de estos mas el nuevo es 5 crea un nuevo intervalo lo agrtega al arreglo final de intervalos
                    //y limpia las variables auxiliares
                    if (sumaFrecEs>5){
                        hasta=this.intervalos.get(i).getHasta();
                        Intervalo intervaloAgrupado = new Intervalo(desde,hasta,sumaFrecEs,sumaFrecOb);
                        this.intervalosAgrupados.add(intervaloAgrupado);
                        paraAgrupar.clear();
                        sumaFrecEs=0;
                        sumaFrecOb=0;
                        desde=0;
                        hasta=0;
                        cant+=1;
                    }
                    //si no supera los 5 lo agrega al arreglo auxiliar y continua
                    else {
                        paraAgrupar.add(this.intervalos.get(i));
                        hasta=this.intervalos.get(i).getHasta();
                    }
                }
            }
            //si la frecuencia esperada es mayor  a 5 lo agrega
            else {
                this.intervalosAgrupados.add(this.intervalos.get(i));
                cant+=1;
            }
        }
        //al terminar el recorrido verifica si tiene intervalos dentro del arreglo auxiliar, si los tiene los suma al ultimo intervalo
        //que se agrego pisandolo en el arreglo
        if (!paraAgrupar.isEmpty()){
            desde=this.intervalos.get(ultimo).getDesde();
            sumaFrecEs = sumaFrecEs + this.intervalos.get(ultimo).getFrecuenciaEsperada();
            sumaFrecOb = sumaFrecOb + this.intervalos.get(ultimo).getFrecuenciaObservada();
            Intervalo intervaloAgrupado = new Intervalo(desde,hasta,sumaFrecEs,sumaFrecOb);
            this.intervalosAgrupados.add(cant-1,intervaloAgrupado);
            paraAgrupar.clear();
            sumaFrecEs=0;
            sumaFrecOb=0;
            desde=0;
            hasta=0;
            cant+=1;
        }
    }

    private boolean calcularChiCuadrado() {
        double cAcumulado = 0.0;
        boolean rechazar = true;
        //agrupa los intervalos para poder calcular el chi
        this.agrupar();
        for(var i=0;i<this.intervalosAgrupados.size();i++){
            var fo = this.intervalosAgrupados.get(i).getFrecuenciaObservada();
            var fe = this.intervalosAgrupados.get(i).getFrecuenciaEsperada();
            var c = (double) Math.pow((fo-fe),2) / (fe);
            cAcumulado += c;
            this.intervalosAgrupados.get(i).setC(c);
            this.intervalosAgrupados.get(i).setcAcumulado(cAcumulado);
        }


        if (cAcumulado < 67.5){
            rechazar=false;
        }

        return rechazar;
    }


    public void contarFrecuenciaIntervalo(HashMap<Integer,Double> numeros){
        for (var i=0;i<numeros.size();i++)
        {
            Double RND = numeros.get(i);
            for (Intervalo intervalo: intervalos)
            {
                if(RND >= intervalo.getDesde() && RND < intervalo.getHasta())
                {
                    intervalo.setFrecuenciaObservada((int) (intervalo.getFrecuenciaObservada()+1));
                }
            }
        }
    }
    public void contarFrecuenciaIntervaloPoisson(HashMap<Integer,Integer> numeros){
        for (var i=0;i<numeros.size();i++)
        {
            Integer RND = numeros.get(i);
            for (Intervalo intervalo: intervalos)
            {
                if(RND >= intervalo.getDesde() && RND < intervalo.getHasta())
                {
                    intervalo.setFrecuenciaObservada((int) (intervalo.getFrecuenciaObservada()+1));
                }
            }
        }
    }
    public void generarIntervalos(int n, int cantidadIntervalos, IDistribucion distribucion,double lambda,double desviacion,double media,double desdeUni, double hastaUni ){
        this.intervalos = new ArrayList<Intervalo>();
        double desde = 0;
        double tamaño = ((double) (media / (double) cantidadIntervalos) );
        double hasta= tamaño;
        if(distribucion instanceof Uniforme){
            desde= desdeUni;
            tamaño=((double) (hastaUni-desdeUni)/(double) cantidadIntervalos);
            hasta= desde+ tamaño;
        }
        for(int i=0;i<cantidadIntervalos;i++)
        {

            double frecuenciaEsperada = distribucion.calcularFrecuenciaEsperada( lambda,desviacion , media, desde, hasta,n,cantidadIntervalos);
            Intervalo intervalo = new Intervalo(desde,hasta - 0.001,frecuenciaEsperada);

            desde = hasta;
            hasta += tamaño;
            this.intervalos.add(intervalo);
        }
    }


}
