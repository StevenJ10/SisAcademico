<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://xmlns.jcp.org/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://xmlns.jcp.org/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <title>Gestionar Tareas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }

        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
        }

        header {
            background: #35424a;
            color: #ffffff;
            padding-top: 30px;
            min-height: 70px;
            border-bottom: #e8491d 3px solid;
        }

        header a {
            color: #ffffff;
            text-decoration: none;
            text-transform: uppercase;
            font-size: 16px;
        }

        .task-form {
            background: #ffffff;
            padding: 20px;
            margin-top: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .task-form label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .task-form input, .task-form select, .task-form textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .task-form button {
            display: inline-block;
            background: #35424a;
            color: #ffffff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
        }

        .task-form button:hover {
            background: #e8491d;
        }

        table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }

        table th, table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }

        table th {
            background: #35424a;
            color: #ffffff;
        }
    </style>
</head>
<body>
<header>
    <div class="container">
        <h1>Gestionar Tareas</h1>
    </div>
</header>
<div class="container">
    <div class="task-form">
        <form method="post" action="gestionarTareas">
            <label for="titulo">Título:</label>
            <input type="text" id="titulo" name="titulo" required>

            <label for="descripcion">Descripción:</label>
            <textarea id="descripcion" name="descripcion" rows="3" required></textarea>

            <label for="fecha">Fecha:</label>
            <input type="date" id="fecha" name="fecha" required>

            <label for="asignatura">Asignatura:</label>
            <select id="asignatura" name="asignatura" required>
                <option value="Matemáticas">Matemáticas</option>
                <option value="Historia">Historia</option>
                <option value="Ciencias">Ciencias</option>
            </select>

            <label for="prioridad">Prioridad:</label>
            <select id="prioridad" name="prioridad" required>
                <option value="Baja">Baja</option>
                <option value="Media">Media</option>
                <option value="Alta">Alta</option>
            </select>

            <button type="submit" name="action" value="agregar">Agregar</button>
            <button type="submit" name="action" value="editar">Editar</button>
            <button type="submit" name="action" value="eliminar">Eliminar</button>
        </form>
    </div>

    <table>
        <thead>
        <tr>
            <th>Título</th>
            <th>Descripción</th>
            <th>Fecha</th>
            <th>Asignatura</th>
            <th>Prioridad</th>
        </tr>
        </thead>
        <tbody>
            <%
            List<String[]> tareas = (List<String[]>) session.getAttribute("tareas");
            if (tareas == null) {
                tareas = new ArrayList<>();
                session.setAttribute("tareas", tareas);
            }
        
            String action = request.getParameter("action");
            if (action != null) {
                try {
                    if ("agregar".equals(action)) {
                        String titulo = request.getParameter("titulo");
                        String descripcion = request.getParameter("descripcion");
                        String fecha = request.getParameter("fecha");
                        String asignatura = request.getParameter("asignatura");
                        String prioridad = request.getParameter("prioridad");
        
                        tareas.add(new String[]{titulo, descripcion, fecha, asignatura, prioridad});
                    } else if ("editar".equals(action)) {
                        out.println("Editar tarea no implementado.");
                    } else if ("eliminar".equals(action)) {
                        out.println("Eliminar tarea no implementado.");
                    }
                } catch (Exception e) {
                    out.println("Error al procesar acción: " + e.getMessage());
                }
            }
        %>
        
        <tr>
            <td><%= tarea[0] %></td>
            <td><%= tarea[1] %></td>
            <td><%= tarea[2] %></td>
            <td><%= tarea[3] %></td>
            <td><%= tarea[4] %></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
