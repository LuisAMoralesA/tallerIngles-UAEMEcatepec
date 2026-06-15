/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.util.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import model.Consultas.*;
import controller.struct.RespaldoStruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author Luis Morales
 */
public class Respaldo implements RespaldoStruct{
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    BaseDatos bd = new BaseDatos();
    
    /**
     * Esas constantes sirven para definir la configuracion de la Base de Datos
     **/
    public static final class configuracionBD {
        //Constantes para hacer la conexion a la base de datos
        public static final String NOMBRE_DRIVER = "com.mysql.cj.jdbc.Driver";
        public static final String URL_DB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
        public static final String NOMBRE_USUARIO = "adminTallerIngles";
        public static final String PASSWORD_USUARIO = "UAEMEX_2026";
    }
    
    public Respaldo(){
        try{
            String nombreDriver = BaseDatos.configuracionBD.NOMBRE_DRIVER;
            Class.forName(nombreDriver);
            String urlDB = BaseDatos.configuracionBD.URL_DB;
            String usuario = BaseDatos.configuracionBD.NOMBRE_USUARIO;
            String password = BaseDatos.configuracionBD.PASSWORD_USUARIO;
            con = DriverManager.getConnection(urlDB, usuario, password);
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void conexionBD(){
        try{
            String urlDB = BaseDatos.configuracionBD.URL_DB;
            String usuario = BaseDatos.configuracionBD.NOMBRE_USUARIO;
            String password = BaseDatos.configuracionBD.PASSWORD_USUARIO;
            con = DriverManager.getConnection(urlDB, usuario, password);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public ArrayList <ConsultaCalificaciones> obtenerCalificaciones(){
        ArrayList <ConsultaCalificaciones> listaAlumnos = new ArrayList<>();
        try{
            conexionBD();
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT students.id_student, students.apellido_paterno_student, students.apellido_materno_student, students.nombre_student, users.nom_user, \n" +
                            "report.id_report, report.first_partial_report, report.second_partial_report, report.avg_report FROM students \n" +
                            "INNER JOIN users ON users.id_user = students.id_user_student \n" +
                            "INNER JOIN report ON report.id_report = students.id_report_student;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                //0. Id del Alumno
                int id_student = rs.getInt("id_student");
                //1. Apellido Paterno
                String apellido_paterno = rs.getString("apellido_paterno_student");
                //2. Apellido Materno
                String apellido_materno = rs.getString("apellido_materno_student");
                //3. Nombre
                String nombre = rs.getString("nombre_student");
                //4. Nombre de Usuario
                String nom_user  =rs.getString("nom_user");
                //5. Calificacion de Primer Parcial
                double first_partial = rs.getDouble("first_partial_report");
                //6. Calificacion de Segundo Parcial
                double second_partial = rs.getDouble("second_partial_report");
                //7. Promedio final
                double average = rs.getDouble("avg_report");
                //8. Id de lista de calificaciones
                int id_report = rs.getInt("id_report");
                ConsultaCalificaciones consulta = new ConsultaCalificaciones(id_student, apellido_paterno, apellido_materno, nombre, nom_user,
                                            id_report, first_partial, second_partial, average);
                listaAlumnos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaAlumnos;
    }
    
    @Override
    public ArrayList <ConsultaPagos> obtenerSeguimientoPago(){
        ArrayList <ConsultaPagos> listaPagos = new ArrayList<>();
        try{
            conexionBD();
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT students.id_student, students.apellido_paterno_student, students.apellido_materno_student, students.nombre_student, students.telefono1_student,  "
                        + "payment.id_payment, payment.register_payment, payment.pay_1, payment.pay_2, payment.pay_3, payment.pay_4, "
                        + "payment.pay_5, payment.pay_6, payment.pay_7, payment.payment_status, payment_status.description_status FROM students \n"
                        + "INNER JOIN payment ON payment.id_payment = students.id_payment_student \n"
                        + "INNER JOIN payment_status ON payment.payment_status = payment_status.id_status ;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_student = rs.getInt("id_student");
                String apellido_paterno_student = rs.getString("apellido_paterno_student");
                String apellido_materno_student = rs.getString("apellido_materno_student");
                String nombre_student = rs.getString("nombre_student");
                String telefono1_student = rs.getString("telefono1_student");
                int id_payment = rs.getInt("id_payment");
                boolean register_payment = rs.getBoolean("register_payment");
                boolean pay_1 = rs.getBoolean("pay_1");
                boolean pay_2 = rs.getBoolean("pay_2");
                boolean pay_3 = rs.getBoolean("pay_3");
                boolean pay_4 = rs.getBoolean("pay_4");
                boolean pay_5 = rs.getBoolean("pay_5");
                boolean pay_6 = rs.getBoolean("pay_6");
                boolean pay_7 = rs.getBoolean("pay_7");
                int payment_status = rs.getInt("payment_status");
                String description_status = rs.getString("description_status");
                ConsultaPagos consulta = new ConsultaPagos(id_student,apellido_paterno_student, apellido_materno_student, nombre_student, telefono1_student,
                                        id_payment, register_payment, pay_1, pay_2, pay_3, pay_4, pay_5, pay_6, pay_7, payment_status, description_status);
                listaPagos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaPagos;
    }
    
    @Override
    public void iniciarRespaldo(OutputStream flujoSalida, String rutaImagen){
        ArrayList <ConsultaAlumnos> listaAlumnos = bd.obtenerDatosAlumnos();
        ArrayList <ConsultaTeacher> listaProfesores = bd.obtenerDatosProfesores();
        ArrayList <ConsultaCalificaciones> listaCalificaciones = obtenerCalificaciones();
        ArrayList <ConsultaPagos> listaPagos = obtenerSeguimientoPago();
        
        //Crear el documento de Excel correspondiente y le da el formato correspondiente. 
        Workbook    documentoExcel = new XSSFWorkbook();
        Font letraEncabezado = this.formatoLetraEncabezado(documentoExcel);
        Font letraDatos = this.formatoLetraNormal(documentoExcel);

        CellStyle estiloEncabezado = this.formatoFondoEncabezado(documentoExcel, letraEncabezado);
        CellStyle estiloDatos = this.formatoFondoNormal(documentoExcel, letraDatos);
        CellStyle estiloCorto = this.formatoDatosCortos(documentoExcel, letraDatos);
        try{
            conexionBD();
            Sheet       hojaCalculoAlumnos = documentoExcel.createSheet("Alumnos");
            Sheet       hojaCalculoProfesores = documentoExcel.createSheet("Profesores");
            Sheet       hojaCalculoCalificaciones = documentoExcel.createSheet("Calificaciones");
            Sheet       hojaCalculoPagos = documentoExcel.createSheet("Seguimiento de Pago");
            
            //Llenar las hojas de calculo correspondientes
            this.llenarHojaAlumno(documentoExcel, hojaCalculoAlumnos, listaAlumnos, 
                    estiloEncabezado, estiloDatos, estiloCorto, rutaImagen );
            this.llenarHojaProfesores(documentoExcel, hojaCalculoProfesores, listaProfesores, 
                    estiloEncabezado, estiloDatos, estiloCorto, rutaImagen  );
            this.llenarHojaCalificaciones(documentoExcel, hojaCalculoCalificaciones, listaCalificaciones, 
                    estiloEncabezado, estiloDatos, estiloCorto, rutaImagen  );
            this.llenarHojaPagos(documentoExcel, hojaCalculoPagos, listaPagos, 
                    estiloEncabezado, estiloDatos, estiloCorto, rutaImagen  );
            //Generar el archivo Excel con las hojas de calculo definidas. 
            documentoExcel.write(flujoSalida);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    @Override
    public void insertarImagen(Workbook hojaCalculo, Sheet hoja, String rutaImagen){
       // String rutaFisicaImagen = ;
        try{
            //Convertir la imagen en un conjunto de bits entendible para Java
            InputStream is = new FileInputStream(rutaImagen);
            byte [] imagenBytes = IOUtils.toByteArray(is);
            //Registrar la imagen dentro del libro de Excel
            int nuevaImagen = hojaCalculo.addPicture(imagenBytes, Workbook.PICTURE_TYPE_PNG);
            is.close();
            
            //Establecer las coordenadas donde se colocara la imagen del Taller de Ingles
            CreationHelper asistente = hojaCalculo.getCreationHelper();
            //Crear el objeto de dibujo dentro de la hoja de calculo
            Drawing <?> dibujo = hoja.createDrawingPatriarch();
            
            ClientAnchor ancla = asistente.createClientAnchor();

            ancla.setCol1(1);
            ancla.setRow1(1);
            ancla.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            
            //Dibujar la imagen
            XSSFPicture contenedor = (XSSFPicture) dibujo.createPicture(ancla, nuevaImagen);
            XSSFClientAnchor anclaAdecuada = (XSSFClientAnchor) contenedor.getPreferredSize(0.23, 0.48);
            anclaAdecuada.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            dibujo.createPicture(anclaAdecuada, nuevaImagen);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void insertarEspacio(Sheet hoja){
        // Darle una altura considerable a las primeras filas para albergar el logotipo libremente
        for (int i = 1; i <= 6; i++) {
            Row filaEspacio = hoja.getRow(i);
            if (filaEspacio == null) {
                filaEspacio = hoja.createRow(i);
            }
            filaEspacio.setHeightInPoints(22);
        }
    }
    
    @Override
    public void llenarInformacionTaller(String tituloHoja, Sheet hoja, CellStyle estiloEncabezado,CellStyle estiloDatos ){
        Row filaTitulo = hoja.getRow(1);
        if (filaTitulo == null) 
            filaTitulo = hoja.createRow(1);
        
        Row filaSubtitulo = hoja.getRow(2);
        if (filaSubtitulo == null) 
            filaSubtitulo = hoja.createRow(2);
        
        for(int i = 2; i<=5; i++){
            Cell celdaTitulo = filaTitulo.createCell(i);
            celdaTitulo.setCellStyle(estiloEncabezado);
            
            Cell celdaSubtitulo = filaSubtitulo.createCell(i);
            celdaSubtitulo.setCellStyle(estiloDatos);
        }
        
        filaTitulo.getCell(2).setCellValue("Taller de Inglés para Niños y Adolescentes");
        filaSubtitulo.getCell(2).setCellValue(tituloHoja);
        
        hoja.addMergedRegion(new CellRangeAddress(1,1,2,5));
        hoja.addMergedRegion(new CellRangeAddress(2,2,2,5));
    }
    @Override
    public void llenarHojaAlumno(Workbook hojaCalculo, Sheet hoja, ArrayList <ConsultaAlumnos> listaAlumnos,
                                CellStyle estiloEncabezado,CellStyle estiloDatos,CellStyle estiloCorto, String rutaImagen ){
        
        this.llenarInformacionTaller("Respaldo de Alumnos del TEI", hoja, estiloEncabezado, estiloDatos);
        this.insertarImagen(hojaCalculo, hoja,rutaImagen);
        this.insertarEspacio(hoja);
        
        Row filaEncabezados = hoja.createRow(7);
        filaEncabezados.setHeightInPoints(24);
        
        String [] encabezados = {
            "Nombre Completo del Alumno", 
            "Nombre de Usuario", 
            "Telefono Principal",
            "Correo Electronico",
            "¿Sale Solo?",
            "Fecha de Nacimiento",
            "Grupo Asignado",
            "Profesor Asignado"
        };
        this.crearEncabezados(filaEncabezados, estiloEncabezado, encabezados);

        int numeroFila = 8;
        for(ConsultaAlumnos alumno : listaAlumnos){
            Row fila = hoja.createRow(numeroFila++);
            fila.setHeightInPoints(18);
            
            String nombreCompleto   = bd.concatenarDatosAlumno(alumno.getId_student());
            String datosGrupo       = bd.concatenarDatosGrupo(alumno.getId_teacher_student());
            String datosProfesor    = bd.concatenarDatosProfesor(alumno.getId_teacher_student());
            
            this.crearCelda(fila, 1, nombreCompleto, estiloDatos);
            this.crearCelda(fila, 2, alumno.getNom_user(), estiloDatos);
            this.crearCelda(fila, 3, alumno.getTelefono1_student(), estiloCorto);
            this.crearCelda(fila, 4, alumno.getEmail_student(), estiloDatos);
            this.crearCelda(fila, 5, alumno.isSale_solo()? "SI" : "NO", estiloCorto);
            this.crearCelda(fila, 6, alumno.getFecha_nacimiento_student().toString(), estiloDatos);
            this.crearCelda(fila, 7, datosGrupo, estiloDatos);
            this.crearCelda(fila, 8, datosProfesor, estiloDatos);
        }
        
        this.autoajusteCeldas(hoja, encabezados.length);
    }
    
    @Override
    public void llenarHojaProfesores(Workbook hojaCalculo, Sheet hoja, ArrayList <ConsultaTeacher> listaProfesores,
                CellStyle estiloEncabezado, CellStyle estiloDatos, CellStyle estiloCorto, String rutaImagen ){
        this.llenarInformacionTaller("Respaldo de Profesores del TEI", hoja, estiloEncabezado, estiloDatos);
        this.insertarImagen(hojaCalculo, hoja, rutaImagen);
        this.insertarEspacio(hoja);
        
        Row filaEncabezados = hoja.createRow(7);
        filaEncabezados.setHeightInPoints(24);
        
        String [] encabezados = {
            "Nombre Completo del Profesor", 
            "Nombre de Usuario", 
            "Telefono Principal",
            "Correo Electronico",
            "Fecha de Nacimiento",
            "Grupo Asignado"
        };
        
        this.crearEncabezados(filaEncabezados, estiloEncabezado, encabezados);

        int numeroFila = 8;
        for(ConsultaTeacher profesor : listaProfesores){
            Row fila = hoja.createRow(numeroFila++);
            fila.setHeightInPoints(18);
            
            String nombreCompleto   = bd.concatenarDatosProfesor(profesor.getId_teacher());
            String datosGrupo        = bd.concatenarDatosGrupo(profesor.getId_teacher());
            
            this.crearCelda(fila, 1, nombreCompleto, estiloDatos);
            this.crearCelda(fila, 2, profesor.getNom_user(), estiloDatos);
            this.crearCelda(fila, 3, profesor.getTelefono_teacher(), estiloCorto);
            this.crearCelda(fila, 4, profesor.getEmail_teacher(), estiloDatos);
            this.crearCelda(fila, 5, profesor.getFecha_nacimiento_teacher().toString(), estiloDatos);
            this.crearCelda(fila, 6, datosGrupo, estiloDatos);
        }
        this.autoajusteCeldas(hoja, encabezados.length);
    }
    
    @Override
    public void llenarHojaCalificaciones(Workbook hojaCalculo, Sheet hoja, ArrayList <ConsultaCalificaciones> listaCalificaciones,
            CellStyle estiloEncabezado, CellStyle estiloDatos, CellStyle estiloCorto, String rutaImagen ){
        this.llenarInformacionTaller("Respaldo de Calificaciones de Alumnos del TEI", hoja, estiloEncabezado, estiloDatos);
        this.insertarImagen(hojaCalculo, hoja, rutaImagen);
        this.insertarEspacio(hoja);
        
        Row filaEncabezados = hoja.createRow(7);
        filaEncabezados.setHeightInPoints(24);
        
        String [] encabezados = {
            "Nombre Completo del Alumno", 
            "Nombre de Usuario", 
            "Primer Parcial (1P)",
            "Segundo Parcial (2P)",
            "Calificacion Final"
        };
        
        this.crearEncabezados(filaEncabezados, estiloEncabezado, encabezados);

        int numeroFila = 8;
        for(ConsultaCalificaciones alumno : listaCalificaciones){
            Row fila = hoja.createRow(numeroFila++);
            fila.setHeightInPoints(18);
            
            String nombreCompleto   = bd.concatenarDatosAlumno(alumno.getId_student());
            this.crearCelda(fila, 1, nombreCompleto, estiloDatos);
            this.crearCelda(fila, 2, alumno.getNom_user(), estiloDatos);
            this.crearCelda(fila, 3, alumno.getFirst_partial_report(), estiloCorto);
            this.crearCelda(fila, 4, alumno.getSecond_partial_report(), estiloCorto);
            this.crearCelda(fila, 5, alumno.getAverage_report(), estiloCorto);
        }
        this.autoajusteCeldas(hoja, encabezados.length);
    }
    @Override
    public void llenarHojaPagos(Workbook hojaCalculo, Sheet hoja, ArrayList <ConsultaPagos> listaPagos, 
            CellStyle estiloEncabezado, CellStyle estiloDatos, CellStyle estiloCorto, String rutaImagen ){
        this.llenarInformacionTaller("Respaldo de Seguimiento de Alumnos del TEI", hoja, estiloEncabezado, estiloDatos);
        this.insertarImagen(hojaCalculo, hoja, rutaImagen);
        this.insertarEspacio(hoja);
        
        Row filaEncabezados = hoja.createRow(7);
        filaEncabezados.setHeightInPoints(24);
        
        String [] encabezados = {
            "Nombre Completo del Alumno", 
            "Pago de inscripción", 
            "Mensualidad 1",
            "Mensualidad 2",
            "Mensualidad 3", 
            "Mensualidad 4", 
            "Mensualidad 5", 
            "Mensualidad 6", 
            "Mensualidad 7", 
            "Estatus del Alumno"
        };
        
        this.crearEncabezados(filaEncabezados, estiloEncabezado, encabezados);
        
        int numeroFila = 8;
        for(ConsultaPagos alumno : listaPagos){
            Row fila = hoja.createRow(numeroFila++);
            fila.setHeightInPoints(18);
            
            String nombreCompleto   = bd.concatenarDatosAlumno(alumno.getId_student());
            this.crearCelda(fila, 1, nombreCompleto, estiloDatos);
            this.crearCelda(fila, 2, alumno.isRegister_payment()? "SI" : "NO", estiloCorto);
            this.crearCelda(fila, 3, alumno.isPay_1()? "SI" : "NO", estiloCorto);
            this.crearCelda(fila, 4, alumno.isPay_2()? "SI" : "NO", estiloCorto);
            this.crearCelda(fila, 5, alumno.isPay_3()? "SI" : "NO", estiloCorto);
            this.crearCelda(fila, 6, alumno.isPay_4()? "SI" : "NO", estiloCorto);
            this.crearCelda(fila, 7, alumno.isPay_5()? "SI" : "NO", estiloCorto);
            this.crearCelda(fila, 8, alumno.isPay_6()? "SI" : "NO", estiloCorto);
            this.crearCelda(fila, 9, alumno.isPay_7()? "SI" : "NO", estiloCorto);
            this.crearCelda(fila, 10, alumno.getDescription_status(), estiloDatos);
        }
        this.autoajusteCeldas(hoja, encabezados.length);
    }
    
    @Override
    public void crearEncabezados(Row filaEncabezados, CellStyle estiloEncabezado, String [] encabezados){
        for(int i = 0; i < encabezados.length; i++){
            Cell celda = filaEncabezados.createCell(i+1);
            celda.setCellValue(encabezados[i]);
            celda.setCellStyle(estiloEncabezado);
        }
    }
    
    @Override
    public void crearCelda(Row fila, int columna, String valor, CellStyle estilo){
        Cell celda = fila.createCell(columna);
        celda.setCellValue(valor);
        celda.setCellStyle(estilo);
    }
    
    @Override
    public void crearCelda(Row fila, int columna, double valor, CellStyle estilo){
        Cell celda = fila.createCell(columna);
        celda.setCellValue(valor);
        celda.setCellStyle(estilo);
    }
    
    @Override
    public void autoajusteCeldas(Sheet hoja, int totalColumnas){
        for (int i = 1; i <= totalColumnas; i++) {
            hoja.autoSizeColumn(i);
            hoja.setColumnWidth(i, hoja.getColumnWidth(i) + 1200);
        }
    }
    
    @Override
    public Font formatoLetraEncabezado(Workbook hojaCalculo){
        //1. Dar formato a los encabezados (Fondo y tipografia). 
        Font headerFont = hojaCalculo.createFont();
        headerFont.setFontName("Arial");
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerFont.setFontHeightInPoints((short) 11);
        return headerFont;
    }
    
    @Override
    public CellStyle formatoFondoEncabezado(Workbook hojaCalculo, Font headerFont){
        // Estilo para el Encabezado
        CellStyle headerStyle = hojaCalculo.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        establecerBordes(headerStyle);
        return headerStyle;
    }
    
    @Override
    public Font formatoLetraNormal(Workbook hojaCalculo){
        // Fuente para los Datos (Normal, Arial, Tamaño 10)
        Font dataFont = hojaCalculo.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        return dataFont;
    }
    
    @Override
    public CellStyle formatoFondoNormal(Workbook hojaCalculo, Font dataFont){
        // Estilo para Datos Generales (Texto alineado a la izquierda)
        CellStyle dataStyle = hojaCalculo.createCellStyle();
        dataStyle.setFont(dataFont);
        dataStyle.setAlignment(HorizontalAlignment.LEFT);
        establecerBordes(dataStyle);
        return dataStyle;
    }
    
    @Override
    public CellStyle formatoDatosCortos(Workbook hojaCalculo, Font dataFont){
        // Estilo para Datos Cortos/Fechas (Texto centrado)
        CellStyle dataStyle = hojaCalculo.createCellStyle();
        dataStyle.setFont(dataFont);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        establecerBordes(dataStyle);
        return dataStyle;
    }
    
    @Override
    // Método auxiliar para pintar bordes limpios y delgados en gris
    public void establecerBordes(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
    }
}
