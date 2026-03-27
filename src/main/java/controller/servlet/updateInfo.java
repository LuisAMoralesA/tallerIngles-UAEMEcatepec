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
@WebServlet(name = "updateInfo", urlPatterns = {"/updateInfo"})
public class updateInfo extends HttpServlet {  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Busca los parametros de los cuadros de texto del usuario para actualización
               
        //Tabla de usuarios para hacer la comparacion
        String rango = request.getParameter("rango");
        
        //Estos atributos los tienen todas las tablas de usuarios
        int idprincipal = Integer.parseInt(request.getParameter("idprincipal"));
        String apaterno = request.getParameter("apaterno");
        String amaterno = request.getParameter("amaterno");
        String nombre = request.getParameter("nombre");
        String birthdate = request.getParameter("birthdate");
        String phone1 = request.getParameter("phone1");
        String email = request.getParameter("email");
                
        String url = "/tallerDeInglesUAEM/view/updateInfo.jsp"; 
  
        //if(request.getParameter("update")!=null){
        HttpSession sesion = request.getSession();
        String username = sesion.getAttribute("sesionIniciada").toString();
        int iduser = Integer.parseInt(request.getParameter("iduser"));
        BaseDatos base = new BaseDatos();
        String rangoActual = sesion.getAttribute("rango").toString();
        switch(rango){
        case "ESTUDIANTE": 
            //NOTA: No puede existir un 0 en el IDProfesor por que esta conectada a otra tabla
            int idprofesor = Integer.parseInt(request.getParameter("idprofesor"));;
            int pagos = sesion.getAttribute("pagos")!=null ? Integer.parseInt(sesion.getAttribute("pagos").toString()) : 0;
            int calif = sesion.getAttribute("calif")!=null ? Integer.parseInt(sesion.getAttribute("calif").toString()) : 0;
            String phone2 = request.getParameter("phone2");
            boolean sale_solo = "1".equals(request.getParameter("sale_solo"));
            Students student = new Students(idprincipal, idprofesor, pagos,calif, iduser,apaterno,amaterno,nombre,
                    phone1, phone2, (Object) birthdate, email, sale_solo);
            base.actualizarEstudiante(student);
            url = rangoActual.equals("ADMINISTRADOR") ? "/tallerDeInglesUAEM/view/listaAlumnos.jsp" :
                    "/tallerDeInglesUAEM/view/menuAlumno.jsp";
            break;
        case "ADMINISTRADOR" :
            Admin_school admin = new Admin_school(idprincipal,iduser,apaterno, amaterno,nombre,
                            (Object) birthdate, phone1, email);
            base.actualizarAdministrador(admin);
            url = rangoActual.equals("ADMINISTRADOR") ? "/tallerDeInglesUAEM/view/listaAdministradores.jsp" :
                    "/tallerDeInglesUAEM/view/menuAdministrador.jsp"; 
            break;
        case "PROFESOR":
            String status = request.getParameter("status");
            int grupo = Integer.parseInt(request.getParameter("grupo"));
            String classroom= request.getParameter("classroom");
            Teachers teacher = new Teachers(idprincipal,iduser,apaterno,amaterno,nombre,
                    phone1, email, (Object) birthdate,status, grupo, classroom);
            base.actualizarTeacher(teacher);
            url = rangoActual.equals("ADMINISTRADOR") ? "/tallerDeInglesUAEM/view/listaTeachers.jsp" :
                    "/tallerDeInglesUAEM/view/menuTeacher.jsp"; 
            break;
        }
        sesion.setAttribute("sesionIniciada", username);
        sesion.setAttribute("actualizacionCompleta","Datos de Usuario modificados correctamente ");
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
