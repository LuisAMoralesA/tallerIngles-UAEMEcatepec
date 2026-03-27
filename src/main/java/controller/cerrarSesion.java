/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet que se activa cuando se desea cerrar sesion en el sitio web. 
 * @author Luis Morales
 */
@WebServlet(name = "cerrarSesion", urlPatterns = {"/cerrarSesion"})
public class cerrarSesion extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        String url = "";
        //Si hay una sesion activa
        if(sesion!=null){
            //Obtener el rango del usuario para determinar hacia que pagina se redirige
            String rango = sesion.getAttribute("rango").toString();
            if(rango!=null){
                switch(rango){
                case "ESTUDIANTE":
                    url= "/tallerDeInglesUAEM/view/loginAlumno.jsp";
                    break;
                case "ADMINISTRADOR":
                    url= "/tallerDeInglesUAEM/view/loginAdministrador.jsp";
                    break;
                case "PROFESOR":
                    url= "/tallerDeInglesUAEM/view/loginTeacher.jsp";
                    break;
                }     
            }
            else{
                url= "/tallerDeInglesUAEM/view/index.jsp";
            }
            //Al terminar de asignar la URL, quitar los atributos de sesion al usuario
            sesion.invalidate();
        }
        else{
            url= "/tallerDeInglesUAEM/view/index.jsp";
        }
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
