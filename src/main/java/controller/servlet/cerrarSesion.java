package controller.servlet;

import controller.Constantes;
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
                    url = Constantes.VentanasJSP.URL_LOGIN_ALUMNO;
                    break;
                case "ADMINISTRADOR":
                    url = Constantes.VentanasJSP.URL_LOGIN_ADMIN;
                    break;
                case "PROFESOR":
                    url = Constantes.VentanasJSP.URL_LOGIN_TEACHER;
                    break;
                }     
            }
            else{
                url = Constantes.VentanasJSP.URL_INDEX;
            }
            //Al terminar de asignar la URL, quitar los atributos de sesion al usuario
            sesion.invalidate();
        }
        else{
            url = Constantes.VentanasJSP.URL_INDEX;
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
