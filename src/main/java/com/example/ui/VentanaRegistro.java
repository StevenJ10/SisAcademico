package com.example.ui;

import com.example.util.DBUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VentanaRegistro {

    private Scene scene;

    public VentanaRegistro(Stage stage) {
        Label lblUsuario = new Label("Usuario:");
        TextField txtUsuario = new TextField();

        Label lblPassword = new Label("Contraseña:");
        PasswordField txtPassword = new PasswordField();

        Label lblRepass = new Label("Confirmar contraseña:");
        PasswordField txtRepass = new PasswordField();

        Label lblNombre = new Label("Nombre completo:");
        TextField txtNombre = new TextField();

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();

        Button btnRegistrarse = new Button("Registrarse");
        Button btnCancelar = new Button("Cancelar");

        btnRegistrarse.setOnAction(e -> {
            String usuario = txtUsuario.getText();
            String pass = txtPassword.getText();
            String repass = txtRepass.getText();
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();

            // Validar campos
            if (usuario.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Registro", "Campos vacíos.", "Usuario y contraseña son obligatorios.");
                return;
            }
            if (!pass.equals(repass)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Registro", null, "Las contraseñas no coinciden.");
                return;
            }
            // Insertar en BD
            if (registrarUsuario(usuario, pass, nombre, email)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Registro", null, "Usuario registrado con éxito.");
                // Regresar a login
                VentanaLogin login = new VentanaLogin(stage);
                stage.setScene(login.getScene());
                stage.centerOnScreen();
            }
        });

        btnCancelar.setOnAction(e -> {
            // Regresar a login
            VentanaLogin login = new VentanaLogin(stage);
            stage.setScene(login.getScene());
            stage.centerOnScreen();
        });

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(lblUsuario, 0, 0);
        grid.add(txtUsuario, 1, 0);

        grid.add(lblPassword, 0, 1);
        grid.add(txtPassword, 1, 1);

        grid.add(lblRepass, 0, 2);
        grid.add(txtRepass, 1, 2);

        grid.add(lblNombre, 0, 3);
        grid.add(txtNombre, 1, 3);

        grid.add(lblEmail, 0, 4);
        grid.add(txtEmail, 1, 4);

        grid.add(btnRegistrarse, 0, 5);
        grid.add(btnCancelar, 1, 5);

        scene = new Scene(grid, 400, 300);

        String cssPath = getClass().getResource("/css/estilos.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

    }

    public Scene getScene() {
        return scene;
    }

    private boolean registrarUsuario(String usuario, String pass, String nombre, String email) {
        boolean exito = false;
        String sql = "INSERT INTO usuarios (username, password, nombre_completo, email) VALUES (?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, pass);
            pstmt.setString(3, nombre);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
            exito = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Registro", "Error al registrar", "No se pudo registrar el usuario.\n" + ex.getMessage());
        }
        return exito;
    }

    private void mostrarAlerta(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
