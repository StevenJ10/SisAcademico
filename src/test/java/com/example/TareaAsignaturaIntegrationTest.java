package com.example;

import com.example.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.Assert.*;

public class TareaAsignaturaIntegrationTest {

    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        // Usar base de datos en memoria de H2 para pruebas
        connection = DBUtil.getConnection();
        
        // Crear tablas necesarias para la prueba
        String createTableAsignatura = "CREATE TABLE IF NOT EXISTS asignatura (" +
                                      "id INT PRIMARY KEY, " +
                                      "nombre VARCHAR(255) NOT NULL)";
        String createTableTarea = "CREATE TABLE IF NOT EXISTS tarea (" +
                                  "id INT PRIMARY KEY, " +
                                  "titulo VARCHAR(255) NOT NULL, " +
                                  "descripcion TEXT, " +
                                  "fecha DATE, " +
                                  "prioridad VARCHAR(50), " +
                                  "asignatura_id INT, " +
                                  "FOREIGN KEY (asignatura_id) REFERENCES asignatura(id))";
        
        try (PreparedStatement stmt = connection.prepareStatement(createTableAsignatura)) {
            stmt.execute();
        }

        try (PreparedStatement stmt = connection.prepareStatement(createTableTarea)) {
            stmt.execute();
        }
    }

    @Test
    public void testTareaConAsignatura() throws SQLException {
        // Insertar datos de prueba
        String insertAsignatura = "INSERT INTO asignatura (id, nombre) VALUES (1, 'Matemáticas')";
        String insertTarea = "INSERT INTO tarea (id, titulo, descripcion, fecha, prioridad, asignatura_id) " +
                             "VALUES (1, 'Estudiar álgebra', 'Estudiar álgebra lineal', '2025-01-20', 'Alta', 1)";
        
        try (PreparedStatement stmt = connection.prepareStatement(insertAsignatura)) {
            stmt.execute();
        }

        try (PreparedStatement stmt = connection.prepareStatement(insertTarea)) {
            stmt.execute();
        }

        // Recuperar datos y verificar que la relación se haya establecido correctamente
        String selectTarea = "SELECT t.id, t.titulo, a.nombre FROM tarea t " +
                             "JOIN asignatura a ON t.asignatura_id = a.id WHERE t.id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(selectTarea)) {
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                assertEquals("Estudiar álgebra", resultSet.getString("titulo"));
                assertEquals("Matemáticas", resultSet.getString("nombre"));
            }
        }
    }

    @After
    public void tearDown() throws SQLException {
        // Limpiar la base de datos después de cada prueba
        String dropTableTarea = "DROP TABLE IF EXISTS tarea";
        String dropTableAsignatura = "DROP TABLE IF EXISTS asignatura";
        
        try (PreparedStatement stmt = connection.prepareStatement(dropTableTarea)) {
            stmt.execute();
        }
        
        try (PreparedStatement stmt = connection.prepareStatement(dropTableAsignatura)) {
            stmt.execute();
        }

        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}

