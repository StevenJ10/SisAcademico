package com.example;

import com.example.ui.VentanaLogin;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {  // <--- EXTENDER APPLICATION

    @Override
    public void start(Stage primaryStage) {
        VentanaLogin login = new VentanaLogin(primaryStage);
        primaryStage.setTitle("Sistema Académico - Login");
        primaryStage.setScene(login.getScene());
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        // "launch" viene de Application
        launch(args);
    }
}
