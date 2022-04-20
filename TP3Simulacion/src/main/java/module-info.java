module com.example.tp3simulacion {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tp.tp3.Graficador to javafx.fxml;

    exports com.tp.tp3;

}