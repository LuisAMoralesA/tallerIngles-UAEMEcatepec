package controller.servlet;

import controller.BaseDatos;
import controller.SHA256;
import controller.Constantes;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import model.Consultas.ConsultaAlumnos;
import model.Tables.*;

/**
 * Servlet que se activa cuando un administrador desea agregar un estudiante desde su perfil 
 * @author Luis Morales
 */
@WebServlet(name = "addStudent", urlPatterns = {"/addStudent"})
public class addStudent extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        //Busca los parametros de los cuadros de texto del alumno para inscripción
        String apaterno = request.getParameter("apaterno").toUpperCase();
        String amaterno = request.getParameter("amaterno").toUpperCase();
        String name = request.getParameter("name").toUpperCase();
        String birthdate = request.getParameter("birthdate");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        boolean sale_solo = request.getParameter("sale_solo").equals("1") ? true : false;
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        //Recupera la sesion actual de la aplicación web. 
        HttpSession sesion = request.getSession();
        //Si las contraseñas de los cuadros de texto coinciden. 
        if(password1.equals(password2)){
            //Crea las conexiones a la base de datos
            BaseDatos base = new BaseDatos();
            //Generar Nombre de Usuario
            String nomUsuario = apaterno.substring(0,2) + amaterno.substring(0,1)+name.substring(0,3) + 
                                birthdate.substring(2,4) + birthdate.substring(5,7) + birthdate.substring(8,10);

            //Hashear la contraseña a la hora de ingresarla a la base de datos
            SHA256 hash = new SHA256();
            String passwordNew = hash.contraseñaNueva(password1);
            
            //Validar si existe el nombre de usuario en la base de datos, redirige a la lista indicando que ya existe un usuario en Base de Datos.
            ArrayList <ConsultaAlumnos> usuarioEncontrado = base.obtenerDatosAlumno(nomUsuario);
            if(usuarioEncontrado != null && !usuarioEncontrado.isEmpty()){
               String mensaje = base.mensajeAlumnoEncontrado(usuarioEncontrado);
               sesion.setAttribute("actualizacionCompleta","El alumno ingresado ya se encuentra en la base de datos");
               sesion.setAttribute("userNameRegistrado", mensaje);
               sesion.setAttribute("iconoVentana", "warning");
               response.sendRedirect(Constantes.VentanasJSP.URL_LISTA_ALUMNOS);
               return;
            }
            
            else{
                //Crear un objeto de tipo User e insertarlo en la Base de datos.
                //Apesar de tener un valor de 1, no lo tomara en cuenta el codigo
                Users user = new Users(1, nomUsuario, passwordNew,"ESTUDIANTE");
                base.insertarUsuario(user);
                
                //Define el valor de los cuadros de mensaje de confirmación
                sesion.setAttribute("actualizacionCompleta","Usuario Agregado correctamente");
                sesion.setAttribute("userNameRegistrado", nomUsuario);
                sesion.setAttribute("iconoVentana", "success");
                
                //Crear un objeto de tipo Payment para la lista de Pago e insertarlo en la base de datos. 
                Payment listaPago = new Payment(1, false,false,false,false,false,false,false,false,1);
                int id_pagos = base.insertarSeguimientoDePago(listaPago);

                //Crear un objeto de tipo Reportt para la lista de Calificaciones e insertarlo en la base de datos.
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
                    Students student = new Students(id_student, id_teacher_student, id_calificaciones, id_pagos, id_user_student, apaterno,amaterno,
                                                    name,phone,phone,(Object) birthdate, email, sale_solo);
                    base.insertarEstudiante(student);
                }
                //Redirigir a la lista de alumnos, indicando que la inserción fue exitosa. 
                response.sendRedirect(Constantes.VentanasJSP.URL_LISTA_ALUMNOS);
            }
        }
        //Si las contraseñas son erroneas, imprimir el mensaje en la ventana actual. 
        else{
            sesion.setAttribute("contraseñaIncorrecta","Las contraseñas ingresadas son diferentes");
            response.sendRedirect(Constantes.VentanasJSP.URL_AGREGAR_INFORMACION+"?add=1");
        }
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
