/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.*;
import java.io.*;
import java.sql.*;

import jakarta.servlet.http.HttpServletResponse; //version mejorada del javax
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.pdf.JRPdfExporter;
import controller.struct.ReportesStruct;
import controller.BaseDatos.configuracionBD;

/**
 * Funcion para crear reportes
 * @author Luis Morales
 */

public class Reportes implements ReportesStruct{
    Connection con = null;
    BaseDatos bd = new BaseDatos();
    /**
     * Este constructor funciona para hacer conexion con la base de datos,
     * usando un URL y un Driver de conexion (8.0)
     **/
    public Reportes(){
        try{
            Class.forName(configuracionBD.NOMBRE_DRIVER);
            String urlDB = configuracionBD.URL_DB;
            String nombreUser = configuracionBD.NOMBRE_USUARIO;
            String password = configuracionBD.PASSWORD_USUARIO;
            con = DriverManager.getConnection(urlDB, nombreUser, password);
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
    /**
     * Esta funcion permite hacer la conexion de manera local a la Base de Datos
     * para los reportes
     **/
    @Override
    public void conexionBDReportes(){
        try{
            String urlDB = configuracionBD.URL_DB;
            String usuario = configuracionBD.NOMBRE_USUARIO;
            String password = configuracionBD.PASSWORD_USUARIO;
            con = DriverManager.getConnection(urlDB, usuario, password);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    /**
     * Este metodo permite crear el documento de Bitacora de Alumnos del TEI.  
     * <br>
     * Este documento cambia dependiendo del grupo seleccionado. 
     * <br>
     * @param  response: Respuesta del servidor HTTP
     * @param ruta: Ubicacion del reporte a utilizar
     * @param ruta_imagenes: Ubicacion de la carpeta Imagenes
     * @param nombre_grupo: Nombre del grupo de la bitacora de Alumnos
     * @param nombre_profesor: Nombre del profesor responsable de ese grupo
     * @param id_teacher_student: Id del profesor en la base de Datos
     * @param classroom: Salon de clases del grupo
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.sql.SQLException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.io.IOException
     **/
    @Override
    public void bitacorasDeAlumnos(HttpServletResponse response, String ruta, String ruta_imagenes, String nombre_grupo, String nombre_profesor, 
                                                                                    int id_teacher_student, String classroom) 
                                                                                    throws ClassNotFoundException, InstantiationException,
                                                                            IllegalAccessException, SQLException, JRException, IOException {
        try{
            //Hacer la conexion a la base de datos
            conexionBDReportes();
            //Se definen parametros de que requiera el reporte
            Map parametros = new HashMap();
            parametros.put("nombre_grupo", nombre_grupo);
            parametros.put("nombre_profesor", nombre_profesor);
            parametros.put("id_teacher_student", id_teacher_student);
            parametros.put("ruta_imagenes", ruta_imagenes);
            parametros.put("classroom", classroom);
            
            //Se crea un archivo para visualizar el PDF y se configura el servlet´
            response.setContentType("application/pdf");
            
            //Usa la plantilla de JaseperReports para cargar la información con los parametros que 
            JasperReport reporteFinal = (JasperReport) JRLoader.loadObjectFromFile(ruta);
            JasperPrint impresionFinal = JasperFillManager.fillReport(reporteFinal, parametros, con);
            JRPdfExporter salida = new JRPdfExporter();
            
            salida.setExporterInput(new SimpleExporterInput(impresionFinal));
            salida.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            salida.exportReport();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }       
    }
    /**
     * Este metodo permite crear el documento de Bitacora de Profesores del TEI.  
     * <br>
     * @param  response: Respuesta del servidor HTTP
     * @param ruta: Ubicacion del reporte a utilizar
     * @param ruta_imagenes: Ubicacion de la carpeta Imagenes
     * @param periodoActual: Periodo en curso del Taller
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.sql.SQLException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.io.IOException
     **/
    @Override
    public void bitacorasDeProfesores(HttpServletResponse response, String ruta, String ruta_imagenes, String periodoActual) throws ClassNotFoundException, InstantiationException,
                                                                            IllegalAccessException, SQLException, JRException, IOException {
        try{
            //Hacer la conexion a la base de datos
            conexionBDReportes();
            //Se definen parametros de que requiera el reporte
            Map parametros = new HashMap();
            
            parametros.put("ruta_imagenes", ruta_imagenes);
            parametros.put("PeriodoActual", periodoActual);
            
            //Se crea un archivo para visualizar el PDF y se configura el servlet´
            response.setContentType("application/pdf");
            
            //Usa la plantilla de JaseperReports para cargar la información con los parametros que se cargaron
            JasperReport reporteFinal = (JasperReport) JRLoader.loadObjectFromFile(ruta);
            JasperPrint impresionFinal = JasperFillManager.fillReport(reporteFinal, parametros, con);
            JRPdfExporter salida = new JRPdfExporter();
            
            salida.setExporterInput(new SimpleExporterInput(impresionFinal));
            salida.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            salida.exportReport();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }       
    }
    /**
     * Este metodo permite crear el documento de Lista de Seguimiento de Pagos de Alumnos del TEI.  
     * <br>
     * Este documento cambia dependiendo del grupo seleccionado. 
     * <br>
     * @param  response: Respuesta del servidor HTTP
     * @param ruta: Ubicacion del reporte a utilizar
     * @param ruta_imagenes: Ubicacion de la carpeta Imagenes
     * @param periodoActual: Periodo en curso del Taller
     * @param nombre_grupo: Nombre del grupo de la bitacora de Alumnos
     * @param nombre_profesor: Nombre del profesor responsable de ese grupo
     * @param id_teacher: Id del profesor en la base de Datos
     * @param numMeses: Numero de meses que posee el semestre
     * @param classroom: Salon de clases del grupo
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.sql.SQLException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.io.IOException
     **/
    @Override
    public void listasPagos(HttpServletResponse response, String ruta, String ruta_imagenes, String nombre_grupo, String nombre_profesor, 
                                                                                    int id_teacher, String classroom, String periodoActual, String numMeses) 
                                                                            throws ClassNotFoundException, InstantiationException,
                                                                            IllegalAccessException, SQLException, JRException, IOException {
        try{
            //Hacer la conexion a la base de datos
            conexionBDReportes();
            //Se definen parametros de que requiera el reporte
            Map parametros = new HashMap();
            parametros.put("nombre_grupo", nombre_grupo);
            parametros.put("nombre_profesor", nombre_profesor);
            parametros.put("classroom", classroom);
            parametros.put("ruta_imagenes", ruta_imagenes);
            parametros.put("PeriodoActual", periodoActual);
            parametros.put("id_teacher", id_teacher);
            parametros.put("numMeses", numMeses);
            
            
            //Se crea un archivo para visualizar el PDF y se configura el servlet´
            response.setContentType("application/pdf");
            
            //Usa la plantilla de JaseperReports para cargar la información con los parametros que se cargaron
            JasperReport reporteFinal = (JasperReport) JRLoader.loadObjectFromFile(ruta);
            JasperPrint impresionFinal = JasperFillManager.fillReport(reporteFinal, parametros, con);
            JRPdfExporter salida = new JRPdfExporter();
            
            salida.setExporterInput(new SimpleExporterInput(impresionFinal));
            salida.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            salida.exportReport();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }     
        
    }
    /**
     * Este metodo permite crear el documento de Lista de Calificaciones de Alumnos del TEI.  
     * <br>
     * Este documento cambia dependiendo del grupo seleccionado. 
     * <br>
     * @param  response: Respuesta del servidor HTTP
     * @param ruta: Ubicacion del reporte a utilizar
     * @param ruta_imagenes: Ubicacion de la carpeta Imagenes
     * @param periodoActual: Periodo en curso del Taller
     * @param nombre_grupo: Nombre del grupo de la bitacora de Alumnos
     * @param nombre_profesor: Nombre del profesor responsable de ese grupo
     * @param id_teacher: Id del profesor en la base de Datos
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.sql.SQLException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.io.IOException
     **/
    @Override
    public void listaCalificaciones(HttpServletResponse response, String ruta, String ruta_imagenes, String nombre_grupo, String nombre_profesor, 
                                                                                    int id_teacher, String periodoActual) 
                                                                            throws ClassNotFoundException, InstantiationException,
                                                                            IllegalAccessException, SQLException, JRException, IOException {
        try{
            //Hacer la conexion a la base de datos
            conexionBDReportes();
            //Se definen parametros de que requiera el reporte
            Map parametros = new HashMap();
            parametros.put("nombre_grupo", nombre_grupo);
            parametros.put("nombre_profesor", nombre_profesor);
            parametros.put("ruta_imagenes", ruta_imagenes);
            parametros.put("PeriodoActual", periodoActual);
            parametros.put("id_teacher", id_teacher);
            
            
            //Se crea un archivo para visualizar el PDF y se configura el servlet´
            response.setContentType("application/pdf");
            
            //Usa la plantilla de JaseperReports para cargar la información con los parametros que se cargaron
            JasperReport reporteFinal = (JasperReport) JRLoader.loadObjectFromFile(ruta);
            JasperPrint impresionFinal = JasperFillManager.fillReport(reporteFinal, parametros, con);
            JRPdfExporter salida = new JRPdfExporter();
            
            salida.setExporterInput(new SimpleExporterInput(impresionFinal));
            salida.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            salida.exportReport();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
}
