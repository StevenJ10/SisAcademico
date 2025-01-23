 package com.example.ui;

import com.example.util.DBUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaLogin {

    private Scene scene;

    public VentanaLogin(Stage stage) {
        // Label superior
        Label lblBienvenido = new Label("Bienvenido");
        lblBienvenido.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Imagen típica de login
        ImageView imgLogin = new ImageView();

        // Carga tu imagen de login
        Image image = new Image(
            getClass().getResourceAsStream("/images/login.png"),
            150,  // ancho deseado
            0,    // alto (0 => se calcula con preserveRatio)
            true, // preserveRatio
            true  // smooth
        );
        imgLogin.setImage(image);

        // Campos de usuario y contraseña
        Label lblUsuario = new Label("Usuario:");
        TextField txtUsuario = new TextField();

        Label lblPassword = new Label("Contraseña:");
        PasswordField txtPassword = new PasswordField();

        // Botones (y un Hyperlink)
        Button btnEntrar = new Button("Entrar");
        Button btnRegistrarse = new Button("Registrarse");
        Hyperlink linkOlvidePass = new Hyperlink("Olvidé mi contraseña");

        // Orden vertical para los botones
        VBox vboxBotones = new VBox(10, btnEntrar, btnRegistrarse, linkOlvidePass);
        vboxBotones.setAlignment(Pos.CENTER);

        // Acciones
        btnEntrar.setOnAction(e -> {
            String usuario = txtUsuario.getText();
            String pass = txtPassword.getText();
            if (validarCredenciales(usuario, pass)) {
                // Ir a VentanaPrincipal
                VentanaPrincipal ventana = new VentanaPrincipal(stage, usuario); 
                stage.setScene(ventana.getScene());
                stage.centerOnScreen();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login incorrecto");
                alert.setHeaderText(null);
                alert.setContentText("Usuario o contraseña inválidos.");
                alert.showAndWait();
            }
        });

        btnRegistrarse.setOnAction(e -> {
            // Abrir ventana de registro
            VentanaRegistro registro = new VentanaRegistro(stage);
            stage.setScene(registro.getScene());
            stage.centerOnScreen();
        });

        linkOlvidePass.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recuperar contraseña");
            alert.setHeaderText("Olvidé mi contraseña");
            alert.setContentText("Aquí iría la lógica de recuperación de contraseña.");
            alert.showAndWait();
        });

        // Contenedor principal
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Añadimos los nodos en orden
        root.getChildren().addAll(
            lblBienvenido,
            imgLogin,
            lblUsuario, txtUsuario,
            lblPassword, txtPassword,
            vboxBotones
        );

        // Creamos la escena
        scene = new Scene(root, 350, 450);

        // Cargar hoja de estilos (CSS)
        String cssPath = getClass().getResource("/css/estilos.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

        // Asignar la escena al Stage
        stage.setScene(scene);

        // **Agregamos el ícono** al Stage (por ejemplo, un icono llamado "icon.png")
        Image icono = new Image(getClass().getResourceAsStream("/images/icono.jfif"));
        stage.getIcons().add(icono);

        // Puedes setear el título de la ventana (opcional)
        stage.setTitle("Sistema Académico - Login");

        // (Opcional) Mostrar la ventana
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }

    private boolean validarCredenciales(String usuario, String pass) {
        boolean valido = false;
        String sql = "SELECT username FROM usuarios WHERE username=? AND password=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, pass);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    valido = true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return valido;
    }
}
