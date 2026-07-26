/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.servlet;

import controller.BaseDatos;
import controller.Constantes;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Luis Morales
 */
@WebServlet(name = "updatePeriodo", urlPatterns = {"/updatePeriodo"})
public class updatePeriodo extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        String periodoActual = request.getParameter("periodoActual");
        BaseDatos bd = new BaseDatos();
        bd.actualizarCalendario(periodoActual);
        sesion.setAttribute("actualizacionCompleta","Periodo escolar actualizado correctamente");
        String url = Constantes.VentanasJSP.URL_CALENDARIO;
        response.sendRedirect(url);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
