/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.servlet;

import controller.BaseDatos;
import model.Tables.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet que se activa cuando se intenta actualizar la informacion personal de un usuario en el sitio web. 
 * @author Luis Morales
 */
@WebServlet(name = "updateGrade", urlPatterns = {"/updateGrade"})
public class updateGrade extends HttpServlet {  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        int idGrade = Integer.parseInt(request.getParameter("idGrade"));
        double firstPartial = Double.parseDouble(request.getParameter("firstPartial"));
        double secondPartial = Double.parseDouble(request.getParameter("secondPartial"));
        double avg = (firstPartial + secondPartial) /2;
        BaseDatos bd = new BaseDatos();
        Report calificaciones = new Report(idGrade, firstPartial, secondPartial, avg);
        bd.actualizarReporteCalificaciones(calificaciones);
        sesion.setAttribute("actualizacionCompleta","Lista de Calificaciones actualizada correctamente ");
        String url = "/tallerDeInglesUAEM/view/listaAlumnos.jsp";
        response.sendRedirect(url);
    }
    
    //Para evitar fallos en el futuro
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
