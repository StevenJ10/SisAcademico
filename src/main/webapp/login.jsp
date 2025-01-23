<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio de Sesión</title>
    <style>
        /* Estilo general de la página */
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
            width: 100%;
        }

        .card {
            background-color: white;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            padding: 30px;
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        .card-title {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
        }

        .form-label {
            font-size: 14px;
            color: #555;
            margin-bottom: 5px;
            text-align: left;
            display: block;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
        }

        input[type="text"]:focus, input[type="password"]:focus {
            border-color: #007BFF;
            outline: none;
        }

        .btn-primary {
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .text-danger {
            color: red;
            font-size: 14px;
            margin-top: 10px;
        }

        /* Estilo de la caja de error */
        .error-message {
            text-align: center;
            color: red;
            font-size: 14px;
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="card">
            <h3 class="card-title">Iniciar Sesión</h3>

            <!-- Formulario de inicio de sesión -->
            <form action="login" method="post">
                <div>
                    <label for="username" class="form-label">Usuario:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div>
                    <label for="password" class="form-label">Contraseña:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div>
                    <button type="submit" class="btn-primary">Entrar</button>
                </div>

                <!-- Mostrar mensaje de error si existe -->
                <c:if test="${not empty errorMessage}">
                    <p class="error-message">${errorMessage}</p>
                </c:if>
            </form>
        </div>
    </div>

</body>
</html>
