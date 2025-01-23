package com.example;

import org.junit.Test;
import static org.junit.Assert.*;
import com.example.model.Asignatura;

public class AsignaturaTest {

     @Test
    public void testAsignaturaConstructorIdNombre() {
        Asignatura asignatura = new Asignatura(1, "Matemáticas");
        assertEquals(1, asignatura.getId());
        assertEquals("Matemáticas", asignatura.getNombre());
    }

    @Test
    public void testAsignaturaConstructorNombre() {
        Asignatura asignatura = new Asignatura("Ciencias");
        assertEquals("Ciencias", asignatura.getNombre());
    }

    @Test
    public void testSettersYGetters() {
        Asignatura asignatura = new Asignatura();
        asignatura.setId(2);
        asignatura.setNombre("Física");
        assertEquals(2, asignatura.getId());
        assertEquals("Física", asignatura.getNombre());
    }

    @Test
    public void testToString() {
        Asignatura asignatura = new Asignatura("Lengua");
        assertEquals("Lengua", asignatura.toString());
    }
}
