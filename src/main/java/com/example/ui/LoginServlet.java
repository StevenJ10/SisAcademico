package com.example.ui;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validar credenciales (ejemplo simple, integrar con tu base de datos)
        if ("admin".equals(username) && "1234".equals(password)) {
            // Iniciar sesión
            request.getSession().setAttribute("user", username);
            response.sendRedirect("welcome.jsp"); // Redirigir a la página de bienvenida
        } else {
            // Si las credenciales son incorrectas, mostrar el mensaje de error
            request.setAttribute("errorMessage", "Usuario o contraseña incorrectos.");
            request.getRequestDispatcher("login.jsp").forward(request, response); // Volver a la página de login con mensaje de error
        }
    }
}
