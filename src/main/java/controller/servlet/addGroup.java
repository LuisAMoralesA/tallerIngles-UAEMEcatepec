package controller.servlet;

import controller.BaseDatos;
import controller.Constantes;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Tables.*;

/**
 * Servlet que se activa cuando un administrador desea agregar un estudiante desde su perfil 
 * @author Luis Morales
 */
@WebServlet(name = "addGroup", urlPatterns = {"/addGroup"})
public class addGroup extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //Busca los parametros de los cuadros de texto del grupo para agregar a la base de datos. 
        int id_group = 1;
        int id_grade = Integer.parseInt(request.getParameter("grade"));
        int level = Integer.parseInt(request.getParameter("level"));
        int id_category = Integer.parseInt(request.getParameter("category"));
        
        //Recupera la sesion actual e inicia la conexion a la base de datos
        HttpSession sesion = request.getSession();
        BaseDatos base = new BaseDatos();
        
        //Crea un objeto de tipo Grupo e inserta sus valores en la base de datos. 
        Grupos grupo = new Grupos(id_group, id_grade, level, id_category, "null");
        base.insertarGrupos(grupo);
        
        //Define el valor de los cuadros de mensaje de confirmación y redirige a la lista de grupos.
        sesion.setAttribute("actualizacionCompleta","Grupo Agregado correctamente");
        sesion.setAttribute("userNameRegistrado", "");
        response.sendRedirect(Constantes.VentanasJSP.URL_LISTA_GRUPOS);
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
