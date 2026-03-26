/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prueba;
import controller.BaseDatos;
import controller.Reportes;

import model.Consultas.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;
import java.sql.SQLException;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse; //version mejorada del javax

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.extensions.ExtensionsEnvironment;
import net.sf.jasperreports.engine.fonts.FontExtensionsRegistry;
        
/**
 *
 * @author Luis Morales
 */
public class Main {
    public static void main(String [] args) throws ClassNotFoundException, InstantiationException,
                                                                            IllegalAccessException, SQLException, JRException, IOException {
        /*BaseDatos base = new BaseDatos();
        //Prueba 1: Bitacoras de Asistencia de Alumnos
        ArrayList <ConsultaBitacorasAlumnos> bitacora = base.obtenerBitacorasDeAlumnos(1);
        Iterator<ConsultaBitacorasAlumnos> iter = bitacora.iterator();
        ConsultaBitacorasAlumnos per = null;
        System.out.println("Nombre Completo\t\t\t¿Sale Solo?");
        while(iter.hasNext()){
            per = iter.next();
            String nombre = base.concatenarDatosAlumno(per.getId_student());
            System.out.println(nombre + "\t" + per.isSale_solo() );
        }
        
        //Prueba 2: Bitacoras de Asistencia de Profesores
        ArrayList <ConsultaBitacorasProfesores> bitacora2 = base.obtenerBitacoraProfesores();
        Iterator<ConsultaBitacorasProfesores> iter2 = bitacora2.iterator();
        ConsultaBitacorasProfesores per2 = null;
        System.out.println("Nombre Completo\t\t\tGrupo Asignado");
        while(iter2.hasNext()){
            per2 = iter2.next();
            String nombre = base.concatenarDatosProfesor(per2.getId_teacher());
            String grupo = base.concatenarDatosGrupo(per2.getId_teacher());
            System.out.println(nombre + "\t"+ grupo );
        }
        //Prueba 3: Listas de Seguimiento de Pago
        ArrayList <ConsultaPagos> pagos = base.obtenerListasSeguimientoDePago(1);
        Iterator <ConsultaPagos> iter3 = pagos.iterator();
        ConsultaPagos per3 = null;
        while(iter3.hasNext()){
            per3 = iter3.next();
            String nombre = base.concatenarDatosAlumno(per3.getId_student());
            String pagoInscripcion = per3.isRegister_payment() ? "PAGADO" : "PENDIENTE";
            String pago1 = per3.isPay_1() ? "PAGADO" : "PENDIENTE";
            String pago2 = per3.isPay_2() ? "PAGADO" : "PENDIENTE";
            String pago3 = per3.isPay_3() ? "PAGADO" : "PENDIENTE";
            String pago4 = per3.isPay_4() ? "PAGADO" : "PENDIENTE";
            String pago5 = per3.isPay_5() ? "PAGADO" : "PENDIENTE";
            String pago6 = per3.isPay_6() ? "PAGADO" : "PENDIENTE";
            String pago7 = per3.isPay_7() ? "PAGADO" : "PENDIENTE";
            System.out.println(nombre + "\t"+ pagoInscripcion+ "\t"+ pago1 + "\t"+ pago2 + "\t"+ pago3+ "\t"+ pago4+ "\t"+ pago5 + "\t"+ pago6+ "\t"+ pago7);
        }*/
    }
}
