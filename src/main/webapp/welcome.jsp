<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema Academico - Bienvenido</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
            text-align: center;
            max-width: 600px;
            width: 90%;
            position: relative;
        }

        /* Estilo del botón de cerrar sesión */
        .menu {
            position: absolute;
            top: 20px;
            right: 20px;
        }

        .menu button {
            padding: 10px 15px;
            background-color: #FF4C4C; /* Rojo para indicar cierre de sesión */
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        .menu button:hover {
            background-color: #FF1A1A;
        }

        img {
            width: 150px;
            margin-bottom: 20px;
        }

        h1 {
            color: #333;
            font-size: 20px;
            margin-bottom: 10px;
        }

        p {
            color: #666;
            font-size: 16px;
            margin-bottom: 20px;
        }

        .buttons {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .buttons button {
            padding: 10px;
            font-size: 16px;
            color: #fff;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .buttons button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Menú de cierre de sesión -->
        <div class="menu">
            <button onclick="handleLogout()">Cerrar sesion</button>
        </div>

        <!-- Imagen -->
        <img src="fondo.jpg" alt="Libros y escalera" />

        <!-- Texto de bienvenida -->
        <h1>Bienvenido <c:out value="${sessionScope.user}" default="Usuario" />!</h1>
        <p>Aqui podras gestionar tus tareas, asignar prioridades y mantener un control completo de tus actividades academicas.</p>

        <!-- Botones de acción -->
        <div class="buttons">
            <button onclick="location.href='gestionarTareas.jsp'">Gestionar Tareas</button>
            <button onclick="location.href='gestionarAsignaturas.jsp'">Gestionar Asignaturas</button>
        </div>
    </div>

    <script>
        function handleLogout() {
            // Redirigir al servlet logout que se encargará de invalidar la sesión
            location.href = 'logout.jsp';
        }
    </script>
</body>
</html>
