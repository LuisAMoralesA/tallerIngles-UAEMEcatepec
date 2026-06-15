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
 *
 * @author anton
 */
@WebServlet(name = "backupServlet", urlPatterns = {"/backupServlet"})
public class backupServlet extends HttpServlet {

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
            BaseDatos bd = new BaseDatos();
            
            //Formula para obtener el periodo que solicita el documento
            LocalDate hoy = LocalDate.now();
            String mesActual = hoy.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            String año = Integer.toString(hoy.getYear());
            mesActual = mesActual.substring(0, 1).toUpperCase() + mesActual.substring(1);
            String periodoActual = bd.obtenerPeriodo(mesActual);
            
            String nombreExcel = "Respaldo_TEI_"+ año + "_" + periodoActual.replace(" ", "_")+".xlsx";
            
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
            
            // Indicamos que la respuesta es un archivo de Excel (.xlsx)
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // Forzamos al navegador a abrir la ventana de descarga con el nombre sugerido
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreExcel + "\"");
            try{
                OutputStream salida = response.getOutputStream();
                Respaldo backup = new Respaldo();
                backup.iniciarRespaldo(salida, imagenLogo);
                salida.flush();
            } catch (Exception e) {
                e.printStackTrace();
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
