package com.example.ui;

import com.example.model.Asignatura;
import com.example.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class VentanaGestionAsignaturas {

    private Scene scene;
    @SuppressWarnings("unused")
    private String username;

    private TableView<Asignatura> tablaAsignaturas;
    private Button btnAgregar, btnEditar, btnEliminar, btnRegresar;

    public VentanaGestionAsignaturas(Stage stage, String username) {
        this.username = username;

        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);

        /* La quité porqye no se ve bien, si encuentran una mejor, la usan.
        // Imagen de fondo (opcional)
        Image bgImage = new Image(getClass().getResourceAsStream("/images/fondo.jpg")); 
        BackgroundSize bgSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage bg = new BackgroundImage(
            bgImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            bgSize
        );
        root.setBackground(new Background(bg));
        root.setOpacity(0.9);
        */

        // Barra superior
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER_RIGHT);

        Button btnTareas = new Button("Gestionar Tareas");
        btnTareas.setOnAction(e -> {
            // Ir a Tareas
            VentanaGestionTareas v = new VentanaGestionTareas(stage, username);
            stage.setScene(v.getScene());
            stage.centerOnScreen();
        });

        Button btnAsignaturas = new Button("Gestionar Asignaturas");
        btnAsignaturas.setDisable(true);

        MenuItem miCerrarSesion = new MenuItem("Cerrar sesión");
        miCerrarSesion.setOnAction(e -> {
            VentanaLogin login = new VentanaLogin(stage);
            stage.setScene(login.getScene());
            stage.centerOnScreen();
        });
        MenuItem miSalir = new MenuItem("Salir");
        miSalir.setOnAction(e -> stage.close());

        MenuButton mbUsuario = new MenuButton(username);
        mbUsuario.getItems().addAll(miCerrarSesion, miSalir);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(btnTareas, btnAsignaturas, spacer, mbUsuario);
        root.setTop(topBar);

        // Grid central
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblNombre = new Label("Nombre de la Asignatura:");
        TextField txtNombre = new TextField();

        btnAgregar = new Button("Agregar");
        btnEditar = new Button("Editar");
        btnEliminar = new Button("Eliminar");
        btnRegresar = new Button("Regresar");

        // Tabla
        tablaAsignaturas = new TableView<>();
        TableColumn<Asignatura, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaAsignaturas.getColumns().add(colNombre);

        // Deshabilitar Editar/Eliminar
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);

        // Listener de selección
        tablaAsignaturas.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                txtNombre.setText(newV.getNombre());
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
            } else {
                txtNombre.clear();
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
            }
        });

        // Cargar datos
        cargarAsignaturas();

        // Acciones
        btnAgregar.setOnAction(e -> {
            agregarAsignatura(txtNombre);
        });
        btnEditar.setOnAction(e -> {
            editarAsignatura(txtNombre);
        });
        btnEliminar.setOnAction(e -> {
            eliminarAsignatura();
        });
        btnRegresar.setOnAction(e -> {
            VentanaPrincipal vp = new VentanaPrincipal(stage, username);
            stage.setScene(vp.getScene());
            stage.centerOnScreen();
        });

        // Ubicar en grid
        grid.add(lblNombre, 0, 0);
        grid.add(txtNombre, 1, 0);

        grid.add(btnAgregar, 0, 1);
        grid.add(btnEditar, 1, 1);
        grid.add(btnEliminar, 2, 1);

        grid.add(tablaAsignaturas, 0, 2, 3, 1);

        grid.add(btnRegresar, 0, 3);

        root.setCenter(grid);

        scene = new Scene(root, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }

    private void cargarAsignaturas() {
        ObservableList<Asignatura> lista = FXCollections.observableArrayList();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM asignaturas";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new Asignatura(rs.getInt("id"), rs.getString("nombre")));
            }
            tablaAsignaturas.setItems(lista);
        } catch (SQLException ex) {
            ex.printStackTrace();
            alertaError("Error", "No se pudieron cargar las asignaturas.\n" + ex.getMessage());
        }
    }

    private void agregarAsignatura(TextField txtNombre) {
        String nombre = txtNombre.getText();
        if (nombre.isEmpty()) {
            alertaAdvertencia("Asignaturas", "El nombre de la asignatura está vacío.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO asignaturas (nombre) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();

            alertaInfo("Asignaturas", "Asignatura agregada correctamente.");
            cargarAsignaturas();
            txtNombre.clear();
        } catch (SQLException ex) {
            ex.printStackTrace();
            alertaError("Error al agregar", "No se pudo agregar la asignatura.\n" + ex.getMessage());
        }
    }

    private void editarAsignatura(TextField txtNombre) {
        Asignatura asignaturaSeleccionada = tablaAsignaturas.getSelectionModel().getSelectedItem();
        if (asignaturaSeleccionada == null) {
            alertaAdvertencia("Asignaturas", "No has seleccionado ninguna asignatura para editar.");
            return;
        }

        String nombre = txtNombre.getText();
        if (nombre.isEmpty()) {
            alertaAdvertencia("Asignaturas", "El nombre de la asignatura está vacío.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE asignaturas SET nombre=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setInt(2, asignaturaSeleccionada.getId());
            pstmt.executeUpdate();

            alertaInfo("Asignaturas", "Asignatura editada correctamente.");
            cargarAsignaturas();
            txtNombre.clear();
        } catch (SQLException ex) {
            ex.printStackTrace();
            alertaError("Error al editar", "No se pudo editar la asignatura.\n" + ex.getMessage());
        }
    }

    private void eliminarAsignatura() {
        Asignatura asignaturaSeleccionada = tablaAsignaturas.getSelectionModel().getSelectedItem();
        if (asignaturaSeleccionada == null) {
            alertaAdvertencia("Asignaturas", "No has seleccionado ninguna asignatura para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Eliminar Asignatura");
        confirm.setHeaderText("¿Está seguro de eliminar la asignatura \"" + asignaturaSeleccionada.getNombre() + "\"?");
        confirm.setContentText("Esta acción no se puede deshacer.");
        var result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection conn = DBUtil.getConnection()) {
                String sql = "DELETE FROM asignaturas WHERE id=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, asignaturaSeleccionada.getId());
                pstmt.executeUpdate();

                alertaInfo("Asignaturas", "Asignatura eliminada correctamente.");
                cargarAsignaturas();
            } catch (SQLException ex) {
                ex.printStackTrace();
                alertaError("Error al eliminar", "No se pudo eliminar la asignatura.\n" + ex.getMessage());
            }
        }
    }

    // Alertas reutilizables
    private void alertaAdvertencia(String titulo, String contenido) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(contenido);
        a.showAndWait();
    }
    private void alertaInfo(String titulo, String contenido) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(contenido);
        a.showAndWait();
    }
    private void alertaError(String titulo, String contenido) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(contenido);
        a.showAndWait();
    }
}
