package com.tp.tp3.Generador.Modelos;

public class Uniforme {

    public static double generarRND(double random, double desde, double hasta){
        return (desde + random*(hasta-desde));
    }
}
