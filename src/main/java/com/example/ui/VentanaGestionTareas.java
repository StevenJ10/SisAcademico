package com.example.ui;

import com.example.model.Asignatura;
import com.example.model.Tarea;
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
import java.time.LocalDate;

public class VentanaGestionTareas {

    private Scene scene;
    @SuppressWarnings("unused")
    private String username; // Usuario que inició sesión

    private TableView<Tarea> tablaTareas;
    private ComboBox<Asignatura> comboAsignaturas;
    private ComboBox<String> comboPrioridad;

    // Botones
    private Button btnAgregar;
    private Button btnEditar;
    private Button btnEliminar;
    private Button btnRegresar;

    public VentanaGestionTareas(Stage stage, String username) {
        this.username = username;

        // Contenedor principal (BorderPane para usar top y center)
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);

        /* Usar con una mejor imagen, o no usar.
        // Imagen de fondo (opcional), ajusta la ruta si gustas
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

        // Barra superior (con 2 botones + Menu usuario), si gustas “Menú”
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER_RIGHT);

        Button btnTareas = new Button("Gestionar Tareas");
        btnTareas.setDisable(true); // Estamos en Tareas
        btnTareas.setOnAction(e -> { 
            /* ya estás aquí, no hace nada */
        });

        Button btnAsignaturas = new Button("Gestionar Asignaturas");
        btnAsignaturas.setOnAction(e -> {
            // Ir a la ventana de Asignaturas
            VentanaGestionAsignaturas v = new VentanaGestionAsignaturas(stage, username);
            stage.setScene(v.getScene());
            stage.centerOnScreen();
        });

        // Menu de usuario (Cerrar Sesión, Salir)
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

        // Spacer para empujar a la derecha
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(btnTareas, btnAsignaturas, spacer, mbUsuario);
        root.setTop(topBar);

        // Contenedor central con GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        // Campos
        Label lblTitulo = new Label("Título:");
        TextField txtTitulo = new TextField();

        Label lblDescripcion = new Label("Descripción:");
        TextArea txtDescripcion = new TextArea();
        txtDescripcion.setPrefRowCount(3);

        Label lblFecha = new Label("Fecha:");
        DatePicker dpFecha = new DatePicker();
        // Validar fecha no pasada
        dpFecha.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate hoy = LocalDate.now();
                setDisable(empty || date.isBefore(hoy));
            }
        });

        Label lblAsignatura = new Label("Asignatura:");
        comboAsignaturas = new ComboBox<>();

        Label lblPrioridad = new Label("Prioridad:");
        comboPrioridad = new ComboBox<>();
        comboPrioridad.getItems().addAll("Baja", "Media", "Alta");

        btnAgregar = new Button("Agregar");
        btnEditar = new Button("Editar");
        btnEliminar = new Button("Eliminar");
        btnRegresar = new Button("Regresar");

        // Tabla
        tablaTareas = new TableView<>();
        TableColumn<Tarea, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Tarea, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<Tarea, LocalDate> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<Tarea, String> colPrioridad = new TableColumn<>("Prioridad");
        colPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));

        // Añadir columnas sin varargs
        tablaTareas.getColumns().add(colTitulo);
        tablaTareas.getColumns().add(colDescripcion);
        tablaTareas.getColumns().add(colFecha);
        tablaTareas.getColumns().add(colPrioridad);

        // Deshabilitar Editar/Eliminar hasta seleccionar
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);

        // Listener para habilitar/deshabilitar y rellenar campos
        tablaTareas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Llenar campos
                txtTitulo.setText(newVal.getTitulo());
                txtDescripcion.setText(newVal.getDescripcion());
                dpFecha.setValue(newVal.getFecha());
                comboPrioridad.setValue(newVal.getPrioridad());
                // Buscar la asignatura
                for (Asignatura asig : comboAsignaturas.getItems()) {
                    if (asig.getId() == newVal.getAsignaturaId()) {
                        comboAsignaturas.setValue(asig);
                        break;
                    }
                }
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
            } else {
                // Limpiar campos
                txtTitulo.clear();
                txtDescripcion.clear();
                dpFecha.setValue(null);
                comboAsignaturas.setValue(null);
                comboPrioridad.setValue(null);

                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
            }
        });

        // Cargar combos y tabla
        cargarAsignaturas();
        cargarTareas();

        // Acciones
        btnAgregar.setOnAction(e -> {
            agregarTarea(txtTitulo, txtDescripcion, dpFecha);
        });
        btnEditar.setOnAction(e -> {
            editarTarea(txtTitulo, txtDescripcion, dpFecha);
        });
        btnEliminar.setOnAction(e -> {
            eliminarTarea();
        });
        btnRegresar.setOnAction(e -> {
            VentanaPrincipal vp = new VentanaPrincipal(stage, username);
            stage.setScene(vp.getScene());
            stage.centerOnScreen();
        });

        // Ubicar en grid
        grid.add(lblTitulo, 0, 0);
        grid.add(txtTitulo, 1, 0);

        grid.add(lblDescripcion, 0, 1);
        grid.add(txtDescripcion, 1, 1);

        grid.add(lblFecha, 0, 2);
        grid.add(dpFecha, 1, 2);

        grid.add(lblAsignatura, 0, 3);
        grid.add(comboAsignaturas, 1, 3);

        grid.add(lblPrioridad, 0, 4);
        grid.add(comboPrioridad, 1, 4);

        // Fila 5: Botones Agregar, Editar, Eliminar
        grid.add(btnAgregar, 0, 5);
        grid.add(btnEditar, 1, 5);
        grid.add(btnEliminar, 2, 5);

        // Fila 6: Tabla
        grid.add(tablaTareas, 0, 6, 3, 1);

        // Fila 7: Botón Regresar
        grid.add(btnRegresar, 0, 7);

        root.setCenter(grid);

        scene = new Scene(root, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }

    // Cargar Asignaturas al combo
    private void cargarAsignaturas() {
        ObservableList<Asignatura> lista = FXCollections.observableArrayList();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT id, nombre FROM asignaturas";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new Asignatura(rs.getInt("id"), rs.getString("nombre")));
            }
            comboAsignaturas.setItems(lista);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se pudieron cargar las asignaturas.\n" + ex.getMessage());
            alert.showAndWait();
        }
    }

    // Cargar Tareas en la tabla
    private void cargarTareas() {
        ObservableList<Tarea> lista = FXCollections.observableArrayList();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM tareas";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Tarea tarea = new Tarea(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getString("prioridad"),
                    rs.getInt("asignatura_id")
                );
                lista.add(tarea);
            }
            tablaTareas.setItems(lista);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se pudieron cargar las tareas.\n" + ex.getMessage());
            alert.showAndWait();
        }
    }

    // Agregar Tarea (validaciones locales)
    private void agregarTarea(TextField txtTitulo, TextArea txtDescripcion, DatePicker dpFecha) {
        Asignatura asignaturaSeleccionada = comboAsignaturas.getValue();
        if (asignaturaSeleccionada == null) {
            alertaAdvertencia("Agregar Tarea", "No se ha seleccionado asignatura.");
            return;
        }

        String titulo = txtTitulo.getText();
        if (titulo.isEmpty()) {
            alertaAdvertencia("Agregar Tarea", "El título está vacío.");
            return;
        }
        LocalDate fecha = dpFecha.getValue();
        if (fecha == null || fecha.isBefore(LocalDate.now())) {
            alertaAdvertencia("Agregar Tarea", "La fecha es inválida (pasada o vacía).");
            return;
        }
        String prioridad = comboPrioridad.getValue();
        if (prioridad == null) {
            alertaAdvertencia("Agregar Tarea", "No se ha seleccionado la prioridad.");
            return;
        }
        String descripcion = txtDescripcion.getText();

        // Inserción en BD
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO tareas (titulo, descripcion, fecha, prioridad, asignatura_id) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, titulo);
            pstmt.setString(2, descripcion);
            pstmt.setDate(3, Date.valueOf(fecha));
            pstmt.setString(4, prioridad);
            pstmt.setInt(5, asignaturaSeleccionada.getId());
            pstmt.executeUpdate();

            alertaInfo("Agregar Tarea", "Tarea agregada correctamente.");
            cargarTareas();
            // Limpiar
            txtTitulo.clear();
            txtDescripcion.clear();
            dpFecha.setValue(null);
            comboPrioridad.setValue(null);
        } catch (SQLException ex) {
            ex.printStackTrace();
            alertaError("Error al agregar tarea", "No se pudo agregar la tarea.\n" + ex.getMessage());
        }
    }

    // Editar Tarea
    private void editarTarea(TextField txtTitulo, TextArea txtDescripcion, DatePicker dpFecha) {
        Tarea tareaSeleccionada = tablaTareas.getSelectionModel().getSelectedItem();
        if (tareaSeleccionada == null) {
            alertaAdvertencia("Editar Tarea", "No has seleccionado ninguna tarea para editar.");
            return;
        }

        Asignatura asignaturaSeleccionada = comboAsignaturas.getValue();
        if (asignaturaSeleccionada == null) {
            alertaAdvertencia("Editar Tarea", "No se ha seleccionado asignatura.");
            return;
        }

        String titulo = txtTitulo.getText();
        if (titulo.isEmpty()) {
            alertaAdvertencia("Editar Tarea", "El título está vacío.");
            return;
        }
        LocalDate fecha = dpFecha.getValue();
        if (fecha == null || fecha.isBefore(LocalDate.now())) {
            alertaAdvertencia("Editar Tarea", "La fecha es inválida (pasada o vacía).");
            return;
        }
        String prioridad = comboPrioridad.getValue();
        if (prioridad == null) {
            alertaAdvertencia("Editar Tarea", "No se ha seleccionado la prioridad.");
            return;
        }
        String descripcion = txtDescripcion.getText();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE tareas SET titulo=?, descripcion=?, fecha=?, prioridad=?, asignatura_id=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, titulo);
            pstmt.setString(2, descripcion);
            pstmt.setDate(3, Date.valueOf(fecha));
            pstmt.setString(4, prioridad);
            pstmt.setInt(5, asignaturaSeleccionada.getId());
            pstmt.setInt(6, tareaSeleccionada.getId());
            pstmt.executeUpdate();

            alertaInfo("Editar Tarea", "Tarea editada correctamente.");
            cargarTareas();
            // Limpiar
            txtTitulo.clear();
            txtDescripcion.clear();
            dpFecha.setValue(null);
            comboPrioridad.setValue(null);
        } catch (SQLException ex) {
            ex.printStackTrace();
            alertaError("Error al editar tarea", "No se pudo editar la tarea.\n" + ex.getMessage());
        }
    }

    // Eliminar Tarea
    private void eliminarTarea() {
        Tarea tareaSeleccionada = tablaTareas.getSelectionModel().getSelectedItem();
        if (tareaSeleccionada == null) {
            alertaAdvertencia("Eliminar Tarea", "No has seleccionado ninguna tarea para eliminar.");
            return;
        }
        // Confirmar
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Eliminar Tarea");
        confirm.setHeaderText("¿Está seguro de eliminar la tarea \"" + tareaSeleccionada.getTitulo() + "\"?");
        confirm.setContentText("Esta acción no se puede deshacer.");
        var result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection conn = DBUtil.getConnection()) {
                String sql = "DELETE FROM tareas WHERE id=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, tareaSeleccionada.getId());
                pstmt.executeUpdate();

                alertaInfo("Eliminar Tarea", "Tarea eliminada correctamente.");
                cargarTareas();
            } catch (SQLException ex) {
                ex.printStackTrace();
                alertaError("Error al eliminar tarea", "No se pudo eliminar la tarea.\n" + ex.getMessage());
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
