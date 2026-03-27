/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
@WebServlet(name = "updateSchedule", urlPatterns = {"/updateSchedule"})
public class updateSchedule extends HttpServlet {  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        BaseDatos bd = new BaseDatos();
        int id_pay = Integer.parseInt(request.getParameter("id_pay"));
        String periodo = request.getParameter("periodo");
        String mes = request.getParameter("mes");
        String descripcion = request.getParameter("descripcion");
        double mensualidad = Double.parseDouble(request.getParameter("mensualidad"));
        String deadline = request.getParameter("deadline");
        if(deadline.equals("") || deadline.equals("null")){
            deadline = null;
        }

        Pay_simbology symbol = new Pay_simbology(id_pay, mes, descripcion, mensualidad, periodo, (Object) deadline);
        bd.actualizarCalendario(symbol);
        sesion.setAttribute("actualizacionCompleta","Mensualidad del calendario actualizada correctamente");
        String url = "/tallerDeInglesUAEM/view/calendario.jsp";
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
