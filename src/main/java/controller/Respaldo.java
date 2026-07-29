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
import controller.Constantes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import model.Tables.*;


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
        
        ArrayList <String> encabezados1 = new ArrayList<>();
        encabezados1.add("Nombre Completo del Alumno");
        encabezados1.add("Pago de inscripcion");
        String [] mensualidades = bd.obtenerMesesCalendario();
        encabezados1.addAll(Arrays.asList(mensualidades));
        encabezados1.add("Estatus del Alumno");
        
        String [] encabezados = encabezados1.toArray(new String[encabezados1.size()]);

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
    
    @Override
    public void iniciarRespaldoSQL(OutputStream flujoSalida, String rutaEjecutable){
        //Se define el comando para crear el archivo SQL dentro del sistema. 
        String [] comando = {
                rutaEjecutable,
                "--opt",
                "--user=" + BaseDatos.configuracionBD.NOMBRE_USUARIO,
                "--password=" +BaseDatos.configuracionBD.PASSWORD_USUARIO,
                "--databases",  
                BaseDatos.configuracionBD.NOMBRE_BASE_DATOS,
                "-R"
                };
        try{
            //El sistema ejecuta el comando para obtener el dump SQL. 
            Runtime ejecuta = Runtime.getRuntime();
            Process creacion = ejecuta.exec(comando);
            //Leer el dump SQL bit por bit
            try{
                //Obtiene el flujo de entrada en bytes para archivo SQL generado en el dump
                InputStream is = creacion.getInputStream();
                //Reserva un espacio para almacenar el archivo sql
                byte[] buffer = new byte[4096];
                int bytesLeidos;
                //Mientras se encuentren bytes en el archivo SQL
                while((bytesLeidos = is.read(buffer))!=-1){
                    //Obtiene los bites del buffer y los guarda en doc. 
                    flujoSalida.write(buffer, 0, bytesLeidos);
                }
                flujoSalida.flush();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void iniciarRespaldoCSV(OutputStream flujoSalida){
        //Obtiene todas las tablas de la base de datos 
        ArrayList <Admin_school> admin_school = bd.obtenerAdministrador();
        ArrayList <Category> category = bd.obtenerCategorias();
        ArrayList <Grade> grade = bd.obtenerNivel();
        ArrayList <Grupos> grupos = bd.obtenerGrupos();
        ArrayList <Pay_simbology> pay_simbology = bd.obtenerCalendario();
        ArrayList <Payment> payment = bd.obtenerSeguimiento();
        ArrayList <Payment_status> payment_status = bd.obtenerEstatus();
        ArrayList <Report> report = bd.obtenerCalificaciones();
        ArrayList <Students> students = bd.obtenerEstudiantes();
        ArrayList <Teachers> teachers = bd.obtenerTeachers();
        ArrayList <Users> users = bd.obtenerUsuarios();
        try{
            //Crear el flujo para el formato ZIP
            ZipOutputStream formatoZIP = new ZipOutputStream(flujoSalida);
            
            this.crearLineaAdmin(formatoZIP, admin_school);
            this.crearLineaCategoria(formatoZIP, category);
            this.crearLineaGrade(formatoZIP, grade);
            this.crearLineaGrupos(formatoZIP, grupos);
            this.crearLineaCalendario(formatoZIP, pay_simbology);
            this.crearLineaPagos(formatoZIP, payment);
            this.crearLineaEstatus(formatoZIP, payment_status);
            this.crearLineaCalificaciones(formatoZIP, report);
            this.crearLineaEstudiantes(formatoZIP, students);
            this.crearLineaTeachers(formatoZIP, teachers);
            this.crearLineaUsuarios(formatoZIP, users);
            formatoZIP.finish();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public BufferedWriter agregarTabla(ZipOutputStream formatoZIP, String nombreArchivo) throws Exception{
        ZipEntry archivo = new ZipEntry(nombreArchivo);
        formatoZIP.putNextEntry(archivo);
        
        formatoZIP.write(0xEF);
        formatoZIP.write(0xBB);
        formatoZIP.write(0xBF);
        
        OutputStreamWriter osw = new OutputStreamWriter(formatoZIP, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);
        
        return bw;
    }
    
    public void crearLineaAdmin(ZipOutputStream zos, ArrayList <Admin_school> admin_school){
        try{
            BufferedWriter bw = this.agregarTabla(zos, "admin_school.csv");
            for(Admin_school ad: admin_school){
                int id_admin                    = ad.getId_admin();
                int id_user_admin               = ad.getId_user_admin();
                String apellido_paterno_admin   = ad.getApellido_paterno_admin();
                String apellido_materno_admin   = ad.getApellido_materno_admin();
                String nombre_admin             = ad.getNombre_admin();
                Object fecha_nacimiento_admin   = ad.getFecha_nacimiento_admin();
                String telefono_admin           = ad.getTelefono_admin();
                String email_admin              = ad.getEmail_admin();

                String linea = id_admin +";"+ id_user_admin +";"+ apellido_paterno_admin +";"+ apellido_materno_admin
                               +";"+ nombre_admin +";"+ fecha_nacimiento_admin +";"+ telefono_admin +";"+ email_admin;
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();
            zos.closeEntry();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void crearLineaCategoria(ZipOutputStream zos, ArrayList <Category> category){
        try{
            BufferedWriter bw = this.agregarTabla(zos, "category.csv");
            for(Category cat: category){
                int id_category                 = cat.getId_category();
                String description_category     = cat.getDescription_category();
                String linea = id_category +";"+ description_category;
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();
            zos.closeEntry();
        }
        
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void crearLineaGrade(ZipOutputStream zos, ArrayList <Grade> grade){
        try{
            BufferedWriter bw = this.agregarTabla(zos, "grade.csv");
            for(Grade nivel: grade){
                int id_grade                = nivel.getId_grade();
                String description_grade    = nivel.getDescription_grade();
                String linea = id_grade +";"+ description_grade;
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();
            zos.closeEntry();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void crearLineaGrupos(ZipOutputStream zos, ArrayList <Grupos> grupos){
        try{
            BufferedWriter bw = this.agregarTabla(zos, "grupos.csv");
            for(Grupos grupo: grupos){
                int id_group            = grupo.getId_group();
                int id_grade            = grupo.getId_grade();
                int level_group         = grupo.getLevel_group();
                int id_category_group   = grupo.getId_category_group();
                String classroom_group  = grupo.getClassroom_group();
                String linea = id_group + ";" + id_grade + ";" + level_group + ";" + id_category_group
                        +";" + classroom_group;
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();
            zos.closeEntry();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void crearLineaCalendario(ZipOutputStream zos, ArrayList <Pay_simbology> pay_simbology){
        try{
            BufferedWriter bw = this.agregarTabla(zos, "pay_simbology.csv");
            for(Pay_simbology mes: pay_simbology){
                int id_pay              = mes.getId_pay();
                String month            = mes.getMonth();
                String description_pay  = mes.getDescription_pay();
                double cost_pay         = mes.getCost_pay();
                String period_pay       = mes.getPeriod_pay();
                Object deadline_pay     = mes.getDeadline_pay();
                String linea = id_pay + ";" + month + ";" + description_pay + ";" + cost_pay
                        +";" + period_pay + ";" + deadline_pay;
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();
            zos.closeEntry();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void crearLineaPagos(ZipOutputStream zos, ArrayList <Payment> payment){
        try{
            BufferedWriter bw = this.agregarTabla(zos, "payment.csv");
            for(Payment pago: payment){
                int id_payment              = pago.getId_payment();
                boolean register_payment    = pago.isRegister_payment();
                boolean pay_1               = pago.isPay_1();
                boolean pay_2               = pago.isPay_2();
                boolean pay_3               = pago.isPay_3();
                boolean pay_4               = pago.isPay_4();
                boolean pay_5               = pago.isPay_5();
                boolean pay_6               = pago.isPay_6();
                boolean pay_7               = pago.isPay_7();
                int payment_status          = pago.getPayment_status();
                String linea = id_payment + ";" + register_payment + ";" + pay_1 + ";" + pay_2
                        +";" + pay_3 + ";" + pay_4  +";" + pay_5 + ";" + pay_6+ ";" + pay_7 + ";" + payment_status;
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();
            zos.closeEntry();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void crearLineaEstatus(ZipOutputStream zos, ArrayList <Payment_status> payment_status){
        try{
            BufferedWriter bw = this.agregarTabla(zos, "payment_status.csv");
            for(Payment_status p: payment_status){
                int id_status               = p.getId_status();
                String description_status   = p.getDescription_status();
                String linea = id_status + ";" + description_status;
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();
            zos.closeEntry();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void crearLineaCalificaciones(ZipOutputStream zos, ArrayList <Report> report){
        try{
            BufferedWriter bw = this.agregarTabla(zos, "report.csv");
            for(Report cal: report){
                int id_report                   = cal.getId_report();
                double first_partial_report     = cal.getFirst_partial_report();
                double second_partial_report    = cal.getSecond_partial_report();
                String linea = id_report + ";" + first_partial_report + ";" + second_partial_report;
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();
            zos.closeEntry();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void crearLineaEstudiantes(ZipOutputStream zos, ArrayList <Students> students){
        try{
            BufferedWriter bw = this.agregarTabla(zos, "students.csv");
            for(Students alu:  students){
                int id_student                      = alu.getId_student(); 
                int id_teacher_student              = alu.getId_teacher_student(); 
                int id_report_student               = alu.getId_report_student(); 
                int id_payment_student              = alu.getId_payment_student(); 
                int id_user_student                 = alu.getId_user_student(); 
                String apellido_paterno_student     = alu.getApellido_paterno_student(); 
                String apellido_materno_student     = alu.getApellido_materno_student(); 
                String nombre_student               = alu.getNombre_student(); 
                String telefono1_student            = alu.getTelefono1_student(); 
                String telefono2_student            = alu.getTelefono2_student(); 
                Object fecha_nacimiento_student     = alu.getFecha_nacimiento_student(); 
                String email_student                = alu.getEmail_student(); 
                boolean sale_solo                   = alu.isSale_solo();  
                String linea = id_student + ";" + id_teacher_student + ";" + id_report_student + ";" + id_payment_student + ";" + 
                               id_user_student + ";" + apellido_paterno_student + ";" + apellido_materno_student + ";" + nombre_student + ";" +
                               telefono1_student + ";" + telefono2_student + ";" + fecha_nacimiento_student + ";" + email_student + ";" + sale_solo;
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();
            zos.closeEntry();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void crearLineaTeachers(ZipOutputStream zos, ArrayList <Teachers> teachers){
        try{
            BufferedWriter bw = this.agregarTabla(zos, "teachers.csv");
            for(Teachers prof: teachers){
                int id_teacher                      = prof.getId_teacher();
                int id_user_teacher                 = prof.getId_user_teacher();
                String apellido_paterno_teacher     = prof.getApellido_paterno_teacher();
                String apellido_materno_teacher     = prof.getApellido_materno_teacher();
                String nombre_teacher               = prof.getNombre_teacher();
                String telefono_teacher             = prof.getTelefono_teacher();
                String email_teacher                = prof.getEmail_teacher(); 
                Object fecha_nacimiento_teacher     = prof.getFecha_nacimiento_teacher(); 
                String status_teacher               = prof.getStatus_teacher(); 
                int id_group_teacher                = prof.getId_group_teacher();
                String classroom_teacher            = prof.getClassroom_teacher();
                String linea = id_teacher + ";" + id_user_teacher + ";" + apellido_paterno_teacher + ";" + 
                               apellido_materno_teacher + ";" + nombre_teacher + ";" + telefono_teacher + ";" +
                               email_teacher + ";" + fecha_nacimiento_teacher + ";" + status_teacher + ";" +
                               id_group_teacher + ";" + classroom_teacher;
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();
            zos.closeEntry();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void crearLineaUsuarios(ZipOutputStream zos, ArrayList <Users> users){
        try{
            BufferedWriter bw = this.agregarTabla(zos, "users.csv");
            for(Users usuario: users){
                int id_user                 = usuario.getId_user();
                String nom_user             = usuario.getNom_user();
                String password             = usuario.getPassword();
                String rango                = usuario.getRango(); 
                String linea = id_user + ";" + nom_user + ";" + password + ";" + rango;
                bw.write(linea);
                bw.newLine();
            }
            bw.flush();
            zos.closeEntry();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
