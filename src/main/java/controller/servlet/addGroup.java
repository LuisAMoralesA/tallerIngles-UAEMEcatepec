/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.servlet;

import controller.BaseDatos;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import model.Tables.*;

/**
 * Servlet que se activa cuando un administrador desea agregar un estudiante desde su perfil 
 * @author Luis Morales
 */
@WebServlet(name = "addGroup", urlPatterns = {"/addGroup"})
public class addGroup extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Busca los parametros de los cuadros de texto del alumno para inscripción
        int id_group = 1;
        int id_grade = Integer.parseInt(request.getParameter("grade"));
        int level = Integer.parseInt(request.getParameter("level"));
        int id_category = Integer.parseInt(request.getParameter("category"));
        
        HttpSession sesion = request.getSession();
        BaseDatos base = new BaseDatos();
        
        Grupos grupo = new Grupos(id_group, id_grade, level, id_category, "null");
        base.insertarGrupos(grupo);
        
        //Define el valor de los cuadros de mensaje de confirmación
        sesion.setAttribute("actualizacionCompleta","Grupo Agregado correctamente");
        sesion.setAttribute("userNameRegistrado", "");
        response.sendRedirect("/tallerDeInglesUAEM/view/listaGrupos.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


}
