package com.tp.tp3;

import com.tp.tp3.Generador.Controladores.GeneradorAleatorios;
import com.tp.tp3.Graficador.Controladores.GestorGraficador;
import com.tp.tp3.Graficador.Pantallas.PantallaGraficador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GestorGraficador gestorGraficador = new GestorGraficador();
        gestorGraficador.graficarUniforme(10000,20,10,30,new PantallaGraficador());

    }

    public static void main(String[] args) {
        launch();
    }
}