package controller.servlet;

import controller.BaseDatos;
import controller.Constantes;
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
@WebServlet(name = "updatePay", urlPatterns = {"/updatePay"})
public class updatePay extends HttpServlet {  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        int idPay = Integer.parseInt(request.getParameter("idPayment"));
        //Comparacion segura de nulos
        boolean mensualidadInscripcion = "1".equals(request.getParameter("mensualidadInscripcion"));
        boolean mensualidad1 = "1".equals(request.getParameter("mensualidad1"));
        boolean mensualidad2 = "1".equals(request.getParameter("mensualidad2"));
        boolean mensualidad3 = "1".equals(request.getParameter("mensualidad3"));
        boolean mensualidad4 = "1".equals(request.getParameter("mensualidad4"));
        boolean mensualidad5 = "1".equals(request.getParameter("mensualidad5"));
        boolean mensualidad6 = "1".equals(request.getParameter("mensualidad6"));
        boolean mensualidad7 = "1".equals(request.getParameter("mensualidad7"));
        int status = Integer.parseInt(request.getParameter("status"));
        BaseDatos bd = new BaseDatos();
        Payment pay = new Payment(idPay, mensualidadInscripcion, mensualidad1, mensualidad2, mensualidad3, mensualidad4
                                , mensualidad5, mensualidad6, mensualidad7, status);
        bd.actualizarSeguimientoDePago(pay);
        sesion.setAttribute("actualizacionCompleta","Lista de Seguimiento actualizada correctamente ");
        String url = Constantes.VentanasJSP.URL_LISTA_ALUMNOS;
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
