package com.example.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VentanaPrincipal {

    private Scene scene;
    @SuppressWarnings("unused")
    private String username; // Nombre de usuario recibido del login

    public VentanaPrincipal(Stage stage, String username) {
        this.username = username;

        // 1) Crear un BorderPane como contenedor principal
        BorderPane root = new BorderPane();

        // 2) Imagen de fondo (con opacidad reducida)
        // Aquí lo hacemos con BackgroundImage. Ajusta la ruta a tu imagen:
        Image bgImage = new Image(getClass().getResourceAsStream("/images/fondo.jpg"));
        //root.setOpacity(0.5);
        BackgroundSize bgSize = new BackgroundSize(
            100, 100, true, true, true, false
        );
        BackgroundImage bg = new BackgroundImage(
            bgImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            bgSize
        );
        // AÑADIMOS un color semitransparente encima (opcional) => un truco es usar un StackPane con 2 layers 
        // Aquí, para simplicidad, bajamos la opacidad de la imagen:
        

        root.setBackground(new Background(bg));

        // 3) Barra superior con:
        //    - Botón "Gestionar Tareas"
        //    - Botón "Gestionar Asignaturas"
        //    - Region que expanda
        //    - MenuButton con ícono usuario + username => "Cerrar Sesión" y "Salir"
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER_RIGHT);

        Button btnTareas = new Button("Gestionar Tareas");
        Button btnAsignaturas = new Button("Gestionar Asignaturas");

        // Seguiremos la indicación de "cuando el usuario presione, se sombree" => 
        // Podríamos cambiar el estilo (CSS) para el botón "activo" si se está en esa ventana
        // Este ejemplo no lo implementa a profundidad, pero se deja la base:
        btnTareas.setOnAction(e -> {
            VentanaGestionTareas ventTareas = new VentanaGestionTareas(stage, username);
            stage.setScene(ventTareas.getScene());
            stage.centerOnScreen();
        });

        btnAsignaturas.setOnAction(e -> {
            VentanaGestionAsignaturas ventAsign = new VentanaGestionAsignaturas(stage, username);
            stage.setScene(ventAsign.getScene());
            stage.centerOnScreen();
        });

        // MenuButton para usuario
        MenuItem miCerrarSesion = new MenuItem("Cerrar sesión");
        miCerrarSesion.setOnAction(e -> {
            // Volver al login
            VentanaLogin login = new VentanaLogin(stage);
            stage.setScene(login.getScene());
            stage.centerOnScreen();
        });

        MenuItem miSalir = new MenuItem("Salir");
        miSalir.setOnAction(e -> stage.close());

        MenuButton mbUsuario = new MenuButton(username); 
        // Podrías añadir un ícono: 
        // mbUsuario.setGraphic(new ImageView(...));
        mbUsuario.getItems().addAll(miCerrarSesion, miSalir);

        // Para empujar la barra a la derecha, agregamos un Region
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(btnTareas, btnAsignaturas, spacer, mbUsuario);

        root.setTop(topBar);

        // 4) Mensaje centrado en la parte central: 
        // "Bienvenido, <usuario>, aquí podrás gestionar..."
        Label lblMensaje = new Label(
            "Bienvenido " + username + ", aquí podrás gestionar tus tareas, " +
            "asignar sus prioridades y relacionarlas con tus asignaturas.\n\n" +
            "¡Organízate y lleva un control completo de tus actividades académicas!"
        );
        lblMensaje.setStyle("-fx-font-size: 18px; -fx-text-fill: black; " +
            "-fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 0.5, 0, 1, 1);");
        lblMensaje.setWrapText(true);

        // Centramos
        StackPane centerPane = new StackPane(lblMensaje);
        centerPane.setAlignment(Pos.CENTER);
        // Opcional: un poco de padding
        centerPane.setPadding(new Insets(40));

        root.setCenter(centerPane);

        scene = new Scene(root, 800, 600);
        String cssPath = getClass().getResource("/css/estilos.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

    }

    public Scene getScene() {
        return scene;
    }
}
