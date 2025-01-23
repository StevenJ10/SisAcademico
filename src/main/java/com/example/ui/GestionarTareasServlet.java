package com.example.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Clase modelo para Tarea
class Tarea {
    private int id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private String prioridad;
    private String asignatura;

    public Tarea(int id, String titulo, String descripcion, LocalDate fecha, String prioridad, String asignatura) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.prioridad = prioridad;
        this.asignatura = asignatura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }
}

@WebServlet(urlPatterns = "/gestionarTareas")
public class GestionarTareasServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Obtener la lista de tareas de la sesión (o crearla si no existe)
        List<Tarea> tareas = (List<Tarea>) request.getSession().getAttribute("tareas");
        if (tareas == null) {
            tareas = new ArrayList<>();
            request.getSession().setAttribute("tareas", tareas);
        }

        switch (action) {
            case "add":
                agregarTarea(request, tareas);
                break;
            case "edit":
                editarTarea(request, tareas);
                break;
            case "delete":
                eliminarTarea(request, tareas);
                break;
            default:
                break;
        }

        // Redirigir de vuelta a la página de gestión de tareas
        response.sendRedirect("gestionarTareas.jsp");
    }

    private void agregarTarea(HttpServletRequest request, List<Tarea> tareas) {
        try {
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String fechaStr = request.getParameter("fecha");
            String prioridad = request.getParameter("prioridad");
            String asignatura = request.getParameter("asignatura");
    
            if (titulo != null && descripcion != null && fechaStr != null &&
                prioridad != null && asignatura != null) {
                LocalDate fecha = LocalDate.parse(fechaStr);
                int id = tareas.size() + 1;
    
                Tarea nuevaTarea = new Tarea(id, titulo, descripcion, fecha, prioridad, asignatura);
                tareas.add(nuevaTarea);
            } else {
                System.out.println("Parámetros inválidos.");
            }
        } catch (Exception e) {
            System.out.println("Error al agregar tarea: " + e.getMessage());
        }
    }

    private void editarTarea(HttpServletRequest request, List<Tarea> tareas) {
        int id = Integer.parseInt(request.getParameter("id"));
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));
        String prioridad = request.getParameter("prioridad");
        String asignatura = request.getParameter("asignatura");

        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                tarea.setTitulo(titulo);
                tarea.setDescripcion(descripcion);
                tarea.setFecha(fecha);
                tarea.setPrioridad(prioridad);
                tarea.setAsignatura(asignatura);
                System.out.println("Tarea editada: " + tarea.getTitulo());
                break;
            }
        }
    }

    private void eliminarTarea(HttpServletRequest request, List<Tarea> tareas) {
        int id = Integer.parseInt(request.getParameter("id"));

        tareas.removeIf(tarea -> tarea.getId() == id);

        System.out.println("Tarea eliminada: ID " + id);
    }
}
