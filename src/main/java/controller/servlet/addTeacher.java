package controller.servlet;

import controller.BaseDatos;
import controller.Constantes;
import controller.SHA256;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import model.Consultas.ConsultaTeacher;
import model.Tables.*;

/**
 * Servlet que se activa cuando un administrador desea agregar un estudiante desde su perfil 
 * @author Luis Morales
 */
@WebServlet(name = "addTeacher", urlPatterns = {"/addTeacher"})
public class addTeacher extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        //Busca los parametros de los cuadros de texto del profesor para insertarlo en la base de datos.
        String apaterno = request.getParameter("apaterno").toUpperCase();
        String amaterno = request.getParameter("amaterno").toUpperCase();
        String name = request.getParameter("name").toUpperCase();
        String birthdate = request.getParameter("birthdate");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String status = request.getParameter("status");
        int grupo = Integer.parseInt(request.getParameter("grupo"));
        String classroom = request.getParameter("classroom");
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
            
            //Validar si existe el nombre de usuario en la base de datos
            ArrayList <ConsultaTeacher> usuarioEncontrado = base.obtenerDatosProfesores(nomUsuario);
            if(usuarioEncontrado != null && !usuarioEncontrado.isEmpty()){
               String mensaje = base.mensajeProfesorEncontrado(usuarioEncontrado);
               sesion.setAttribute("actualizacionCompleta","El profesor ingresado ya se encuentra en la base de datos");
               sesion.setAttribute("userNameRegistrado", mensaje);
               sesion.setAttribute("iconoVentana", "warning");
               response.sendRedirect(Constantes.VentanasJSP.URL_LISTA_TEACHERS);
               return;
            }
            
            else{
                //Crear un objeto de tipo User e insertarlo en la Base de datos.
                //Apesar de tener un valor de 1, no lo tomara en cuenta el codigo
                Users user = new Users(1, nomUsuario, passwordNew,"PROFESOR");
                base.insertarUsuario(user);

                //Define el valor de los cuadros de mensaje de confirmación
                sesion.setAttribute("actualizacionCompleta","Usuario Agregado correctamente");
                sesion.setAttribute("userNameRegistrado", nomUsuario);
                sesion.setAttribute("iconoVentana", "success");
                
                //Vuelve a obtener los datos del usuario en la tabla para obtener su id en la tabla
                ArrayList <Users> lista = base.obtenerUsuario(nomUsuario);
                Iterator  <Users> iter = lista.iterator();
                Users per = null;

                //Cuando encuentre el registro, ingresa los datos del Form a la tabla de Profesores
                if(iter.hasNext()){
                    per = iter.next();
                    int id_teacher = 1;
                    int id_user_teacher = per.getId_user();
                    Teachers teacher = new Teachers(id_teacher, id_user_teacher, apaterno,amaterno,
                                                    name,phone,email, (Object) birthdate, status, grupo, classroom);
                    base.insertarTeacher(teacher);
                }
                //Redirigir a la lista de profesores, indicando que la inserción fue exitosa. 
                response.sendRedirect(Constantes.VentanasJSP.URL_LISTA_TEACHERS);
            }
        }
        //Si las contraseñas son erroneas, imprimir el mensaje en la ventana actual. 
        else{
            sesion.setAttribute("contraseñaIncorrecta","Las contraseñas ingresadas son diferentes");
            response.sendRedirect(Constantes.VentanasJSP.URL_AGREGAR_INFORMACION+"?add=2");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


}
