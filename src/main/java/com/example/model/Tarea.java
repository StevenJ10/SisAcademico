package com.example.model;

import java.time.LocalDate;

public class Tarea {
    private int id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private String prioridad;
    private int asignaturaId;

    public Tarea() {
    }

    public Tarea(int id, String titulo, String descripcion, LocalDate fecha, String prioridad, int asignaturaId) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.prioridad = prioridad;
        this.asignaturaId = asignaturaId;
    }

    public Tarea(String titulo, String descripcion, LocalDate fecha, String prioridad, int asignaturaId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.prioridad = prioridad;
        this.asignaturaId = asignaturaId;
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

    public int getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }
}
