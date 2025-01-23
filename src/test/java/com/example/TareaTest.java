package com.example;

import org.junit.Test;
import com.example.model.Tarea;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class TareaTest {
    
    @Test
    public void testConstructorYGetters() {
        Tarea tarea = new Tarea(1, "Estudio Matemáticas", "Estudiar álgebra", LocalDate.of(2025, 1, 20), "Alta", 101);
        assertEquals(1, tarea.getId());
        assertEquals("Estudio Matemáticas", tarea.getTitulo());
        assertEquals("Estudiar álgebra", tarea.getDescripcion());
        assertEquals(LocalDate.of(2025, 1, 20), tarea.getFecha());
        assertEquals("Alta", tarea.getPrioridad());
        assertEquals(101, tarea.getAsignaturaId());
    }

    @Test
    public void testSetters() {
        Tarea tarea = new Tarea();
        tarea.setId(2);
        tarea.setTitulo("Estudio Historia");
        tarea.setDescripcion("Estudiar la Revolución Industrial");
        tarea.setFecha(LocalDate.of(2025, 2, 15));
        tarea.setPrioridad("Media");
        tarea.setAsignaturaId(102);

        assertEquals(2, tarea.getId());
        assertEquals("Estudio Historia", tarea.getTitulo());
        assertEquals("Estudiar la Revolución Industrial", tarea.getDescripcion());
        assertEquals(LocalDate.of(2025, 2, 15), tarea.getFecha());
        assertEquals("Media", tarea.getPrioridad());
        assertEquals(102, tarea.getAsignaturaId());
    }

}
