package controller.struct;

import java.io.OutputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import model.Consultas.*;


/**
 *
 * @author Luis Morales
 */
public interface RespaldoStruct {
    //Conexion a Base de datos. 
    public void conexionBD();
    
    public ArrayList <ConsultaCalificaciones> obtenerCalificaciones();
    public ArrayList <ConsultaPagos> obtenerSeguimientoPago();
    
    public void insertarImagen(Workbook hojaCalculo, Sheet hoja, String rutaImagen); 
    public void iniciarRespaldo(OutputStream flujoSalida, String rutaImagen);
    public void insertarEspacio(Sheet hoja);
    
    
    //Funciones de llenado de datos
    public void llenarHojaAlumno(Workbook hojaCalculo, Sheet hoja, ArrayList <ConsultaAlumnos> listaAlumnos, 
            CellStyle estiloEncabezado,CellStyle estiloDatos,CellStyle estiloCorto, String rutaImagen);
    public void llenarHojaProfesores(Workbook hojaCalculo, Sheet hoja, ArrayList <ConsultaTeacher> listaProfesores, 
            CellStyle estiloEncabezado,CellStyle estiloDatos,CellStyle estiloCorto, String rutaImagen);
    public void llenarHojaCalificaciones(Workbook hojaCalculo, Sheet hoja, ArrayList <ConsultaCalificaciones> listaCalificaciones, 
            CellStyle estiloEncabezado,CellStyle estiloDatos,CellStyle estiloCorto, String rutaImagen);
    public void llenarHojaPagos(Workbook hojaCalculo, Sheet hoja, ArrayList <ConsultaPagos> listaPagos, 
            CellStyle estiloEncabezado,CellStyle estiloDatos,CellStyle estiloCorto, String rutaImagen);
    //Funciones auxiliares de creacion
    public void llenarInformacionTaller(String tituloHoja, Sheet hoja, CellStyle estiloEncabezado,CellStyle estiloDatos );
    public void crearEncabezados(Row filaEncabezados, CellStyle estiloEncabezado, String [] encabezados);
    public void crearCelda(Row fila, int columna, String valor, CellStyle estilo);
    public void crearCelda(Row fila, int columna, double valor, CellStyle estilo);
    public void autoajusteCeldas(Sheet hoja, int totalColumnas); 
    
    //Funciones de formato de hojas de calculo.
    public Font formatoLetraEncabezado(Workbook hojaCalculo);
    public CellStyle formatoFondoEncabezado(Workbook hojaCalculo, Font headerFont);
    public Font formatoLetraNormal(Workbook hojaCalculo);
    public CellStyle formatoFondoNormal(Workbook hojaCalculo, Font dataFont);
    public CellStyle formatoDatosCortos(Workbook hojaCalculo, Font dataFont);
    public void establecerBordes(CellStyle style);
    
    public void iniciarRespaldoSQL(OutputStream flujoSalida, String rutaEjecutable);
}
