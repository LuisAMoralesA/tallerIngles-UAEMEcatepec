/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.servlet;

import controller.Constantes;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import controller.Respaldo;
import controller.BaseDatos;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Servlet que se activa cuando se desea hacer respaldo semestral del SIGEP. 
 * @author Luis Morales
 */

@WebServlet(name = "backupServlet", urlPatterns = {"/backupServlet"})
public class backupServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            BaseDatos bd = new BaseDatos();
            //Obtiene los parametros de la solicitud para ver cual archivo se debe generar. (Excel o SQL)
            String type = request.getParameter("type");
            
            //Formula para obtener el periodo que solicita el documento
            LocalDate hoy = Constantes.HOY;
            String año = Integer.toString(hoy.getYear());
            String periodoActual = bd.obtenerPeriodo(1);
            
            //Si se desea hacer el respaldo en Excel. 
            if("excel".equals(type)){
                //Define un nombre para el documento de respaldo de Excel
                String nombreExcel = "Respaldo_TEI_"+ año + "_" + periodoActual.replace(" ", "_")+".xlsx";
                // Indicamos que la respuesta es un archivo de Excel (.xlsx)
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                // Forzamos al navegador a abrir la ventana de descarga con el nombre sugerido
                response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreExcel + "\"");
                //Definir la ruta de la carpeta imagenes para no generar rutas estaticas
                String imagenLogo = getServletContext().getRealPath("/Images/") ;
                if(imagenLogo!=null){
                    File folder = new File(imagenLogo);
                    if (folder.exists()){
                        imagenLogo = folder.getAbsolutePath() + File.separator + "Logo_Taller2_BN.png";
                    }
                }
                else{
                    imagenLogo = Constantes.Imagenes.URL_LOGO_TALLER2_BN;
                }
                try{
                    //Genenera el documento de Excel por medio de la clase de respaldo
                    OutputStream salida = response.getOutputStream();
                    Respaldo backup = new Respaldo();
                    backup.iniciarRespaldo(salida, imagenLogo);
                    //Cierra el flujo de datos del navegador
                    salida.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            //Si se desea hacer el respaldo en un archivo sql. 
            else if("sql".equals(type)){
                //Define un nombre para el documento de respaldo de Lenguaje SQL
                String nombreSQL = "Respaldo_TEI_"+ año + "_" + periodoActual.replace(" ", "_")+".sql";
                // Indicamos que la respuesta es un archivo SQL
                response.setContentType("text/sql");
                // Forzamos al navegador a abrir la ventana de descarga con el nombre sugerido
                response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreSQL + "\"");
                //Se localiza el recurso donde se encuentra el MySQL dump para generar el archivo
                String rutaMysqldump = getServletContext().getRealPath("/WEB-INF/mysqldump.exe");
                if(rutaMysqldump!=null){
                    File folder = new File(rutaMysqldump);
                    if (folder.exists()){
                        rutaMysqldump = folder.getAbsolutePath();
                    }
                }
                else{
                    rutaMysqldump = Constantes.ARCHIVO_MYSQLDUMP;
                }
                try{
                    //Genenera el documento SQL por medio de la clase de respaldo
                    OutputStream salida = response.getOutputStream();
                    Respaldo backup = new Respaldo();
                    backup.iniciarRespaldoSQL(salida,rutaMysqldump);
                    //Cierra el flujo de datos del navegador
                    salida.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
