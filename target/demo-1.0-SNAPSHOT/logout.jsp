<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cerrando sesión...</title>
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

        h1 {
            color: #333;
            font-size: 24px;
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
        <h1>Has cerrado sesion correctamente</h1>
        <p>Estas siendo redirigido al inicio de sesion...</p>

        <div class="buttons">
            <!-- Agrega botones si lo necesitas, pero por ahora solo se redirige automáticamente -->
            <button onclick="location.href='login.jsp'">Ir a Login</button>
        </div>
    </div>

    <script>
        // Redirigir al login después de 2 segundos
        setTimeout(function() {
            window.location.href = 'login.jsp';
        }, 2000);  // 2000 ms = 2 segundos
    </script>
</body>
</html>
