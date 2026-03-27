/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.servlet;

import controller.BaseDatos;
import controller.SHA256;
import model.Tables.Users;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet que se activa cuando se intenta actualizar la informacion de inicio de sesion de un usuario en el sitio web. 
 * @author Luis Morales
 */
@WebServlet(name = "updateUser", urlPatterns = {"/updateUser"})
public class updateUser extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         //Busca los parametros de los cuadros de texto del usuario para actualización
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String rango = request.getParameter("rango");
        String url = "/tallerDeInglesUAEM/view/actualizarUsuario.jsp"; 
        
        //No poner atributos disabled en HTML por que no son detectados
        HttpSession sesion = request.getSession();
        String usernameOriginal = sesion.getAttribute("sesionIniciada").toString();
        int uid = Integer.parseInt(request.getParameter("iduser"));
        if(password1.equals(password2)){
            //Crea las conexiones a la base de datos
            BaseDatos base = new BaseDatos();
            //Hashear la contraseña a la hora de ingresarla a la base de datos
            SHA256 hash = new SHA256();
            password1 = hash.contraseñaNueva(password1);

            //Actualizar los valores 
            Users user = new Users(uid, username, password1, rango);
            base.actualizarUsuario(user);
            String rangoActual = sesion.getAttribute("rango").toString();

            sesion.setAttribute("actualizacionCompleta","Usuario Actualizado correctamente");

            switch(rango){
                case "ESTUDIANTE": 
                    url = rangoActual.equals("ADMINISTRADOR") ? "/tallerDeInglesUAEM/view/listaAlumnos.jsp": 
                            "/tallerDeInglesUAEM/view/menuAlumno.jsp";
                    break;
                case "ADMINISTRADOR" : 
                    url = rangoActual.equals("ADMINISTRADOR") ? "/tallerDeInglesUAEM/view/listaAdministradores.jsp" : 
                            "/tallerDeInglesUAEM/view/menuAdministrador.jsp"; 
                    break;
                case "PROFESOR":
                    url = rangoActual.equals("ADMINISTRADOR") ? "/tallerDeInglesUAEM/view/listaTeachers.jsp" : 
                            "/tallerDeInglesUAEM/view/menuTeacher.jsp"; 
                    break;
            }
            sesion.setAttribute("sesionIniciada", usernameOriginal);
            response.sendRedirect(url);

        }
        else{
            sesion.setAttribute("contraseñaIncorrecta","El dato ingresado no es valido");
            response.sendRedirect(url);
        }
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
