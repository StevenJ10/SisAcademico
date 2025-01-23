package com.example;

import org.junit.Test;
import com.example.model.Usuario;
import static org.junit.Assert.*;

public class UsuarioTest {
    
    @Test
    public void testConstructorYGetters() {
        Usuario usuario = new Usuario(1, "stevenv", "password123", "Steven Velásquez", "steven@email.com", "Administrador");
        assertEquals(1, usuario.getId());
        assertEquals("stevenv", usuario.getUsername());
        assertEquals("password123", usuario.getPassword());
        assertEquals("Steven Velásquez", usuario.getNombreCompleto());
        assertEquals("steven@email.com", usuario.getEmail());
        assertEquals("Administrador", usuario.getRol());
    }

    @Test
    public void testSetters() {
        Usuario usuario = new Usuario();
        usuario.setId(2);
        usuario.setUsername("maria123");
        usuario.setPassword("pass456");
        usuario.setNombreCompleto("María López");
        usuario.setEmail("maria@email.com");
        usuario.setRol("Estudiante");

        assertEquals(2, usuario.getId());
        assertEquals("maria123", usuario.getUsername());
        assertEquals("pass456", usuario.getPassword());
        assertEquals("María López", usuario.getNombreCompleto());
        assertEquals("maria@email.com", usuario.getEmail());
        assertEquals("Estudiante", usuario.getRol());
    }

}
