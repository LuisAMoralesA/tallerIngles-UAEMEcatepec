/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import model.Tables.*;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import java.util.Iterator;

/**
 * Servlet que se activa cuando se intenta registrar a un alumno desde el sitio web. 
 * @author Luis Morales
 */
@WebServlet(name = "registerStudent", urlPatterns = {"/registerStudent"})
public class registerStudent extends HttpServlet {
    
    /*Para agregar un alumno a la base de datos, se debe de acceder a tres tablas
        1. Tabla de Usuarios para la insersion de los datos del Login
        2. Tabla de Lista de Seguimiento para insertar una nueva lista
        3. Tabla de Lista de Calificaciones para insertar una nueva lista
        4. Tabla de alumnos para agregar los datos del formulario de Registro de datos*/
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Busca los parametros de los cuadros de texto del alumno para inscripción
        String apaterno = request.getParameter("apaterno").toUpperCase();
        String amaterno = request.getParameter("amaterno").toUpperCase();
        String name = request.getParameter("name").toUpperCase();
        String phone = request.getParameter("phone");
        String birthdate = request.getParameter("birthdate");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        
        //if(request.getParameter("add")!=null){
        HttpSession sesion = request.getSession(false);
        if(password1.equals(password2)){
            //Crea las conexiones a la base de datos
            BaseDatos base = new BaseDatos();

            //Generar Nombre de Usuario
            String nomUsuario = apaterno.substring(0,2) + amaterno.substring(0,1)+name.substring(0,3) + 
                                birthdate.substring(2,4) + birthdate.substring(5,7) + birthdate.substring(8,10);

            //Hashear la contraseña a la hora de ingresarla a la base de datos
            SHA256 hash = new SHA256();
            String passwordNew = hash.contraseñaNueva(password1);

            //Apesar de tener un valor de 1, no lo tomara en cuenta el codigo
            Users user = new Users(1, nomUsuario, passwordNew, "ESTUDIANTE");
            base.insertarUsuario(user);
            
            //Define el valor de los cuadros de mensaje de confirmación
            sesion.setAttribute("contraseñaCorrecta","Usuario Agregado correctamente");
            sesion.setAttribute("userNameRegistrado", nomUsuario);

            //Asigna su lista de pago
            Payment listaPago = new Payment(1, false,false,false,false,false,false,false,false,1);
            int id_pagos = base.insertarSeguimientoDePago(listaPago);

            //Asigna una lista de calificaciones nueva
            Report reporteCalificaciones = new Report(1, 0.0, 0.0, 0.0);
            int id_calificaciones = base.insertarReporteCalificaciones(reporteCalificaciones);

            //Vuelve a obtener los datos del usuario en la tabla para obtener su id en la tabla
            ArrayList <Users> lista = base.obtenerUsuario(nomUsuario);
            Iterator  <Users> iter = lista.iterator();
            Users per = null;

            //Cuando encuentre el registro, ingresa los datos del Form a la tabla de estudiantes
            if(iter.hasNext()){
                per = iter.next();
                int id_student = 1;
                int id_teacher_student = 0;
                int id_user_student = per.getId_user();
                boolean sale_solo = false;
                Students student = new Students(id_student, id_teacher_student, id_calificaciones, id_pagos, id_user_student, apaterno,amaterno,
                                                name,phone,phone,(Object) birthdate, email, sale_solo);
                base.insertarEstudiante(student);
            }
            response.sendRedirect("/tallerDeInglesUAEM/view/loginAlumno.jsp");
        }
        else{
            sesion.setAttribute("contraseñaIncorrecta","Las contraseñas ingresadas son diferentes");
            response.sendRedirect("/tallerDeInglesUAEM/view/loginAlumno.jsp");
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
