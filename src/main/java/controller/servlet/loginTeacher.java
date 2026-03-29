
package controller.servlet;

import controller.BaseDatos;
import controller.Constantes;
import controller.SHA256;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet que se activa cuando se inicia sesion como Profesor en el sitio web. 
 * @author Luis Morales
 */
@WebServlet(name = "loginTeacher", urlPatterns = {"/loginTeacher"})
public class loginTeacher extends HttpServlet {
    
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
        if(request.getParameter("submit")!=null){
            //Devuelve un numero segun lo indicado en el metodo de Inicio de Sesion
            int resultado = base.inicioSesion(user, pass, "PROFESOR");
            switch (resultado) {
                //Si el usuario existe y tiene el rango correcto
                case BaseDatos.constantesLogin.ACCESO_CONCEDIDO:
                    //Establece una sesion al usuario
                    sesion = request.getSession(true); 
                    sesion.setAttribute("sesionIniciada", user);
                    response.sendRedirect(Constantes.VentanasJSP.URL_MENU_TEACHER);
                    //response.sendRedirect("/tallerDeInglesUAEM/view/menuTeacher.jsp");
                    break;
                //Si el usuario no existe o tiene otro rango 
                case BaseDatos.constantesLogin.USUARIO_NO_ENCONTRADO:
                    //Impide el paso debido a que el usuario no fue encontrado
                    sesion = request.getSession(false);
                    sesion.setAttribute("errorMessage", "Usuario no encontrado");
                    response.sendRedirect(Constantes.VentanasJSP.URL_LOGIN_TEACHER);
                    //response.sendRedirect("/tallerDeInglesUAEM/view/loginTeacher.jsp");
                    break;
                //Si el usuario existe, pero tiene su contraseña incorrecta
                case BaseDatos.constantesLogin.DATO_INCORRECTO:
                    //Impide el paso debido a que la contraseña ingresada fue incorrecta
                    sesion = request.getSession(false);
                    sesion.setAttribute("errorMessage", "La contraseña ingresada es incorrecta");
                    response.sendRedirect(Constantes.VentanasJSP.URL_LOGIN_TEACHER);
                    //response.sendRedirect("/tallerDeInglesUAEM/view/loginTeacher.jsp");
                    break;
            }
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
