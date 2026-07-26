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

/**
 *
 * @author anton
 */
@WebServlet(name = "deleteInformation", urlPatterns = {"/deleteInformation"})
public class deleteInformation extends HttpServlet {

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
        
            //Obtiene datos de la solicitud HTTP para hacer el proceso de eliminación. 
            String rango = request.getParameter("rango");
            int id_principal = Integer.parseInt(request.getParameter("id"));
            int id_admin, id_student, id_teacher, id_group;
            int id_user = 0;
            String url = "";
            
            //Obtiene la sesion de la aplicacion y crea la conexion con la base de datos
            HttpSession sesion = request.getSession();
            BaseDatos bd = new BaseDatos();
            //Dependiendo del rango que se desea eliminar, cambian los valores y los objetos. 
            switch (rango){
                //CASO DE ADMINISTRADOR: Requiere eliminar  su registro de administrador y de usuario. 
                case "a":
                    id_user = Integer.parseInt(request.getParameter("user"));
                    id_admin = id_principal;
                    bd.eliminarAdministrador(id_admin);
                    bd.eliminarUsuario(id_user);
                    url = Constantes.VentanasJSP.URL_LISTA_ADMIN;
                    //Define el valor de los cuadros de mensaje de confirmación
                    sesion.setAttribute("actualizacionCompleta","Datos del Administrador eliminados correctamente");
                    break;
                    
                //CASO DE ESTUDIANTE: Requiere eliminar su registro de alumno, sus listas de pago, 
                //calificaciones y de usuario. 
                case "s":
                    id_user = Integer.parseInt(request.getParameter("user"));
                    id_student = id_principal;
                    bd.eliminarAlumno(id_student);
                    bd.eliminarListaDePago(id_student);
                    bd.eliminarListaCalificaciones(id_student);
                    bd.eliminarUsuario(id_user);
                    url = Constantes.VentanasJSP.URL_LISTA_ALUMNOS;
                    //Define el valor de los cuadros de mensaje de confirmación
                    sesion.setAttribute("actualizacionCompleta","Datos del Estudiante eliminados correctamente");
                    break;
                    
                //CASO DE TEACHER: Requiere desvincular los alumnos y eliminar su registro de profesor 
                //y de usuario. 
                case "t":
                    id_user = Integer.parseInt(request.getParameter("user"));
                    id_teacher = id_principal;
                    bd.desvincularAlumnos(id_teacher);
                    bd.eliminarTeacher(id_teacher);
                    bd.eliminarUsuario(id_user);
                    url = Constantes.VentanasJSP.URL_LISTA_TEACHERS;
                    //Define el valor de los cuadros de mensaje de confirmación
                    sesion.setAttribute("actualizacionCompleta","Datos del Profesor eliminados correctamente");
                    break;
                    
                //CASO DE GRUPOS: Requiere desvincular al grupo del profesor y eliminar el registro dek grupo.
                case "g":
                    id_group = id_principal;
                    bd.desvincularProfesores(id_group);
                    bd.eliminarGrupo(id_group);
                    url = Constantes.VentanasJSP.URL_LISTA_GRUPOS;
                    sesion.setAttribute("actualizacionCompleta","Datos del Grupo eliminados correctamente");
                    break;
            }
            response.sendRedirect(url);
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
