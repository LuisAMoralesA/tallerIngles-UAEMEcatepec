/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.servlet;

import controller.BaseDatos;
import controller.Constantes;
import controller.Reportes;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import jakarta.servlet.annotation.WebServlet;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;


/**
 * Servlet que se activa cuando se desea visualizar documentos en el sitio web. 
 * @author Luis Morales
 */
@WebServlet(name = "reportesServlet", urlPatterns = {"/reportesServlet"})
public class reportesServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //Llamar a la clase para crear los reportes
            Reportes r = new Reportes();
            //Llama a la base de datos para obtener ciertos datos
            BaseDatos bd = new BaseDatos();
            //Crear una variable para saber que reporte es el que se quiere imprimir
            String URLJasper = "";
            //Definir la ruta de la carpeta imagenes para no generar rutas estaticas
            String imagenes = getServletContext().getRealPath("/Images/") + File.separator;
            
            //Obtiene los atributos de solicitud que hay en cada hipervinculo de documento
            String attendance = request.getParameter("Attendance");
            String payment = request.getParameter("Payment");
            String grade = request.getParameter("Grade");
            
            //Declara variables para los parametros que piden la mayoria de los documentos
            int id_teacher = 0;
            String grupo = "";
            String profesor = "";
            String classroom = "";
            String numMeses = "";
            String ruta = "";
            
            //Formula para obtener el periodo que solicita el documento
            String periodo = "";
            LocalDate hoy = LocalDate.now();
            String mesActual = hoy.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            String año = Integer.toString(hoy.getYear());
            mesActual = mesActual.substring(0, 1).toUpperCase() + mesActual.substring(1);
            String periodoActual = bd.obtenerPeriodo(mesActual);

            try{
                //Si se solicita la bitacora de Asistencia
                if(attendance != null){
                    URLJasper = Constantes.Reportes.URL_JASPER_BITACORA_ALUMNOS;
                    id_teacher = Integer.parseInt(attendance);
                    grupo = bd.concatenarDatosGrupo(id_teacher);
                    profesor = bd.concatenarDatosProfesor(id_teacher);
                    classroom = request.getParameter("c");
                    //Define el documento que hay que imprimir
                    ruta = getServletContext().getRealPath(URLJasper);
                    r.bitacorasDeAlumnos(response, ruta, imagenes, grupo, profesor, id_teacher, classroom);
                }
                //Si se solicita la lista de seguimiento de Pagos
                else if(payment != null){
                    URLJasper = Constantes.Reportes.URL_JASPER_LISTA_SEGUIMIENTO_PAGO;
                    periodo = año + " " + periodoActual;
                    id_teacher = Integer.parseInt(payment);
                    grupo = bd.concatenarDatosGrupo(id_teacher);
                    profesor = bd.concatenarDatosProfesor(id_teacher);
                    classroom = request.getParameter("c");
                    numMeses = String.valueOf(bd.conteoMeses(periodoActual));
                    ruta = getServletContext().getRealPath(URLJasper);
                    r.listasPagos(response, ruta, imagenes, grupo, profesor, id_teacher, classroom, periodo, numMeses);
                }
                //Si se solicita la lista de calificaciones
                else if(grade !=null){
                    URLJasper = Constantes.Reportes.URL_JASPER_LISTA_CALIFICACIONES;
                    periodo = año + " " + periodoActual;
                    id_teacher = Integer.parseInt(grade);
                    grupo = bd.concatenarDatosGrupo(id_teacher);
                    profesor = bd.concatenarDatosProfesor(id_teacher);
                    ruta = getServletContext().getRealPath(URLJasper);
                    r.listaCalificaciones(response, ruta, imagenes, grupo, profesor, id_teacher, periodo);
                }
                //Si se solicita la bitacora de profesores
                else{
                    URLJasper = Constantes.Reportes.URL_JASPER_BITACORA_PROFESORES;
                    periodo = año + " " + periodoActual;
                    ruta = getServletContext().getRealPath(URLJasper);
                    r.bitacorasDeProfesores(response, ruta, imagenes, periodo);
                }
            }catch (Exception ex) {
                throw new ServletException("Error al generar reporte", ex);
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
