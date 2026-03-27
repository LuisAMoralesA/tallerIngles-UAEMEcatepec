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
 * Servlet que se activa cuando se inicia sesion como Estudiante en el sitio web. 
 * @author Luis Morales
 */
@WebServlet(name = "loginStudent", urlPatterns = {"/loginStudent"})
public class loginStudent extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Busca los parametros de los cuadros de texto de usuario y contraseña
        String user = request.getParameter("user");
        //Hashear la contraseña para compararla con la de la base de datos
        SHA256 hash = new SHA256();
        String pass = hash.contraseñaNueva(request.getParameter("pass"));
        //Declara al controlador de la base de datos
        BaseDatos base = new BaseDatos();
        HttpSession sesion; 
            //Si se oprime el boton de Iniciar Sesion
            if(request.getParameter("submit")!=null){
                //Devuelve un numero segun lo indicado en el metodo de Inicio de Sesion
                int resultado = base.inicioSesion(user, pass, "ESTUDIANTE");
                switch (resultado) {
                    //Si el usuario existe y tiene el rango correcto
                    case BaseDatos.ACCESO_CONCEDIDO:
                        //Establece una sesion al usuario
                        sesion = request.getSession(true); 
                        sesion.setAttribute("sesionIniciada", user);
                        response.sendRedirect("/tallerDeInglesUAEM/view/menuAlumno.jsp");
                        break;
                    //Si el usuario no existe o tiene otro rango 
                    case BaseDatos.USUARIO_NO_ENCONTRADO:
                        //Impide el paso debido a que el usuario no fue encontrado
                        sesion = request.getSession(false);
                        sesion.setAttribute("errorMessage", "Usuario no encontrado");
                        response.sendRedirect("/tallerDeInglesUAEM/view/loginAlumno.jsp");
                        break;
                    //Si el usuario existe, pero tiene su contraseña incorrecta
                    case BaseDatos.DATO_INCORRECTO:
                        //Impide el paso debido a que la contraseña ingresada fue incorrecta
                        sesion = request.getSession(false);
                        sesion.setAttribute("errorMessage", "La contraseña ingresada es incorrecta");
                        response.sendRedirect("/tallerDeInglesUAEM/view/loginAlumno.jsp");
                        break;
                }
            }      
        //catch SQL Exception
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
