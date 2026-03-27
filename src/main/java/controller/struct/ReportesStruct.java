/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller.struct;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author anton
 */
public interface ReportesStruct {
    public void bitacorasDeAlumnos(HttpServletResponse response, String ruta, 
            String ruta_imagenes, String nombre_grupo, String nombre_profesor, 
            int id_teacher_student, String classroom) 
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, SQLException, JRException, IOException;
    public void bitacorasDeProfesores(HttpServletResponse response, String ruta, 
            String ruta_imagenes, String periodoActual) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, 
            SQLException, JRException, IOException;
    public void listasPagos(HttpServletResponse response, String ruta, 
            String ruta_imagenes, String nombre_grupo, String nombre_profesor, 
            int id_teacher, String classroom, String periodoActual, String numMeses) 
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, SQLException, JRException, IOException;
    public void listaCalificaciones(HttpServletResponse response, String ruta, 
            String ruta_imagenes, String nombre_grupo, String nombre_profesor, 
            int id_teacher, String periodoActual) 
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, SQLException, JRException, IOException;
}
