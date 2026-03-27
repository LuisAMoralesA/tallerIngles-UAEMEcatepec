/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
 *
 * @author anton
 */
@WebServlet(name = "deleteInformation", urlPatterns = {"/deleteInformation"})
public class deleteInformation extends HttpServlet {

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
        
            String rango = request.getParameter("rango");
            int id_principal = Integer.parseInt(request.getParameter("id"));
            int id_admin, id_student, id_teacher, id_group;
            int id_user = 0;
            String url = "";
            HttpSession sesion = request.getSession();
            BaseDatos bd = new BaseDatos();
            switch (rango){
                //CASO DE ADMINISTRADOR
                case "a":
                    id_user = Integer.parseInt(request.getParameter("user"));
                    id_admin = id_principal;
                    bd.eliminarAdministrador(id_admin);
                    bd.eliminarUsuario(id_user);
                    url = "/tallerDeInglesUAEM/view/listaAdministradores.jsp";
                    //Define el valor de los cuadros de mensaje de confirmación
                    sesion.setAttribute("actualizacionCompleta","Datos del Administrador eliminados correctamente");
                    break;
                //CASO DE ESTUDIANTE
                case "s":
                    id_user = Integer.parseInt(request.getParameter("user"));
                    id_student = id_principal;
                    bd.eliminarListaDePago(id_student);
                    bd.eliminarListaCalificaciones(id_student);
                    bd.eliminarAlumno(id_student);
                    bd.eliminarUsuario(id_user);
                    url = "/tallerDeInglesUAEM/view/listaAlumnos.jsp";
                    //Define el valor de los cuadros de mensaje de confirmación
                    sesion.setAttribute("actualizacionCompleta","Datos del Estudiante eliminados correctamente");
                    break;
                //CASO DE TEACHER
                case "t":
                    id_user = Integer.parseInt(request.getParameter("user"));
                    id_teacher = id_principal;
                    bd.desvincularAlumnos(id_teacher);
                    bd.eliminarTeacher(id_teacher);
                    bd.eliminarUsuario(id_user);
                    url = "/tallerDeInglesUAEM/view/listaTeachers.jsp";
                    //Define el valor de los cuadros de mensaje de confirmación
                    sesion.setAttribute("actualizacionCompleta","Datos del Profesor eliminados correctamente");
                    break;
                //CASO DE GRUPOS
                case "g":
                    id_group = id_principal;
                    bd.desvincularProfesores(id_group);
                    bd.eliminarGrupo(id_group);
                    url = "/tallerDeInglesUAEM/view/listaGrupos.jsp";
                    sesion.setAttribute("actualizacionCompleta","Datos del Grupo eliminados correctamente");
                    break;
            }
            response.sendRedirect(url);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
